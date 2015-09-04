package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

    public static Scene getScene(GridPane gridPane, Stage primaryStage){
        Scene scene = new Scene(gridPane, 400, 460);
        primaryStage.setScene(scene);



        return scene;
    }

    public static void setprimaryStageValues(Stage primaryStage){
        primaryStage.setMaxWidth(440);
        primaryStage.setMaxHeight(450);
    }
}
