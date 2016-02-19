package sorts;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by decklol on 19/02/16.
 */
public class BubbleSort {
    /**
     * Simple bubble sort. Presumed that only size is passed on
     * and the numbers will range from 1 to (size-1)
     * @param size size of sortable
     * @return ArrayList of ArrayLists - representing all states every step of the sort
     */
    public static ArrayList<ArrayList<SortableComponent>> sort(int size){

        if(size<3){
          System.out.println("SORT: Input too low, returned null");
          return null;
        }
        //return list
        ArrayList allStates = new ArrayList<ArrayList<SortableComponent>>();
        //generate  numbers
        ArrayList state = new ArrayList<Integer>();
        for(int x = 0;x<size;x++){
            state.add(x);
        }
        Collections.shuffle(state);

        SortableComponent first = new SortableComponent(state,0,0,false);

        allStates.add(state);

        boolean swap = true;
        Integer temp;

        while (swap){ //while a swap occurs in an iteration
            swap= false;
            for(int x=0;x<size-1;x++){ //compare all with adjacents
                SortableComponent s = new SortableComponent(state,x,x+1,swap); //consider order, should show comparison first, then swap, so 2 states per swap, 1 state per comparison and no swap
                allStates.add(s);
                if ((Integer) state.get(x) > (Integer)state.get(x+1)){ // if swap needs to occur, start swapping
                    temp = (Integer) state.get(x);
                    state.set(x,state.get(x+1));
                    state.set(x+1,temp);
                    swap = true;
                    SortableComponent sc = new SortableComponent(state,x,x+1,swap);
                    allStates.add(sc);
                }


            }
        }
        return allStates;

    }
}
