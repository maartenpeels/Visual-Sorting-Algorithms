package algorithms;

import helpers.SortingArray;

public class RadixSort implements Algorithm {
    @Override
    public SortingArray run(SortingArray array) {
        for(int i = 1; i <= 1000000000; i *= 10) {
            if(array.isSorted()) continue;
            array.setProc_values(countingSort(array, array.getProc_values(), i));
        }

        return array;
    }

    private int[] countingSort(SortingArray arr, int[] proc_values, int place) {
        int[] out = new int[proc_values.length];
        int[] count = new int[10];

        for(int i=0; i < proc_values.length; i++){
            int digit = getDigit(proc_values[i], place);
            arr.mark(i);
            count[digit] += 1;
        }

        for(int i=1; i < count.length; i++){
            count[i] += count[i-1];
        }

        for(int i = proc_values.length-1; i >= 0; i--){
            int digit = getDigit(proc_values[i], place);
            arr.mark(i);

            out[count[digit]-1] = proc_values[i];
            arr.set(count[digit] - 1, proc_values[i]);
            count[digit]--;
        }

        return out;
    }

    private int getDigit(int value, int digitPlace) {
        return ((value/digitPlace ) % 10);
    }
}
