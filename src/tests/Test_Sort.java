package tests;

import sorts.logic.InsertSort;
import sorts.logic.QuickSort;
import sorts.logic.SelectionSort;
import sorts.logic.SortableComponent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by decklol on 19/02/16.
 */
public class Test_Sort {
    public static void main(String[] args) {

        //ArrayList<SortableComponent> states = BubbleSort.sort(generateUniqSortArray());
        //System.out.println(((SortableComponent)states.get(0)).getValue());
        //printSort(states);
        ArrayList<SortableComponent> statesX = InsertSort.sort(generateUniqSortArray());
        //printSort(statesX);
        QuickSort o = new QuickSort();
        ArrayList statesZ = o.sort(10);
        printSort(statesX);
        //System.out.println(statesX.get(0).getValue().size());
    }

    public static void printSort(ArrayList<SortableComponent> list) {
        String out = "";
        for (SortableComponent e : list) {
            ArrayList h = e.getValue();
            for (int z = 0; z < h.size(); z++) {
                out += h.get(z) + ", ";

            }
            if (e.isSwapped()) out += " SWAPPED";
            out += "\n";
        }
        System.out.println(out);
    }

    public static ArrayList<Integer> generateUniqSortArray() {

        ArrayList<Integer> arrToSort = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            arrToSort.add(i);
        }

        Collections.shuffle(arrToSort);
        return arrToSort;
    }

}