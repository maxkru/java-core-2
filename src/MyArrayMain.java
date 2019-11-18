public class MyArrayMain {

    public static int sumStringArray(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        int result = 0;

        if(arr.length != 4)
            throw new MyArraySizeException("Array size must be 4x4");
        for(int i = 0; i < arr.length; i++) {
            if (arr[i].length != 4)
                throw new MyArraySizeException("Array size must be 4x4");
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    result += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String[][] testArr = new String[][]{
                {"1", "2\n0", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}};

        try {
            int sum = sumStringArray(testArr);
            System.out.println("Сумма элементов массива: " + sum);
        } catch (MyArraySizeException e) {
            System.out.println("Некорректный размер массива");
        } catch (MyArrayDataException e) {
            System.out.println("В ячейке [" + e.getI() + "][" + e.getJ() + "] строка, не приводимая к int.");
            System.out.println("Содержимое ячейки: " + testArr[e.getI()][e.getJ()]);
        }
    }

}
