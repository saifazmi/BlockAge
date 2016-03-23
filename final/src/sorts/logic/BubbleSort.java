package sorts.logic;

import java.util.ArrayList;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 * <p>
 * BubbleSort with a SortableComponent object which prepares the data to be
 * more easily unpacked for visualisation at SortVisual class.
 */
public class BubbleSort {

    /**
     * Simple bubble sort. Iterates through list until no swaps are made,
     * swapping every time it finds x < x+1.
     *
     * @param state ArrayList of integers
     * @return ArrayList of ArrayLists - representing all states every step of the sort
     */
    public static ArrayList<SortableComponent> sort(ArrayList<Integer> state) {

        // return list
        ArrayList<SortableComponent> allStates = new ArrayList<>();

        boolean swap = true;
        Integer temp;

        while (swap) { //while a swap occurs in an iteration

            swap = false;

            for (int x = 0; x < state.size() - 1; x++) { //compare all with adjacents

                // consider order, should show comparison first, then swap, so 2 states per swap,
                // 1 state per comparison and no swap
                SortableComponent s = new SortableComponent(getByValue(state), x, x + 1, swap);

                // if swap needs to occur, start swapping
                if (state.get(x) > state.get(x + 1)) {

                    allStates.add(s);
                    temp = state.get(x);
                    state.set(x, state.get(x + 1));
                    state.set(x + 1, temp);
                    swap = true;
                    SortableComponent sc = new SortableComponent(getByValue(state), x, x + 1, swap);
                    allStates.add(sc);
                }


            }
        }

        return allStates;

    }

    //@TODO: complete doc

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