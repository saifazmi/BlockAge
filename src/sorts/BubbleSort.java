package sorts;

import java.util.ArrayList;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 */
//TESTED - WORKS, VIS LOGIC WILL WORK
public class BubbleSort {
    /**
     * Simple bubble sort. Presumed that only size is passed on
     * and the numbers will range from 1 to (size-1)
     *
     * @param n ArrayList of unique integers to sort
     * @return ArrayList of ArrayLists - representing all states every step of the sort
     */
    public static ArrayList<SortableComponent> sort(ArrayList<Integer> n) {
        ArrayList<Integer> state = n;
        //return list
        ArrayList allStates = new ArrayList<SortableComponent>();
        //generate  numbers
        //System.out.println(state);
        SortableComponent first = new SortableComponent(getByValue(state), 0, 0, false);

        boolean swap = true;
        Integer temp;

        while (swap) { //while a swap occurs in an iteration
            swap = false;
            for (int x = 0; x < state.size() - 1; x++) { //compare all with adjacents
                //swap = false;
                SortableComponent s = new SortableComponent(getByValue(state), x, x + 1, swap); //consider order, should show comparison first, then swap, so 2 states per swap, 1 state per comparison and no swap
                //    allStates.add(s);
                if ((Integer) state.get(x) > (Integer) state.get(x + 1)) { // if swap needs to occur, start swapping
                    allStates.add(s);
                    temp = (Integer) state.get(x);
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

    /**
     * Pass by value
     *
     * @param list
     * @return
     */
    public static ArrayList<Integer> getByValue(ArrayList<Integer> list) {
        ArrayList s = new ArrayList<>(list);
        return s;
    }
}