package sorts.logic;

import java.util.ArrayList;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 21/03/16, last edited by Evgeniy Kim on 21/03/16
 * <p>
 * InsertSort with a SortableComponent object which prepares the data to be
 * more easily unpacked for visualisation at SortVisual class.
 */
public class InsertSort {

    /**
     * Simple, with inclusion of the abstract layer, which keeps track of all relevant states for animating
     *
     * @param n input list to sort
     * @return allStates relevant states used for swapping
     */
    public static ArrayList<SortableComponent> sort(ArrayList<Integer> n) {
        ArrayList<Integer> state = n;
        ArrayList<SortableComponent> allStates = new ArrayList<>();

        for (int x = 1; x < state.size(); x++) {
            int y = x;
            while (y > 0 && (state.get(y - 1) > state.get(y))) {
                SortableComponent s = new SortableComponent(getByValue(state), y - 1, y, false);
                allStates.add(s); //save state prior to a swap
                //swap y and y-1
                int temp = state.get(y - 1);
                state.set(y - 1, state.get(y));
                state.set(y, temp);
                SortableComponent sw = new SortableComponent(getByValue(state), y - 1, y, true);
                allStates.add(sw); //save swap state
                y = y - 1;
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
