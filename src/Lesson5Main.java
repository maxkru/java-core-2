import java.util.Arrays;

public class Lesson5Main {
    static final int size = 10000000;

    public static void main(String[] args) {
        float[] arr = new float[size];
        float[] arr2 = new float[size];
        parallelled(arr,7);
        unParallelled(arr2);
        System.out.println(Arrays.equals(arr, arr2));
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
        Arrays.fill(arr, 1.0f);
        final int h = arr.length/nOfThreads;
        final int firstShortThread = arr.length % nOfThreads;
        long a = System.currentTimeMillis();
        float[][] aParts = new float[nOfThreads][h+1];
        Thread[] threads = new Thread[nOfThreads];
        for (int i = 0; i < nOfThreads; i++) {
            final boolean isLongThread = (i < firstShortThread);
            if (isLongThread) {
                System.arraycopy(arr, i * (h + 1), aParts[i], 0, h + 1);
            } else {
                System.arraycopy(arr, firstShortThread * (h + 1) + (i - firstShortThread) * h, aParts[i], 0, h);
            }
            final int tNumber = i;
            threads[tNumber] = new Thread(() -> {
                int cellOffset, iLimit;
                if (isLongThread) {
                    cellOffset = tNumber * (h + 1);
                    iLimit = h + 1;
                } else {
                    cellOffset = firstShortThread * (h + 1) + (tNumber - firstShortThread) * h;
                    iLimit = h;
                }
                for (int j = 0; j < iLimit; j++)
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
            if (i < firstShortThread) {
                System.arraycopy(aParts[i], 0, arr, i * (h + 1), h + 1);
            } else {
                System.arraycopy(aParts[i], 0, arr, firstShortThread * (h + 1) + (i - firstShortThread) * h, h);
            }

        }

        System.out.println("Parallelled, " + nOfThreads + " threads: " + (System.currentTimeMillis() - a) + " ms");
    }
}
