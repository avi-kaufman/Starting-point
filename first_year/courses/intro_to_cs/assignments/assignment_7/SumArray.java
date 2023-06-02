  class SumArray {

    public static void main(String args[]) {
        // Put your testing here. For example:
        int a[] = { 1, 2, 3, 4 };
        System.out.println(sumArr(a));
    }

    // Returns the sum of the elements in the given array.
    public static int sumArr(int arr[]) {
        return sumArr(arr, arr.length -1);
    }
  
    // Returns the sum of the elements in the given array, up to, and including, element n.
    // Assumes that n is nonnegative.
    private static int sumArr(int arr[], int n) {
        if (n == 0) {
            return arr[0];
        }
        return arr[n] + sumArr(arr, n -1);
    } 
} 