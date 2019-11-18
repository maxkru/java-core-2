public class MyArrayDataException extends Exception {

    private int i, j;

    public MyArrayDataException(int i, int j) {
        super("Incorrect data in [" + i + "][" + j + "]");
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
