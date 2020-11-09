package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
     static  String url="jdbc:sqlite:C:/Users/liner/Documents/SD/SD2020E/Train.db";
     static TrainModel TDB=new TrainModel(url);
    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller control=new Controller(TDB);
        TrainView view=new TrainView(TDB, control);
        control.setView(view);

        primaryStage.setTitle("Train Form");
        primaryStage.setScene(new Scene(view.asParent(), 600, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
