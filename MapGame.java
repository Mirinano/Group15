//Emacs customization file -*- mode:java; coding:utf-8-unix -*-

import javafx.application.Application;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

//import java.io.File;
//import javafx.scene.media.Media;
//import java.net.MalformedURLException;
//import javafx.scene.media.AudioClip;
////import javafx.scene.media.MediaPlayer;


public class MapGame extends Application {

//    Stage stage;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
//        primaryStage.setTitle("MAP GAME");
        stage.setTitle("MAP GAME");
        changeView("GameStart.fxml");
//        changeView("MapGame.fxml");
//        Pane myPane_top = (Pane)FXMLLoader.load(getClass().getResource("MapGame.fxml"));
//        Scene myScene = new Scene(myPane_top);
//        primaryStage.setScene(myScene);
//        primaryStage.show();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }



    //画面遷移のため↑start()から分離
    //call from here & GameStartController::func1ButtonAction
    public void changeView(String fxml) {

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxml))));
        } catch (IOException ex) {
            System.out.println("changeView"+":"+fxml);
        }
    }
}
