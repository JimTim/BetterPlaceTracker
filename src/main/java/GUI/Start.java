/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BetterPlaceAPI.Donator;
import BetterPlaceAPI.FundraisingEvents;
import Control.FiveSecondWonder;
import Control.PropertyHandler;
import Control.UrlReader;
import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tim
 */
public class Start extends Application {

    //TODO: muss die überhaupt Global sein?!
    private ProgressBar progressBar;
    private String betterPlaceUrl;
    private String betterPlaceID;
    private String propertyFile = "User.properties";
    private FiveSecondWonder fiveSecondWonder = new FiveSecondWonder();
    //static GridPane donationGrid;

    @Override
    public void start(final Stage primaryStage) {

        try {
            Properties properties = PropertyHandler.readProperties(propertyFile);
            betterPlaceUrl = properties.getProperty("betterPlaceUrl");
            betterPlaceID = properties.getProperty("betterPlaceID");
        } catch (IOException e) {
            e.printStackTrace();
        }


        betterPlaceUrl=betterPlaceUrl+betterPlaceID+"/";
        //Hauptpanel
        final GridPane gridPane = GuiDesigns.getHauptPanel();
        //Bild
        final ImageView imgView = GuiDesigns.getImageView(gridPane);

        //DonatorList
        final ObservableList<Donator> donatorList;
        donatorList = FXCollections.observableArrayList();
        final ListView/*observableArrayList*/<Donator> donatorListView = GuiDesigns.getDonationListView(donatorList);

        progressBar = GuiDesigns.getProgress(gridPane);

        final Label actual=new Label("Aktuell");
        actual.setAlignment(Pos.CENTER_LEFT);

        final Label target=new Label("Ziel");
        target.setAlignment(Pos.CENTER_RIGHT);

        final BorderPane borderpane = new BorderPane();
        borderpane.setLeft(actual);
        borderpane.setRight(target);

        GuiDesigns.getScene(gridPane,primaryStage);

        GuiDesigns.setprimaryStageValues(primaryStage);
        primaryStage.show();

        //Task
        //Ich denke hier kann man auch noch optimieren
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            try {

                //Der name ist noch nicht Ideal, bitte mal umbennen nach Funktion und testen
                fiveSecondWonder.givemefivesconds(betterPlaceUrl,donatorList);

                String json;
                Gson gson = new Gson();
                //get actual donationCount
                json = UrlReader.readUrl(betterPlaceUrl);
                FundraisingEvents fundraisingEvents = gson.fromJson(json, FundraisingEvents.class);
                progressBar.setProgress(fundraisingEvents.getProgressPercentage().doubleValue() / 100);
                actual.setText(fundraisingEvents.getDonatedAmountInCents()/100+"€");
                target.setText(fundraisingEvents.getRequestedAmountInCents()/100+"€");
                int linkSize=fundraisingEvents.getProfilePicture().getLinks().size();

                //das sollte man eventuell nicht in dieser Funktion  machen sondern schon vorher
                if (imgView.getImage()==null) {
                    Image img = new Image(fundraisingEvents.getProfilePicture().getLinks().get(linkSize-1).getHref());
                    imgView.setImage(img);
                    gridPane.add(imgView, 0, 0);
                    primaryStage.setTitle(fundraisingEvents.getTitle());
                    gridPane.add(donatorListView, 0, 1);
                    gridPane.add(borderpane, 0, 3);
                }

            } catch (Exception ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}