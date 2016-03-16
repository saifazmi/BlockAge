package sorts.logic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 * INCOMPLETE, unsure of a reliable way to go around it.
 */

public class QuickSort {
    ArrayList allStates = new ArrayList<SortableComponent>();

    /**
     * Pre-quicksort formatting, ON OLD 'input size' MODEL
     *
     * @param size
     * @return Arraylist of arraylists, all states of sort
     */
    public ArrayList<SortableComponent> sort(int size) {
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

        //added for testing
        SortableComponent b = new SortableComponent(state, 0, 0, false);
        allStates.add(b);

        ArrayList<Integer> out = quickSort(state);

        //added for testing
        SortableComponent a = new SortableComponent(out, 0, 0, false);
        allStates.add(a);

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