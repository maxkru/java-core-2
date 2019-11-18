public class MyArrayDataException extends Exception {

    private int firstIndex, secondIndex;

    public MyArrayDataException(int firstIndex, int secondIndex) {
        super("Incorrect data in [" + firstIndex + "][" + secondIndex + "]");
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getSecondIndex() {
        return secondIndex;
    }
}
