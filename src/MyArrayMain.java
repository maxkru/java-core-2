public class MyArrayMain {

    public static int sumStringArray(String[][] arr) throws MyArraySizeException {
        int result = 0;

        if(arr.length != 4)
            throw new MyArraySizeException("Array size must be 4x4");
        for(int i = 0; i < arr.length; i++) {
            if (arr[i].length != 4)
                throw new MyArraySizeException("Array size must be 4x4");
        }

        return result;
    }

    public static void main(String[] args) {
        String[][] testArr = new String[][]{
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}};

        try {
            int a = sumStringArray(testArr);
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        }
    }

}
