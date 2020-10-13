import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EcovisMainUI extends Application {

    //TODO: Download javaFX
    //TODO: Edit configurations, copypaste declan's stuff under VM options
    //TODO: Under project structure, add library - path to JavaFX/lib
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        System.out.println("before UI run");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("EcovisMainUI.fxml"));
        primaryStage.setTitle("ECOVIZ");
        primaryStage.setScene(new Scene(root, 1850, 900));
//        VBox parent1 = new VBox();
//        Label label1 = new Label("Some label text"); //child node for parent1
//        Label label2 = new Label("Some additional label text"); //child node for parent1
//        parent1.getChildren().addAll(label1,label2);


        Stage filterwindow = new Stage();
        filterwindow.initModality(Modality.NONE);

        Stage backgroundElevation = new Stage();
        createElevation(backgroundElevation);

        Stage filterStage = new Stage();

//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        scene.setFill(Color.TRANSPARENT);
//        primaryStage.setOpacity(0.2);
        primaryStage.show();
    }

    private static ElevationGrid elevationGrid; //this code is here for testing purposes, need to find a way to parse

    public void createElevation(Stage backgroundElevation){


        FileReader fr = new FileReader("./data/"); //this code is here for testing purposes, need to find a way to parse
        elevationGrid = fr.getElevation(); //this code is here for testing purposes, need to find a way to parse elevgrid

        //Creating a writable image
        WritableImage wImage = new WritableImage(elevationGrid.getDimX(), elevationGrid.getDimY());

        //getting the pixel writer
        PixelWriter writer = wImage.getPixelWriter();

        //for creating bounds for the greyscale 0-255
        float greyscaleValue =0;
        float mingrey =255;
        float maxgrey =0;
        int resolution = 6;
        boolean contourline =false;
        int contourgap = (int)((elevationGrid.getMaxHeight()-elevationGrid.getMinHeight())/25);

        for(int y = 0; y < elevationGrid.getDimY(); y++) {
            for(int x = 0; x < elevationGrid.getDimX(); x++) {
                //create bounds for the greyscale 0-255
                greyscaleValue = ((elevationGrid.getElevation(x,y)-elevationGrid.getMinHeight())/(elevationGrid.getMaxHeight()-elevationGrid.getMinHeight())) * (255);
                if (greyscaleValue<mingrey){mingrey=greyscaleValue;}
                if (greyscaleValue>maxgrey){maxgrey=greyscaleValue;}

                //code for adding contour lines (bit buggy, lines aren't always thin)
//                if (((int)greyscaleValue%contourgap)==0){
//                    contourline=true;
//                }

                //reducing the resolution of the image and adding granularity
                greyscaleValue = (int)(greyscaleValue/resolution);
                greyscaleValue = greyscaleValue*resolution;

                //Setting the color to the writable image
                if (contourline){ //makes it black if it's a contour line
                    writer.setColor(x, y, Color.BLACK);
                    contourline=false;
                }
                else{ //otherwise it paints it into the correct grey colour
                    writer.setColor(x, y, Color.grayRgb((int)(greyscaleValue)));
                }



                }
        }
        System.out.println("min grey ="+mingrey+" max grey="+maxgrey);
        System.out.println("min elevation ="+elevationGrid.getMinHeight()+" max elevation="+elevationGrid.getMaxHeight());

        //Setting the view for the writable image
        ImageView imageView = new ImageView(wImage);

        //Creating a Group object
        Group root = new Group(imageView);

        //Creating a scene object
        Scene elevationScene = new Scene(root, elevationGrid.getDimX(), elevationGrid.getDimY());

        //Setting title to the Stage
        backgroundElevation.setTitle("Elevation Layer");

        //Adding scene to the stage
        backgroundElevation.setScene(elevationScene);

        //Displaying the contents of the stage
        backgroundElevation.show();

        //for testing purposes:
        System.out.println("last colour value:"+greyscaleValue);

    }

    @Override
    public void stop() throws Exception {
        System.out.println("after UI run");
    }

    public void OpenFilterButtonClicked(MouseEvent mouseEvent) throws IOException {
        //show the hidden filter pane
    }
}
