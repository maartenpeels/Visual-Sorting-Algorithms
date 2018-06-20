package algorithms;

import java.util.Collections;

import helpers.SortingArray;

public class BadSort implements Algorithm{
    @Override
    public SortingArray run(SortingArray array) {
        //Collections.reverse(array.getProc_values());
        array.lessThan(1,2);
        array.swap(5, 8);
        return array;
    }
}
