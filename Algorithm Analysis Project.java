import java.util.Arrays;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class ParallelMergeSort extends RecursiveTask<int[]>{
  private int [] myArray;

  public ParallelMergeSort(int [] myArray){
    this.myArray = myArray;
  }
@Override
  protected int [] compute(){
    if(myArray.length <= 16) {
      Arrays.sort(myArray);
      return myArray;
    }
    int mid = myArray.length/2;
    int [] left = Arrays.copyOfRange(myArray, 0, mid);
    int [] right = Arrays.copyOfRange(myArray, mid, myArray.length);

    ParallelMergeSort leftRun = new ParallelMergeSort(left);
    ParallelMergeSort rightRun = new ParallelMergeSort(right);

    leftRun.fork();
    int [] sortedRight = rightRun.compute();
    int [] sortedLeft = leftRun.join();

    return merge(sortedLeft, sortedRight);
  }

  private int [] merge(int [] left, int [] right)
  {
    int [] merged = new int [left.length + right.length];
    int i = 0, j = 0, k = 0;

while (i < left.length && j < right.length) {
            merged[k++] = (left[i] < right[j]) ? left[i++] : right[j++];
        }

        while (i < left.length) merged[k++] = left[i++];
        while (j < right.length) merged[k++] = right[j++];

        return merged;
    }

  public static void main(String [] args){
    int [] myArray = {3, 6, 8, 10, 1, 2, 1};
    long startTime = System.nanoTime();
    ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    int [] sortedArray = pool.invoke(new ParallelMergeSort(myArray));
    long endTime = System.nanoTime();
    long duration = ((endTime - startTime)/1000000);
    System.out.println("Time taken: " + duration + " milliseconds");
    System.out.println(Arrays.toString(sortedArray));

    pool.close();
  }
}
