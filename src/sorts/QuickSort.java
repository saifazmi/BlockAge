package sorts;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Caitlyn PLS on 19-Feb-16
 * WORKS
 * Underlying IDEA: SortableComponent comes in sets of 3. one for left side, one for pivot, one for right
 * Do with it what you will, i thnk this is safest
 */

public class QuickSort {
    ArrayList allStates = new ArrayList<SortableComponent>();

    /**
     * Pre-quicksort formatting
     *
     * @param size
     * @return
     */
    public ArrayList<Integer> sort(int size) {
        if (size < 3) {
            System.out.println("SORT: Input too low, returned null");
            return null;
        }
        //return list

        //generate  numbers
        ArrayList state = new ArrayList<Integer>();
        for (int x = 0; x < size; x++) {
            state.add(x);
        }
        Collections.shuffle(state);

        ArrayList<Integer> out = quickSort(state);
        return allStates;

    }

    /**
     * Code taken and edited from User djitz on GitHub
     * Pointers on pivot
     *
     * @param input
     * @return
     */
    private ArrayList<Integer> quickSort(ArrayList<Integer> input) {

        if (input.size() <= 1) {
            return input;
        }

        int middle = (int) Math.ceil((double) input.size() / 2);
        int pivot = input.get(middle);

        ArrayList<Integer> less = new ArrayList<Integer>();
        ArrayList<Integer> greater = new ArrayList<Integer>();

        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) <= pivot) {
                if (i == middle) {
                    continue;
                }
                less.add(input.get(i));
            } else {
                greater.add(input.get(i));
            }
        }
        SortableComponent s = new SortableComponent(less, pivot, pivot, false);
        ArrayList qp = new ArrayList<Integer>();
        qp.add(pivot);
        SortableComponent x = new SortableComponent(qp, pivot, pivot, false);
        SortableComponent y = new SortableComponent(greater, pivot, pivot, false);
        System.out.println("Adding");

        allStates.add(s);
        allStates.add(x);
        allStates.add(y);
        return concatenate(quickSort(less), pivot, quickSort(greater));
    }

    /**
     * Code taken and edited from User djitz on GitHub
     *
     * @param less
     * @param pivot
     * @param greater
     * @return
     */
    private ArrayList<Integer> concatenate(ArrayList<Integer> less, int pivot, ArrayList<Integer> greater) {

        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < less.size(); i++) {
            list.add(less.get(i));
        }

        list.add(pivot);

        for (int i = 0; i < greater.size(); i++) {
            list.add(greater.get(i));
        }

        return list;
    }


}