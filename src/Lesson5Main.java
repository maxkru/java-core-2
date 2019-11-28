import java.util.Arrays;

public class Lesson5Main {
    static final int size = 10000000;

    public static void main(String[] args) {
        float[] arr = new float[size];
        parallelled(arr,3);
    }

    private static void unParallelled(float[] arr) {
        Arrays.fill(arr, 1.0f);

        long a = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Unparallelled: " + (System.currentTimeMillis() - a) + " ms");
    }

    private static void parallelled(float[] arr, int nOfThreads)  {
        if (arr.length % nOfThreads != 0)
            throw new IllegalArgumentException("\'nOfThreads\' must divide \'arr.length\' without remainder");

        Arrays.fill(arr, 1.0f);
        int h = arr.length/nOfThreads;
        long a = System.currentTimeMillis();
        float[][] aParts = new float[nOfThreads][h];
        Thread[] threads = new Thread[nOfThreads];
        for (int i = 0; i < nOfThreads; i++) {
            System.arraycopy(arr, i * h, aParts[i], 0, h);
            int tNumber = i;
            threads[tNumber] = new Thread(() -> {
                int cellOffset = tNumber * h;
                for (int j = 0; j < h; j++)
                    aParts[tNumber][j] = (float)(aParts[tNumber][j] * Math.sin(0.2f + (cellOffset+j) / 5) * Math.cos(0.2f + (cellOffset+j) / 5) * Math.cos(0.4f + (cellOffset+j) / 2));
            });
            threads[tNumber].start();
        }

        for (int i = 0; i < nOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }

            System.arraycopy(aParts[i], 0, arr, i*h, h);
        }

        System.out.println("Parallelled, " + nOfThreads + " threads: " + (System.currentTimeMillis() - a) + " ms");
    }
}
