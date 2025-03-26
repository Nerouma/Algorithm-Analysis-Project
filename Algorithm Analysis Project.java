import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class ParallelMergeSort extends RecursiveTask<int[]>{
  private int [] myArray;

  public ParallelMergeSort(int [] myArray){
    this.myArray = myArray;
  }
@Override
  protected int [] compute(){
    if(myArray.length <= 1000) {
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
  
  public void randomArrayandMerge(int size){
    int[] randomArray = new int[size]; // Create an array of the specified size
    Random random = new Random();

    // Fill the array with random integers
    for (int i = 0; i < size; i++) {
        randomArray[i] = random.nextInt(100000); // Generates random integers between 0 (inclusive) and 100 (exclusive)
    }
    long startTime = System.nanoTime();
    ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    int [] sortedArray = pool.invoke(new ParallelMergeSort(randomArray));
    long endTime = System.nanoTime();
    long duration = ((endTime - startTime));
    pool.close();
    if (size <=1){
      return;
    }
    else{
      System.out.println("Time taken: " + duration + " milliseconds");
    }
    
    
  }
  public static void main(String [] args){
    ParallelMergeSort pms = new ParallelMergeSort(new int[0]);
    pms.randomArrayandMerge(1);
    pms.randomArrayandMerge(10);
    pms.randomArrayandMerge(100);
    pms.randomArrayandMerge(1000);
    pms.randomArrayandMerge(10000);
    pms.randomArrayandMerge(100000);
    pms.randomArrayandMerge(1000000);
    pms.randomArrayandMerge(10000000);
    pms.randomArrayandMerge(100000000);
  }
}
