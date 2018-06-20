package algorithms;

import helpers.SortingArray;

public class InsertionSort implements Algorithm{
    @Override
    public SortingArray run(SortingArray array) {
        for (int i = 1; i < array.getSize(); i++) {
            for(int j = i; j > 0 && array.lessThan(j, j - 1); j--) {
                array.swap(j, j - 1);
            }
        }

        return array;
    }
}
