// Emacs customization file -*- mode:java; coding:utf-8-unix -*-

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
//import javafx.scene.input.KeyCode;
//import javafx.scene.Group;

public class GameStartController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Bgm().menu();//メニュー BGM開始
    }


    public void func1ButtonAction(ActionEvent event) {
        new MapGame().changeView("MapGame.fxml");//ゲーム画面へ遷移
    }

    public void func2ButtonAction(ActionEvent event) {

        try {
//            System.out.println("tryの処理");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("catchの処理");
        } finally {
            System.out.println("finallyの処理");
        }
    }

    public void keyAction(KeyEvent event) {
/*
        KeyCode key = event.getCode();
        if (key == KeyCode.DOWN) {
        } else if (key == KeyCode.RIGHT) {
        } else if (key == KeyCode.LEFT) {
        } else if (key == KeyCode.UP) {
        }
 */
    }
}
