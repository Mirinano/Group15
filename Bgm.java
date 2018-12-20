import java.io.File;
import javafx.scene.media.Media;
import java.net.MalformedURLException;
import javafx.scene.media.AudioClip;
//import javafx.scene.media.MediaPlayer;


//メニュー、ゲーム中の BGM
//ワープ音は別プレーヤなので MapGameController::jumpで実装
public class    Bgm {

    private static AudioClip mplayer =new AudioClip(new File("./wav/menu.wav").toURI().toString());
    //↑static忘れずに。何曲も再生されることに…(別プレーヤになる)

    //メニュー画面
    public void menu() {//call from GameStartController::Initializable
        mplayer.stop();
        mplayer =new AudioClip(new File("./wav/menu.wav").toURI().toString());
        mplayer.setCycleCount(AudioClip.INDEFINITE);//無限loop
        //↑を↓より先に記載しないと効かない。
        mplayer.play();
    }

    //ゲーム中
    public void game() {//call from MapGameController::Initializable
        mplayer.stop();
        mplayer =new AudioClip(new File("./wav/run.wav").toURI().toString());
        mplayer.setCycleCount(AudioClip.INDEFINITE);//無限loop
        //↑を↓より先に記載しないと効かない。
        mplayer.play();
    }

    //↓SE
    //ジャンプ音
    public void jump() {//call from MapGameController::jump()
        //これは別プレーヤ
        AudioClip oto =new AudioClip(new File("./wav/jump.wav").toURI().toString());
        oto.play();
    }

    //アイテムゲット
    public void item() {//call from MapGameController::jump()
        //これは別プレーヤ
        AudioClip oto =new AudioClip(new File("./wav/item.wav").toURI().toString());
        oto.play();
    }

    //ゴール
    public void goal() {//call from MapGameController
        mplayer.stop();
        mplayer =new AudioClip(new File("wav/fanfare1.wav").toURI().toString());
        mplayer.play();
    }

}
