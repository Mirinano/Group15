public class Item {
    private static int x;
    private static int y;
    private static int num;
    private static boolean exist;

    Item(int x, int y, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
        this.exist = true;
    }

    public boolean ExistItem(int x, int y) {
        if ((this.x == x) & (this.y == y)) {
            ChangeExist();
            return true;
        } else {
            return false;
        }
    }

    public boolean checkget() {
        return exist;
    }

    public void ChangeExist() {
        this.exist = false;
    }
}