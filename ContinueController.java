import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class ContinueController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ContinueButton;


    @FXML
    void BackMenuButtonAction(Event event) {
        new MapGame().changeView("GameStart.fxml");
        //スタート画面に遷移
    }

    @FXML
    void ContinueButtonAction(ActionEvent event) {
        new MapGame().changeView("MapGame.fxml");
        //再度ゲームスタート
    }

    @FXML
    void initialize() {
        assert ContinueButton != null : "fx:id=\"ContinueButton\" was not injected: check your FXML file 'Continue.fxml'.";


    }

}
