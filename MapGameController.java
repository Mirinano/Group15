import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;

import java.io.File;
import javafx.scene.media.Media;
import java.net.MalformedURLException;
import javafx.scene.media.AudioClip;

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
    // public Group[] mapGroups;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapData = new MapData(21, 15);
        chara = new MoveChara(1, 1, mapData);
        // mapGroups = new Group[mapData.getHeight()*mapData.getWidth()];
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
        mapPrint(chara, mapData);
    }

    public void mapPrint(MoveChara c, MapData m) {
        int cx = c.getPosX();
        int cy = c.getPosY();
        mapGrid.getChildren().clear();
        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int index = y * mapData.getWidth() + x;
                if (x == cx && y == cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }
    }

    public void func1ButtonAction(ActionEvent event) {
    }

    public void func2ButtonAction(ActionEvent event) {
    }

    public void func3ButtonAction(ActionEvent event) {
    }

    public void func4ButtonAction(ActionEvent event) {
        new MapGame().changeView("GameStart.fxml", 0);
    }

    public void keyAction(KeyEvent event) {
        KeyCode key = event.getCode();
        if (key == KeyCode.DOWN) {
            downButtonAction();
        } else if (key == KeyCode.RIGHT) {
            rightButtonAction();
        } else if (key == KeyCode.LEFT) {
            leftButtonAction();
        } else if (key == KeyCode.UP) {
            upButtonAction();
        }
    }

    public void outputAction(String actionString) {
        System.out.println("Select Action: " + actionString);
    }

    public void downButtonAction() {

        if (jump(0, 1, MoveChara.TYPE_DOWN) == false) {
            outputAction("DOWN");
            chara.setCharaDir(MoveChara.TYPE_DOWN);
            chara.move(0, 1);
        }
        chara.itemexist(chara.getPosX(), chara.getPosY());
        mapPrint(chara, mapData);
    }

    public void downButtonAction(ActionEvent event) {
        downButtonAction();
    }

    public void rightButtonAction() {

        if (jump(1, 0, MoveChara.TYPE_RIGHT) == false) {
            outputAction("RIGHT");
            chara.setCharaDir(MoveChara.TYPE_RIGHT);
            chara.move(1, 0);
        }
        chara.itemexist(chara.getPosX(), chara.getPosY());
        mapPrint(chara, mapData);
    }

    public void rightButtonAction(ActionEvent event) {
        rightButtonAction();
    }

    public void leftButtonAction() {

        if (jump(-1, 0, MoveChara.TYPE_LEFT) == false) {
            outputAction("LEFT");
            chara.setCharaDir(MoveChara.TYPE_LEFT);
            chara.move(-1, 0);
        }
        chara.itemexist(chara.getPosX(), chara.getPosY());
        mapPrint(chara, mapData);
    }

    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }

    public void upButtonAction() {

        if (jump(0, -1, MoveChara.TYPE_UP) == false) {
            outputAction("UP");
            chara.setCharaDir(MoveChara.TYPE_UP);
            chara.move(0, -1);
        }
        chara.itemexist(chara.getPosX(), chara.getPosY());
        mapPrint(chara, mapData);
    }

    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }

    public boolean jump(int dx, int dy, int type) {
        // 進行方向が壁の場合はワープさせると変な感じなのでやらない。
        if (chara.canMove(dx, dy) == false)
            return false;

        // 1/10の確立で jump発生
        if ((int) (Math.random() * 10) != 5)
            return false;
        // 但し↓で発生させた飛先の座標によっては壁かもしれない。
        // したがって、さらに確立は下がる。

        // x方向 21、y方向15の範囲でランダム生成
        int x = (int) (Math.random() * 21);
        int y = (int) (Math.random() * 15);
        // System.out.println("jumpX:"+x+" y"+y);
        if (chara.canMove2(x, y) == true) {
            outputAction("JUMP");
            chara.setCharaDir(type);
            chara.move2(x, y);
            // mapPrint(chara, mapData);

            AudioClip oto = new AudioClip(new File("./mp3/jump.mp3").toURI().toString());
            oto.play();

            return true;
        }
        return false;
    }
}
