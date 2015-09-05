/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BetterPlaceAPI.BetterPlaceDonator;
import BetterPlaceAPI.Donator;
import BetterPlaceAPI.FundraisingEvents;
import BetterPlaceAPI.Opinions;
import Control.PropertyHandler;
import Tools.ISO8601;
import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
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
    private Date lastDonationDate = null;
    private String propertyFile = "User.properties";
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

        final ObservableList<Donator> donatorList;
        donatorList = FXCollections.observableArrayList();
        final ListView/*observableArrayList*/<Donator> donatorListView = new ListView<>(donatorList);
        donatorListView.setMaxHeight(200);
        donatorListView.setCellFactory(new Callback<ListView<Donator>, ListCell<Donator>>() {

            @Override
            public ListCell<Donator> call(ListView<Donator> p) {

                return new ListCell<Donator>() {
                    @Override
                    protected void updateItem(Donator donator, boolean bln) {
                        super.updateItem(donator, bln);
                        if (donator != null) {
                            DecimalFormat df = new DecimalFormat("#.00");
                            if (donator.getMessage() != null && !donator.getMessage().equals("")) {
                                setText(df.format(donator.getCount()) + "€ von " + donator.getName() + "\n" + donator.getMessage().replace("<br>", "\n"));
                            } else {
                                setText(df.format(donator.getCount()) + "€ von " + donator.getName());
                            }
                        }
                    }
                };
            }
        });

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
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            String json;
            try {
                json = readUrl(betterPlaceUrl + "opinions.json?order=created_at:DESC&per_page=1");

                Gson gson = new Gson();
                Opinions opinionsTotalEntries = gson.fromJson(json, Opinions.class);
                int total_entries = opinionsTotalEntries.getTotalEntries();

                json = readUrl(betterPlaceUrl + "opinions.json?order=created_at:DESC&facets=has_donation:true&per_page=" + total_entries);
                Opinions opinions = gson.fromJson(json, Opinions.class);
                List<BetterPlaceDonator> donators = opinions.getData();
                if (lastDonationDate != null) {
                    for (BetterPlaceDonator donator : donators) {
                        Date date = ISO8601.toDate(donator.getUpdatedAt());
                        long lastDonationTimestamp=lastDonationDate.getTime();
                        if (lastDonationDate != null && date.getTime() > lastDonationTimestamp) {
                            if (donator.getDonatedAmountInCents() != null) {
                                Donator newDonator=new Donator();
                                newDonator.setId(donator.getId());
                                newDonator.setCount(donator.getDonatedAmountInCents() / 100);
                                if (donator.getAuthor() != null && donator.getAuthor().getName() != null && !donator.getAuthor().getName().equals("")) {
                                   newDonator.setName(donator.getAuthor().getName());

                                    if (donator.getMessage() != null && !donator.getMessage().equals("")) {
                                        newDonator.setMessage(donator.getMessage());
                                    }
                                } else {
                                    newDonator.setName("Anonym");
                                }

                                //still exist?
                                boolean found=false;
                                for (int i=0; i< donatorList.size();i++){
                                    if (donatorList.get(i).getId() == newDonator.getId()) {
                                        donatorList.set(i, newDonator);
                                        found=true;
                                    }
                                }
                                if (!found){
                                     donatorList.add(newDonator);
                                }
                            }
                        }
                    }
                }
                lastDonationDate = new Date();

                //get actual donationCount
                json = readUrl(betterPlaceUrl);
                FundraisingEvents fundraisingEvents = gson.fromJson(json, FundraisingEvents.class);
                progressBar.setProgress(fundraisingEvents.getProgressPercentage().doubleValue() / 100);
                actual.setText(fundraisingEvents.getDonatedAmountInCents()/100+"€");
                target.setText(fundraisingEvents.getRequestedAmountInCents()/100+"€");
                int linkSize=fundraisingEvents.getProfilePicture().getLinks().size();
                if (imgView.getImage()==null) {
                    Image img = new Image(fundraisingEvents.getProfilePicture().getLinks().get(linkSize-1).getHref());
                    imgView.setImage(img);
                    primaryStage.setTitle(fundraisingEvents.getTitle());
                    gridPane.add(imgView, 0, 0);
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

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder stringBuilder = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                stringBuilder.append(chars, 0, read);
            }

            return stringBuilder.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

    }
}
