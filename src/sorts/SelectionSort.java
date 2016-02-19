package sorts;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dckxqt on 19-Feb-16.
 *
 * WORKS, visualization logic untested, cant really see until its made
 * SAIF ^
 *
 */
public class SelectionSort {
    /**
     * Selection sort, not sure if functional
     * @param size
     * @return
     */
    public static ArrayList<SortableComponent> sort(int size) {
        if (size < 3) {
            System.out.println("SORT: Input too low, returned null");
            return null;
        }
        //return list
        ArrayList allStates = new ArrayList<SortableComponent>();
        //generate  numbers
        ArrayList state = new ArrayList<Integer>();
        for (int x = 0; x < size; x++) {
            state.add(x);
        }
        Collections.shuffle(state);
        int i,j;
        for (j = 0; j<size-1;j++) {
            int min = j;
            for (i= j+1;i<size;i++) {
                SortableComponent s = new SortableComponent(getByValue(state),min,i,false);
                allStates.add(s);
                if ((Integer)state.get(i) < (Integer)state.get(min)) {
                    min = i;
                    SortableComponent c = new SortableComponent(getByValue(state),j,i,true);
                    allStates.add(c);
                }
            }
            if(min!=j) { //swapping
                Integer temp = (Integer) state.get(j);
                state.set(j,state.get(min));
                state.set(min,temp);
                SortableComponent s = new SortableComponent(getByValue(state),min,j,true);
                allStates.add(s);
            }
        }
        return allStates;
    }

    /**
     * Pass by value
     * @param list
     * @return
     */
    public static ArrayList<Integer> getByValue(ArrayList<Integer> list){
        ArrayList s = new ArrayList<Integer>(list);
        return s;
    }
}
