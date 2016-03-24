package sorts.logic;

import java.util.ArrayList;

/**
 * @author : Evgeniy Kim
 * @version : 23/03/2016;
 *          <p>
 *          InsertSort with a SortableComponent object which prepares the data to be
 *          more easily unpacked for visualisation at SortVisual class.
 * @date : 21/03/16
 */
public class InsertSort {

    /**
     * Simple, with inclusion of the abstract layer, which keeps track of all relevant states for animating
     *
     * @param state input list to sort
     * @return allStates relevant states used for swapping
     */
    public static ArrayList<SortableComponent> sort(ArrayList<Integer> state) {

        ArrayList<SortableComponent> allStates = new ArrayList<>();

        for (int x = 1; x < state.size(); x++) {

            int y = x;

            while (y > 0 && (state.get(y - 1) > state.get(y))) {

                SortableComponent s = new SortableComponent(getByValue(state), y - 1, y, false);

                //save state prior to a swap
                allStates.add(s);

                //swap y and y-1
                int temp = state.get(y - 1);
                state.set(y - 1, state.get(y));
                state.set(y, temp);
                SortableComponent sw = new SortableComponent(getByValue(state), y - 1, y, true);

                //save swap state
                allStates.add(sw);
                y = y - 1;
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
