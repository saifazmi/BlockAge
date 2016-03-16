//package tests.logic;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import sorts.logic.BubbleSort;
//import sorts.logic.QuickSort;
//import sorts.logic.SelectionSort;
//import sorts.logic.SortableComponent;
//
//import java.util.ArrayList;
//
///**
// * Created by decklol on 19/02/16.
// */
//public class Test_Sort {
//
//    /**
//     * Testing function of output of the sorting function
//     * Check if the list is sorted
//     *
//     * @param states all the states got from the sort mechanism
//     * @return passed true if sorted, false otherwise
//     */
//    public boolean sortTest(ArrayList<SortableComponent> states) {
//        // Get the initial states and the final states of the list
//        ArrayList<Integer> initial = states.get(0).getValue();
//        ArrayList<Integer> sorted = states.get(states.size() - 1).getValue();
//
//        // If the initial state is already sorted, do the sorting with another list
//        while (initial.equals(sorted)) {
//            states = BubbleSort.sort(initial.size());
//            initial = states.get(0).getValue();
//            sorted = states.get(0).getValue();
//        }
//
//        // Check if the list is sorted
//        boolean passed = true;
//        for (int i = 0; i < sorted.size() && passed; i++) {
//            int a = sorted.get(i);
//            if (a != i) {
//                passed = false;
//            }
//        }
//
//        return passed;
//    }
//
//    /**
//     * Testing function of the output of the bubble sort mechanism
//     * Check if list is sorted
//     */
//    @Test()
//    public void bubbleSort() {
//        ArrayList<SortableComponent> states = BubbleSort.sort(200);
//
//        boolean passed = sortTest(states);
//
//        Assert.assertEquals(passed, true);
//    }
//
//    /**
//     * Testing function of the output of the quick sort mechanism
//     * Check if list is sorted
//     */
//    @Test()
//    public void quickSort() {
//        QuickSort o = new QuickSort();
//        ArrayList states = o.sort(200);
//
//        boolean passed = sortTest(states);
//
//        Assert.assertEquals(passed, true);
//
//    }
//
//    /**
//     * Testing function of the output of the selection sort mechanism
//     * Check if list is sorted
//     */
//    @Test()
//    public void selectionSort() {
//        ArrayList<SortableComponent> states = SelectionSort.sort(200);
//
//        boolean passed = sortTest(states);
//
//        Assert.assertEquals(passed, true);
//
//
//    }
//
//}
