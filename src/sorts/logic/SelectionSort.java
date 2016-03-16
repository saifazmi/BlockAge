package sorts.logic;

import java.util.ArrayList;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 *
 * SelectionSort with use of SortableComponent, making it possible to visuailze at SortVisual class
 */
public class SelectionSort {
    /**
     * Selection sort
     * Partitions list into sorted and unsorted halves, taking elements from the unsorted half and
     * placing them in the sorted, in it's correct position.
     *
     * @param state ArrayList which contains a randomly ordered set of numbers
     * @return ArrayList of ArrayLists , all opssible states
     */
    public static ArrayList<SortableComponent> sort(ArrayList<Integer> state) {
        int size = state.size();
        ArrayList<SortableComponent> allStates = new ArrayList<>();

        int i, j;
        for (j = 0;j<size-1;j++){
            int minimum = j;
            for (i=j+1;i<size;i++){
                SortableComponent s = new SortableComponent(getByValue(state), minimum, i, false);
                allStates.add(s);
                if (state.get(i) < state.get(minimum)){
                    minimum = i;
                    SortableComponent c = new SortableComponent(getByValue(state), j, i, true);
                    allStates.add(c);
                }
            }
            if (minimum != j){ //swapping
                Integer temp = state.get(j);
                state.set(j, state.get(minimum));
                state.set(minimum, temp);
                SortableComponent s = new SortableComponent(getByValue(state), minimum, j, true);
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
