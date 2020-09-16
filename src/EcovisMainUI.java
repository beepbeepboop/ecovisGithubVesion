import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
        Label label1 = new Label("Some label text"); //child node for parent1
        Label label2 = new Label("Some additional label text"); //child node for parent1
        parent1.getChildren().addAll(label1,label2);

        Scene firstScene = new Scene(parent1);
        primaryStage.setHeight(500);
        primaryStage.setWidth(600);
        primaryStage.setScene(firstScene);primaryStage.show();


        Stage filterwindow = new Stage();
        filterwindow.initModality(Modality.NONE);

    }

    @Override
    public void stop() throws Exception {
        System.out.println("after UI run");
    }
}
