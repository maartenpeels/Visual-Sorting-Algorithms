package algorithms;

import java.util.ArrayList;

import helpers.SortingArray;

public class QuickSort implements Algorithm {
    @Override
    public SortingArray run(SortingArray array) {
        quickSort(array, 0, array.getSize()-1);

        return array;
    }

    private void quickSort(SortingArray arr, int left, int right) {
        if (left >= right) return;

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr,pivot + 1, right);
    }

    private int partition(SortingArray arr, int left, int right) {
        int pivot = getPivot(arr, left, right);
        arr.swap(pivot, right);

        pivot = left;
        for(int i = left; i < right; i++) {
            if(arr.lessThan(i, right)) {
                if(i != pivot) {
                    arr.swap(i, pivot);
                }
                pivot++;
            }
        }
        arr.swap(right, pivot);

        return pivot;
    }

    private int getPivot(SortingArray arr, int left, int right) {
        int pivot = 0;
        if(left + 1 == right) {
            return left;
        }else{
            int middle = Math.round((left + right) / 2);
            boolean LM = arr.lessThan(left, right);
            boolean MR = arr.lessThan(middle, right);

            if(LM == MR){
                return middle;
            }else if(LM){
                return arr.lessThan(left, right) ? right : left;
            }else {
                return arr.lessThan(left, right) ? left : right;
            }
        }
    }
}
