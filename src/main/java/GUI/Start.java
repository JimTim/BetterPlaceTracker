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
import Control.Soundhandler;
import Tools.ISO8601;
import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Control.Soundhandler.*;

/**
 *
 * @author Tim
 */
public class Start extends Application {

    private ProgressBar progressBar;
    private String betterPlaceUrl; // = "https://api.betterplace.org/de/api_v4/fundraising_events/";
    //würde Local reichen / kommt dann eh in die Properties
    private String betterPlaceID; //="24307";
    private Date lastDonationDate = null;
    //static GridPane donationGrid;

    @Override
    public void start(final Stage primaryStage) {

        try {
            initialiseProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        betterPlaceUrl=betterPlaceUrl+betterPlaceID+"/";
        final GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(0, 10, 0, 10));

        final ImageView imgView = new ImageView();
        imgView.setPreserveRatio(true);
        imgView.setSmooth(true);
        imgView.setCache(true);
        imgView.fitWidthProperty().bind(gp.widthProperty().subtract(20));
        
        final ObservableList<Donator> donatorList;
        donatorList = FXCollections.observableArrayList();
        final ListView/*observableArrayList*/<Donator> donatorListView = new ListView<>(donatorList);
        donatorListView.setMaxHeight(200);
        donatorListView.setCellFactory(new Callback<ListView<Donator>, ListCell<Donator>>(){
 
            @Override
            public ListCell<Donator> call(ListView<Donator> p) {

                return new ListCell<Donator>(){

                    @Override
                    protected void updateItem(Donator donator, boolean bln) {
                        super.updateItem(donator, bln);
                        if (donator != null) {
                            DecimalFormat df = new DecimalFormat("#.00");
                            if (donator.getMessage()!=null && !donator.getMessage().equals("")) {
                                setText(df.format(donator.getCount())+"€ von "+donator.getName()+"\n"+donator.getMessage().replace("<br>", "\n"));
                            } else {
                                setText(df.format(donator.getCount())+"€ von "+donator.getName());
                            }
                        }
                    }
                };
            }
        });
        progressBar = new ProgressBar();
        progressBar.prefWidthProperty().bind(gp.widthProperty().subtract(20));
        gp.add(progressBar, 0, 2);
        final BorderPane borderpane = new BorderPane();
        final Label actual=new Label("Aktuell");
        actual.setAlignment(Pos.CENTER_LEFT);
        final Label target=new Label("Ziel");
        target.setAlignment(Pos.CENTER_RIGHT);
        borderpane.setLeft(actual);
        borderpane.setRight(target);
        
        Scene scene = new Scene(gp, 400, 460);
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(440);
        primaryStage.setMaxHeight(450);
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
                                    Soundhandler.playSound();
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
                    gp.add(imgView, 0, 0);
                    gp.add(donatorListView, 0, 1);
                    gp.add(borderpane, 0, 3);
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

    private void initialiseProperties() throws IOException {
        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream("User.properties"));
        properties.load(stream);
        stream.close();

        betterPlaceUrl = properties.getProperty("betterPlaceUrl");
        betterPlaceID = properties.getProperty("betterPlaceID");
    }
}
