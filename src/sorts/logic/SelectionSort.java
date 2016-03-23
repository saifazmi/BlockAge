package sorts.logic;

import java.util.ArrayList;

/**
 * @author : Evgeniy Kim; Contributors - Evgeniy Kim
 * @version : 23/03/2016;
 *          <p>
 *          SelectionSort with use of SortableComponent, making it possible to visuailze at SortVisual class
 * @date : 19/02/16
 */
public class SelectionSort {
    /**
     * Selection sort
     * Partitions list into sorted and unsorted halves, taking elements from the unsorted half and
     * placing them in the sorted, in it's correct position.
     *
     * @param state ArrayList which contains a randomly ordered set of numbers
     * @return ArrayList of ArrayLists , all possible states
     */
    public static ArrayList<SortableComponent> sort(ArrayList<Integer> state) {

        ArrayList<SortableComponent> allStates = new ArrayList<>();
        int size = state.size();
        int x;
        int y;

        for (y = 0; y < size - 1; y++) {

            int minimum = y;

            for (x = y + 1; x < size; x++) {

                SortableComponent s = new SortableComponent(getByValue(state), minimum, x, false);
                allStates.add(s);

                if (state.get(x) < state.get(minimum)) {

                    minimum = x;
                    SortableComponent c = new SortableComponent(getByValue(state), y, x, true);
                    allStates.add(c);
                }
            }

            //swapping
            if (minimum != y) {

                Integer temp = state.get(y);
                state.set(y, state.get(minimum));
                state.set(minimum, temp);
                SortableComponent s = new SortableComponent(getByValue(state), minimum, y, true);
                allStates.add(s);
            }
        }

        return allStates;
    }

    /**
     * Pass by value (hard copy)
     *
     * @param list the list to copy
     * @return the copy
     */
    public static ArrayList<Integer> getByValue(ArrayList<Integer> list) {

        return new ArrayList<>(list);
    }
}
