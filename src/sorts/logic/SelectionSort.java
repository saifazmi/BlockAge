package sorts.logic;

import java.util.ArrayList;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 * <p>
 * SelectionSort with use of SortableComponent, making it possible to visuailze at SortVisual class
 */
public class SelectionSort {
    /**
     * Selection sort
     * Partitions list into sorted and unsorted halves, taking elements from the unsorted half and
     * placing them in the sorted, in it's correct position.
     *
     * @param n ArrayList which contains a randomly ordered set of numbers
     * @return ArrayList of ArrayLists , all opssible states
     */
    public static ArrayList<SortableComponent> sort(ArrayList<Integer> n) {
        ArrayList<Integer> state = n;
        int size = state.size();
        ArrayList<SortableComponent> allStates = new ArrayList<>();

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
            if (minimum != y) { //swapping
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
     * Pass by value
     *
     * @param list
     * @return
     */
    public static ArrayList<Integer> getByValue(ArrayList<Integer> list) {
        return new ArrayList<>(list);
    }
}
