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

    public boolean ExistItem(int dx, int dy) {
        if ((this.x == dx) & (this.y == dy)) {
            ChangeExist();
            System.out.println("get item.");
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
