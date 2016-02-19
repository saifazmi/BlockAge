package sorts;

import java.util.ArrayList;

/**
 * Created by decklol on 19/02/16.
 * For indication of what is being sorted on the visual level.
 */
public class SortableComponent {
    public ArrayList value; //WHOLE ARRAYLIST
    public int comparing1; //INDEX OF WHATS BEING COMPARED
    public int comparing2;
    public boolean swapped; //SWAP STATE - FLAG FOR ANIMATION

    public SortableComponent(ArrayList<Integer> value, int comparing1,int comparing2, boolean swapped){
        this.value = value;
        this.comparing1 = comparing1;
        this.comparing2 = comparing2;
        this.swapped = swapped;
    }

    public boolean isSwapped() {
        return swapped;
    }

    public void setSwapped(boolean swapped) {
        this.swapped = swapped;
    }

    public int getComparing1() {
        return comparing1;
    }

    public void setComparing1(int comparing1) {
        this.comparing1 = comparing1;
    }

    public int getComparing2() {
        return comparing2;
    }

    public void setComparing2(int comparing2) {
        this.comparing2 = comparing2;
    }
}
