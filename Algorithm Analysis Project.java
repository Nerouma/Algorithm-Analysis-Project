import java.util.Arrays;
import java.util.concurrent.RecursiveTask;
import java.until.concurrent.ForkJoinPool;

class ParallelMergeSort extends RecursiveTask<int[]>{
  private int [] myArray;

  public ParallelMergeSort(int [] myArray){
    this.myArray = myArray;
  }
@Override
  protected int [] compute(){
    if(myArray.length <= 16) {
      Arrays.sort(myArray)
      return myArray;
    }
    int mid = array.length/2;
    int [] left = Arrays.copyOFRange(array, 0, mid);
    int [] right = Arrays.copyOFRange(array, mid, array.length);

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
