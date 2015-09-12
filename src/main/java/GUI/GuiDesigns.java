package GUI;

import BetterPlaceAPI.Donator;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.text.DecimalFormat;

/**
 * Created by karl on 04.09.15.
 */
public class GuiDesigns {
    /**
     * Das Hauptgrid
     * @return gridPane
     */
    public static GridPane getHauptPanel(){
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(0, 10, 0, 10));

        return gridPane;
    }

    /**
     * Das Bild der Donation
     * @param gridPane
     * @return ImageView
     */
    public static ImageView getImageView(GridPane gridPane){
        final ImageView imgView = new ImageView();
        imgView.setPreserveRatio(true);
        imgView.setSmooth(true);
        imgView.setCache(true);
        imgView.fitWidthProperty().bind(gridPane.widthProperty().subtract(20));
        return imgView;
    }

    /**
     * Der Fortschrittsbalken
     * @param gridPane
     * @return
     */
    public static ProgressBar getProgress(GridPane gridPane){
        ProgressBar progressBar = new ProgressBar();
        progressBar.prefWidthProperty().bind(gridPane.widthProperty().subtract(20));
        gridPane.add(progressBar, 0, 2);
        return  progressBar;
    }

    /**
     * Setzt Höhe udn Breite der Scene
     * @param gridPane
     * @param primaryStage
     * @return
     */
    public static Scene getScene(GridPane gridPane, Stage primaryStage){
        //TODO hier mal besser mit Prozent arbeiten oder so
        Scene scene = new Scene(gridPane, 400, 460);
        primaryStage.setScene(scene);
        return scene;
    }

    /**
     * Setzt Höhe und Breite der Stage
     * @param primaryStage
     */
    public static void setprimaryStageValues(Stage primaryStage){
        primaryStage.setMaxWidth(440);
        primaryStage.setMaxHeight(450);
    }

    /**
     *
     * @param donatorList
     * @return Listview DonatorListview
     */
    public static ListView/*observableArrayList*/<Donator> getDonationListView(ObservableList<Donator> donatorList){
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
        return donatorListView;
    }
}
