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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EcovisMainUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        System.out.println("before UI run");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 300, 275));
        VBox parent1 = new VBox();
//        Label label1 = new Label("Some label text"); //child node for parent1
//        Label label2 = new Label("Some additional label text"); //child node for parent1
//        parent1.getChildren().addAll(label1,label2);


        Scene firstScene = new Scene(parent1);
        primaryStage.setHeight(500);
        primaryStage.setWidth(600);
        primaryStage.setScene(firstScene);primaryStage.show();


        Stage filterwindow = new Stage();
        filterwindow.initModality(Modality.NONE);

    }


    public void createElevation(Stage backgroundElevation){

        //Creating a writable image
        WritableImage wImage = new WritableImage(ElevationGrid.getDimX, ElevationGrid.getDimY);

        //getting the pixel writer
        PixelWriter writer = wImage.getPixelWriter();

        //for creating bounds for the greyscale 0-255
        int greyscaleValue =0;

        for(int y = 0; y < ElevationGrid.getDimY; y++) {
            for(int x = 0; x < ElevationGrid.getDimX; x++) {
                //create bounds for the greyscale 0-255
                greyscaleValue = ((ElevationGrid.getElevation(x,y)-ElevationGrid.getMinHeight)/(ElevationGrid.getMaxHeight-ElevationGrid.getMinHeight)) * (255);
                //Setting the color to the writable image
                writer.setColor(x, y, Color.grayRgb(greyscaleValue);
                }
        }

        //Setting the view for the writable image
        ImageView imageView = new ImageView(wImage);

        //Creating a Group object
        Group root = new Group(imageView);

        //Creating a scene object
        Scene elevationScene = new Scene(root, 600, 500);

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
}
