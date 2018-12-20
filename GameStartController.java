import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class GameStartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button GhostStart;


    @FXML
    void GhostButtonAction(Event event) {
        new MapGame().changeView("MapGame1.fxml");
    }

    @FXML
    void TimeAttackButtonAction(ActionEvent event) {
    }

    @FXML
    void WarpButtonAction(ActionEvent event) {
        new MapGame().changeView("MapGame.fxml");
    }

    @FXML
    void initialize() {
        assert GhostStart != null : "fx:id=\"GhostStart\" was not injected: check your FXML file 'GameStart.fxml'.";
		new Bgm().menu();

    }

}
