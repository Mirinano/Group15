/*
 * Title      : MapData.java
 * Description: ゲームマップデータの作成、保持を行う
 * Encoding   : UTF-8
*/

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {
    public static final int TYPE_NONE   = 0;
    public static final int TYPE_WALL   = 1;
    public static final int TYPE_OTHERS = 2;
    private static final String mapImageFiles[] = {
        "png/SPACE.png",
        "png/WALL.png",
        "png/item.png"  //「not used」じゃなくなった。
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width;
    private int height;
    //コンストラクタ
    MapData(int x, int y){
        mapImages     = new Image[3];
        mapImageViews = new ImageView[y][x];
//        for (int i=0; i<2; i++) {
        // 配列に画像を登録
        for (int i=0; i<3; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }
        // 高さ、幅を保存
        width  = x;
        height = y;
        // mapsは2次配列
        maps = new int[y][x];
        // 一度、すべてを壁で埋める
        fillMap(MapData.TYPE_WALL);
        // 路を掘る
        digMap(1, 3);

        //アイテム設置
        for (int xx,yy,count=0; count <2;  ) {
            //横20のランダム整数生成
            xx = (int)(Math.random()*20);
            //縦14のランダム整数作成
            yy = (int)(Math.random()*14);
            if (maps[yy][xx] ==MapData.TYPE_NONE) { //ランダムの座標が路であれば…
                maps[yy][xx] =MapData.TYPE_OTHERS; //アイテムで上書き
                count++; //設置できたのでカウントを増設
            } //設置できなければ再トライ
        }
        //ゴールには必ず置いたるか
        maps[13][19] = MapData.TYPE_OTHERS;
        //アイテム設置おわり。
        //マップデータができたので画像を表示する
        setImageViews();
    }

    //ゲッター(高さ)
    public int getHeight(){
        return height;
    }
    
    //ゲッター(幅)
    public int getWidth(){
        return width;
    }

    //(x, y)座標が壁か、道か、アイテムかを数字で返す。
    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    //(x, y)座標の画像を返す。
    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    //(x, y)座標のアイテムの画像を路に変更する。
    public void clr_item_image(int x, int y ) {//call from MoveChara
        maps[y][x] =MapData.TYPE_NONE;
        mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
    }

    //(x, y)座標を上書きする。セッター。
    public void setMap(int x, int y, int type){
        if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
            return;
        }
        maps[y][x] = type;
    }

    //画像を表示する。
    public void setImageViews() {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
            }
        }
    }

    //typeに指定したものですべての座標を埋める(一括変更)
    public void fillMap(int type){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                maps[y][x] = type;
            }
        }
    }

    /*
     * 穴掘り式の迷路生成アルゴリズム
     * (x, y)から堀進める。
     * 上下左右方向2マスが、壁であれば、そちらへ2マス掘る。
     * 堀進めた先でも同様に上下左右をチェックし、掘れなくなるまで進める。
     * 掘れるところがなくなったら、戻って掘れるところを探す。
     * 一切掘れなくなったら終わり。
    */
    public void digMap(int x, int y){
        setMap(x, y, MapData.TYPE_NONE);
        int[][] dl = {{0,1},{0,-1},{-1,0},{1,0}};
        int[] tmp;

        for (int i=0; i<dl.length; i++) {
            int r = (int)(Math.random()*dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i=0; i<dl.length; i++){
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
                setMap(x+dx, y+dy, MapData.TYPE_NONE);
                digMap(x+dx*2, y+dy*2);

            }
        }
    }

    // データを出力。マップを表示する。
    public void printMap(){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                if (getMap(x,y) == MapData.TYPE_WALL){
                    System.out.print("++");
                }else{
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }
}
