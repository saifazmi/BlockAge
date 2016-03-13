package test;

import sorts.BubbleSort;
import sorts.QuickSort;
import sorts.SelectionSort;
import sorts.SortableComponent;

import java.util.ArrayList;

/**
 * Created by decklol on 19/02/16.
 */
public class Test_Sort {
    public static void main(String[] args) {
        ArrayList states = BubbleSort.sort(10);
        //System.out.println(((SortableComponent)states.get(0)).getValue());
        //printSort(states);
        ArrayList statesX = SelectionSort.sort(10);
        //printSort(statesX);
        QuickSort o = new QuickSort();
        ArrayList statesZ = o.sort(10);
        printSort(statesZ);
    }

    public static void printSort(ArrayList<SortableComponent> list) {
        String out = "";
        for (SortableComponent e : list) {
            ArrayList h = e.getValue();
            for (int z = 0; z < h.size(); z++) {
                out += h.get(z) + ", ";

            }
            if(e.isSwapped())  out+= " SWAPPED";
            out += "\n";
        }
        System.out.println(out);
    }

}