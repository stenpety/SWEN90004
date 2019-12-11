package con02.tute;

import java.util.Random;
import java.util.Arrays;

/**
 * Quicksort, with insertion sort for small segments
 */

public class ConQuick extends Thread {

  private static int ARRAY_SIZE = 2000; 

  private int [] a;
  private int lo, hi;

  public ConQuick(int[] a, int lo, int hi) {
 
    this.a = a;
    this.lo = lo;
    this.hi = hi;
  }

  public void run() {
 
    // quicksort until the array is largely sorted
    if (lo + 60 < hi) {
      int pivot = partition(lo, hi);

      // sort the lower side of the array
      new ConQuick(a, lo, pivot-1).run();

      // sort the higher side of the array
      ConQuick lowSeqQuick = new ConQuick(a, pivot+1, hi);
      lowSeqQuick.start();
    }
    // finish with a quick insertion sort
    insertionsort(lo, hi);
  }

  private int partition(int lo, int hi) {
  
    // use median-of-three partitioning
    int mid = (int) (lo+hi)/2;
    int indexOfLargest = hi;
    int t, i, j, median;

    if (a[mid] > a[indexOfLargest]) {
      indexOfLargest = mid;
    }
    if (a[lo] > a[indexOfLargest]) {
      indexOfLargest = lo;
    }
    if (indexOfLargest != hi) {
      t = a[hi];
      a[hi] = a[indexOfLargest];
      a[indexOfLargest] = t;
    }
    
    // bring the median of a[lo], a[mid], a[hi] into the lo position
    if (a[lo] < a[mid]) {
      t = a[mid];
      a[mid] = a[lo];
      a[lo] = t;
    }

    // start partitioning
    median = a[lo];
    i = lo;
    j = hi + 1;
    do {
      do { i++; } while (a[i] < median);
      do { j--; } while (a[j] > median);
      t = a[i];
      a[i] = a[j];
      a[j] = t;
    } while (i < j);

    // bring the median element to the pivot position j
    t = a[i];
    a[i] = a[j];
    a[j] = a[lo];
    a[lo] = t;

    return j;
  }
  
  private void insertionsort(int lo, int hi) {

    int i, j, v;

    for (i = 1; i <= hi; i++) {
      v = a[i];
      j = i-1;
      while (j >= 0 && v < a[j]) {
        a[j+1] = a[j];
        j--;
      }
      a[j+1]= v;
    }
  }

  public static void main(String [] args) {
    //create an array 
    int [] a = new int[ARRAY_SIZE];
    Random rand = new Random();
    
    // populate the array with random integers
    for(int i = 0; i < a.length; i++) {
      a[i] = rand.nextInt(10000);
    }

    // sort the array
    ConQuick mainSeqQuick = new ConQuick(a, 0, a.length-1);
    mainSeqQuick.run();
  
    System.out.println(Arrays.toString(a));
  }

}
