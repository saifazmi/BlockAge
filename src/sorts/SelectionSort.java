package sorts;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 */
//WORKS, visualization logic untested, cant really see until its made
public class SelectionSort {
    /**
     * Selection sort, not sure if functional
     *
     * @param size
     * @return
     */
    public static ArrayList<SortableComponent> sort(int size) {
        if (size < 3) {
            System.out.println("SORT: Input too low, returned null");
            return null;
        }
        //return list
        ArrayList<SortableComponent> allStates = new ArrayList<>();
        //generate  numbers
        ArrayList<Integer> state = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            state.add(x);
        }
        Collections.shuffle(state);
        int i, j;
        for (j = 0; j < size - 1; j++) {
            int min = j;
            for (i = j + 1; i < size; i++) {
                SortableComponent s = new SortableComponent(getByValue(state), min, i, false);
                allStates.add(s);
                if (state.get(i) < state.get(min)) {
                    min = i;
                    SortableComponent c = new SortableComponent(getByValue(state), j, i, true);
                    allStates.add(c);
                }
            }
            if (min != j) { //swapping
                Integer temp = state.get(j);
                state.set(j, state.get(min));
                state.set(min, temp);
                SortableComponent s = new SortableComponent(getByValue(state), min, j, true);
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
