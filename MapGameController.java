// Emacs customization file -*- mode:java; coding:utf-8-unix -*-

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

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
    // public Group[] mapGroups;

    //スコアのラベル追加
    @FXML private Label   label_tm,label_im, label_tl;
    //スコアタイマ関係
    private static Timeline tmr;
    private int total=0, kill=0;

    //ゴール
    private boolean get_goal =false;


    private Bgm bgm =new Bgm();


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

//        new Bgm().game();//BGM開始
        bgm.game();//BGM開始

        tmr =//スコア時間カウント開始
        new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                public void handle(ActionEvent actionEvent) {
                    //ここに書いた処理が周期的に実行される
                    total++;
                    label_tm.setText(String.valueOf(total/60) +":"+ String.valueOf(total%60));

                    //無限設定(Timeline.INDEFINITE)だとメモリリークする(?)らしいのでやっておく
                    if (++kill >10) {
//         System.out.println("kill");
                        kill=0;tmr.stop();tmr.play();}
                }
            }
                )
            );
        tmr.setCycleCount(Timeline.INDEFINITE);
        tmr.play();
    }

    public void mapPrint(MoveChara c, MapData m) {
        int cx = c.getPosX();
        int cy = c.getPosY();
        int gx = c.getPosgX();
        int gy = c.getPosgY();
        mapGrid.getChildren().clear();
        for (int y = 0; y < mapData.getHeight(); y++) {
            for (int x = 0; x < mapData.getWidth(); x++) {
                int index = y * mapData.getWidth() + x;
                if (x == cx && y == cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
                if (x == gx && y == gy) {
                    chara.ghost();
                    mapGrid.add(c.getGhostlImageView(), gx, gy);

                }
            }
        }
    }

    public void func1ButtonAction(ActionEvent event) {
//アイテムラベルの例
        label_im.setText(String.valueOf(Integer.parseInt(label_im.getText()) +1));
    }

    public void func2ButtonAction(ActionEvent event) {
//トータルラベルの例
        label_tl.setText(String.valueOf(Integer.parseInt(label_tl.getText()) +100));
    }

    public void func3ButtonAction(ActionEvent event) {
    }

    public void func4ButtonAction(ActionEvent event) {
        tmr.stop();
        new MapGame().changeView("GameStart.fxml");
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
        System.out.println("Select Action: " + actionString +" x:"+chara.getPosX() +" y:"+chara.getPosY());
    }

    public void downButtonAction() {

        if (get_goal) return;

        if (jump(0,1,MoveChara.TYPE_DOWN) ==false) {
            //↓ジャンプしなかったら
            chara.setCharaDir(MoveChara.TYPE_DOWN);
            chara.move(0, 1);
            chara.moveghost(0, 1);
            outputAction("DOWN");
        }
        mapPrint(chara, mapData);
        goal_check();
    }

    public void downButtonAction(ActionEvent event) {
        downButtonAction();
    }

    public void rightButtonAction() {

        if (get_goal) return;

        if (jump(1,0,MoveChara.TYPE_RIGHT) ==false) {
            //↓ジャンプしなかったら
            chara.setCharaDir(MoveChara.TYPE_RIGHT);
            chara.move(1, 0);
            chara.moveghost(-1, 0);
            outputAction("RIGHT");
        }
        mapPrint(chara, mapData);
        goal_check();
    }

    public void rightButtonAction(ActionEvent event) {
        rightButtonAction();
    }

    public void leftButtonAction() {

        if (get_goal) return;

        if (jump(-1,0,MoveChara.TYPE_LEFT) ==false) {
            //↓ジャンプしなかったら
            chara.setCharaDir(MoveChara.TYPE_LEFT);
            chara.move(-1, 0);
            chara.moveghost(1, 0);
            outputAction("LEFT");
        }
        mapPrint(chara, mapData);
        goal_check();
    }

    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }

    public void upButtonAction() {

        if (get_goal) return;

        if (jump(0,-1,MoveChara.TYPE_UP) ==false) {
            //↓ジャンプしなかったら
            chara.setCharaDir(MoveChara.TYPE_UP);
            chara.move(0, -1);
            chara.moveghost(0, 1);
            outputAction("UP");
        }
        mapPrint(chara, mapData);
        goal_check();
    }

    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }


    //ジャンプ(↓privateなので、このクラス↑だけで使用)
    private boolean  jump(
                         int dx,    //次の移動先の相対座標
                         int dy,    //次の移動先の相対座標
                         int type   //chara.setCharaDir()に渡すため
                         ) {
        //進行方向が壁の場合はジャンプさせると変な感じなのでやらない。
        int ret_can=chara.canMove(dx,dy);
        if (ret_can ==0) return false;

        if (ret_can ==1) {
            //アイテムだった時
            //マップデータは chara.canMove()で修正してあるので、ここでは
            //mapImageViews[]を再構築する。これで以降呼ばれる mapPrint()では
            //アイテム→通路になる。
            for (int y = 0; y < mapData.getHeight(); y++) {
                for (int x = 0; x < mapData.getWidth(); x++) {
                    int index = y * mapData.getWidth() + x;
                    mapImageViews[index] = mapData.getImageView(x, y);
                }
            }
            //↑同じ事を initialize()でもやってるのでまとめたいところだが…まぁーいいや

            bgm.item();//アイテムゲット音

            //アイテムスコア +10
            label_im.setText(String.valueOf(Integer.parseInt(label_im.getText()) +10));
        }

        // 1/10の確立で jump発生
        if ((int)(Math.random()*10) !=5) return false;
        //但し↓で発生させた飛先の座標によっては壁かもしれない。
        //したがって、さらに確立は下がる。

        //x方向 21、y方向15の範囲でランダム生成
        int x = (int)(Math.random()*20);
        int y = (int)(Math.random()*14);
//   System.out.println("jumpX:"+x+" y"+y);
        if (chara.canMove2(x,y) ==true) {
            chara.setCharaDir(type);
            chara.move2(x,y);
            outputAction("JUMP");
//            mapPrint(chara, mapData);


            bgm.jump();//ジャンプ音

            return true;
        }
        return false;
    }

    private boolean  goal_check() {

        if (chara.getPosX() ==19 && chara.getPosY() ==13) {
            get_goal =true;//ねこを動かsなくする
            tmr.stop();     //時計停止
            bgm.goal();     //BGM停止 →ファンファーレ

            //ここで、どんな計算にするのかしらないが total pointを書けば良い
            label_tl.setText(String.valueOf(Integer.parseInt(label_tl.getText()) +10000));
            return true;
        }
        return false;
    }
}
