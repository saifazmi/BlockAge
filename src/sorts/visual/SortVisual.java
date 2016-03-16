package sorts.visual;

import core.CoreEngine;
import entity.SortableBlockade;
import entity.Unit;
import entity.Unit.Sort;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sorts.logic.BubbleSort;
import sorts.logic.SelectionSort;
import sorts.logic.SortableComponent;

import java.util.ArrayList;

/**
 * @author : Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 *
 * The central class for displaying the sort visual. Loosely based on the Model-View-Controller model.
 * This class contains both: Controller and View, and the Model being individual Sort classes.
 * However instead of the Controller constantly changing the Model, it does it all in one go:
 * after a sort is chosen, all sort 'states' which are steps from the beginning to the end where the
 * list is sorted, are stored with swapped flags where necessary. This class then unwraps that information
 * in a way that can be easily usable by a visualizer, which is also here.
 *
 */
public class SortVisual {
    public int HEIGHT = 200;
    public int WIDTH = 300;
    private static ArrayList<SortVisualBar> blocks; //sorts all the visual block objects
    private ArrayList<SortableComponent> sorts; //all RELEVANT sort states, swapped and the state before a swap
    private ArrayList<Tuple> tuples; //used to store WHAT is swapped in a state
    private SortableBlockade block; //the physical blockade on the map passed in
    private Pane sortPane = null;
    private Unit unit; //the physical unit on the map passed in
    public Sort sort; //enum to choose sort
    private boolean remove = false;

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public SortVisual(SortableBlockade block, Unit unit) {
        block.setSortVisual(this);
        this.sort = unit.getSort();
        this.block = block;
        this.unit = unit;
        start();

    }

    public void start() {
        tuples = new ArrayList<Tuple>();

        // DFS unit
        if (this.sort == Unit.Sort.BUBBLE) sorts = BubbleSort.sort(block.getToSortArray());
        //TODO: this is wrong, and quick isnt implemented yet
        // BFS unit
        if (this.sort == Unit.Sort.SELECTION) sorts = SelectionSort.sort(block.getToSortArray());

        // Astar unit
        if (this.sort == Unit.Sort.SELECTION) sorts = SelectionSort.sort(block.getToSortArray());

        sortPane = new Pane();
        sortPane.setStyle("-fx-background-color: gray;");
        sortPane.setPrefSize(WIDTH, HEIGHT);
        //make blocks
        blocks = new ArrayList<SortVisualBar>();
        for (double x = 0; x < 11; x++) {              //width  //height
            SortVisualBar block = new SortVisualBar(15.0, (x * 15.0), Color.INDIANRED, (int) x - 1); //-1 because extra invis block, so 1 holds 0...etc
            int loc = 40; //calculate x pos for block
            if (x != 0) {
                int pos = find(x - 1);
                loc = 40 + (20 * pos);
            }
            if (x == 0) loc = 10;
            block.relocate(loc, HEIGHT - (x * 15) - 5); //place in location calculated
            sortPane.getChildren().add(block); //add to pane
            block.setUpdateX(block.getLayoutX()); //set custom variable needed for animation
            blocks.add(block); //add to global list of blocks
        }
        ArrayList<SortVisualBar> blocksTemp = new ArrayList<SortVisualBar>();
        //logical positioning in the data structure, they are ordered visually above,
        //but now need to be in the corresponding place in the data structure as well
        blocksTemp.add(blocks.get(0));
        for (int x = 0; x < sorts.get(0).getValue().size(); x++) {
            blocksTemp.add(blocks.get(sorts.get(0).getValue().get(x) + 1));
        }
        blocks = blocksTemp;
        prepareTransitions();//processes sorts state list, populates the Tuple list
        swapTwo(tuples.get(0).getFirst(), tuples.get(0).getSecond(), 0);// swap animation
    }

    /**
     * Used to generate a correct location for a block.
     * @param s
     * @return x position
     */
    private int find(double s) {
        ArrayList<Integer> state = sorts.get(0).getValue();
        for (int x = 0; x < state.size(); x++) {
            if (state.get(x) == s) {
                return x;
            }
        }
        return -1;
    }


    /**
     * Populates the tuple list for animations to play.
     * Iterates through all the sort states and finds what to swap.
     */
    private void prepareTransitions() {
        int count = 0;
        while (count < sorts.size()) {
            Tuple x = findSwapped(sorts.get(count), count);
            if (x.getFirst() != -1 && x.getSecond() != -1) {
                x.setFirst(x.getFirst() + 1);
                x.setSecond(x.getSecond() + 1);
                tuples.add(x);
            }
            count++;
        }


    }

    /**
     * Finds what needs to be swapped LOGICALLY
     * returns a tuple of what is to be swapped
     * @param sortState current state
     * @param currentID current number of state
     * @return Tuple which potentially has to be swapped
     */
    private Tuple findSwapped(SortableComponent sortState, int currentID) {
        int first = -1;//flag for no swap
        int second = -1;

        if (sortState.swapped) {
            SortableComponent previous = sorts.get(currentID - 1);
            for (int x = 0; x < sortState.getValue().size(); x++) {
                if (first == -1 && previous.getValue().get(x) != sortState.getValue().get(x)) {
                    first = x; //0 is
                }
                if (previous.getValue().get(x) != sortState.getValue().get(x)) {
                    second = x;
                }
            }
        }
        return new Tuple(first, second);
    }

    /**
     * General Pattern:
     * Make transition, play it, after it's played make a new one (REQUIRED for nature of JavaFX not updating x)
     * Take block1, push it to x=0, replace its old pos with block2, put block 1 in old block2 pos
     * USAGE: formatted to use the same corresponding numbers as the Tuples generated.
     *
     * @param: int block1 first block id
     * @param: int block2 second block id
     * @param: int swapIndex keep track of where you are in all swaps
     */
    private void swapTwo(int block1, int block2, int swapIndex) {

        SortVisualBar b1 = blocks.get(block1);
        SortVisualBar b2 = blocks.get(block2);
        //update possible movements from older transitions
        b1.relocate(b1.getUpdateX(), b1.getLayoutY());
        b2.relocate(b2.getUpdateX(), b2.getLayoutY());

        double oldX = b1.getLayoutX();
        double oldSecondX = b2.getLayoutX();
        //TODO: provisional code insertion test, also label
        FillTransition col1 = new FillTransition(Duration.millis(10),b1,Color.INDIANRED,Color.AQUA);
        FillTransition col2 = new FillTransition(Duration.millis(10),b2,Color.INDIANRED,Color.AQUA);


        // first block , 3 transitions  UP LEFT DOWN
        TranslateTransition tty = new TranslateTransition(Duration.seconds(0.17), blocks.get(block1));
        tty.setFromY(0);
        tty.setToY(-100);

        TranslateTransition ttx = new TranslateTransition(Duration.seconds(0.17), blocks.get(block1));
        ttx.setFromX(0);
        ttx.setToX(-oldX);//was -200

        TranslateTransition txx = new TranslateTransition(Duration.seconds(0.17), blocks.get(block1));
        txx.setFromY(-100);
        txx.setToY(0);

        //second block, 3 transitions UP <> DOWN
        TranslateTransition ty = new TranslateTransition(Duration.seconds(0.17), blocks.get(block2));
        ty.setFromY(0);
        ty.setToY(-200);

        TranslateTransition tx = new TranslateTransition(Duration.seconds(0.17), blocks.get(block2));
        tx.setFromX(0);
        tx.setToX(-(oldSecondX - (oldX)));//always left

        TranslateTransition txt = new TranslateTransition(Duration.seconds(0.17), blocks.get(block2));
        txt.setFromY(-200);
        txt.setToY(0);

        //last 3, first block, UP RIGHT DOWN
        TranslateTransition gy = new TranslateTransition(Duration.seconds(0.17), blocks.get(block1));
        gy.setFromY(0);
        gy.setToY(-200);

        TranslateTransition gx = new TranslateTransition(Duration.seconds(0.17), blocks.get(block1));
        gx.setFromX(-oldX);
        gx.setToX(oldSecondX - oldX);//old distance-  width - gap

        TranslateTransition gyy = new TranslateTransition(Duration.seconds(0.17), blocks.get(block1));
        gyy.setFromY(-200); //this is how it works...dont ask
        gyy.setToY(0);

        SequentialTransition seq = new SequentialTransition(col1,col2,tty, ttx, txx, ty, tx, txt, gy, gx, gyy);
        seq.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FillTransition col1x = new FillTransition(Duration.millis(10),b1,Color.AQUA,Color.INDIANRED);
                FillTransition col2x = new FillTransition(Duration.millis(10),b2,Color.AQUA,Color.INDIANRED);
                ParallelTransition colShift = new ParallelTransition(col1x,col2x);
                colShift.play();

                if (swapIndex != tuples.size() - 1) {
                    //update the special var x value, for the next time the block is used to update logically
                    b1.setUpdateX(oldSecondX);
                    b2.setUpdateX(oldX);
                    //prepare next swap from Tuple list
                    Tuple next = tuples.get(swapIndex + 1);
                    //swap logical position in the data structure as well as visual
                    SortVisualBar temp = blocks.get(block1);
                    blocks.set(block1, blocks.get(block2));
                    blocks.set(block2, temp);
                    swapTwo(next.getFirst(), next.getSecond(), swapIndex + 1); //next transition
                } else {
                    remove = true;
                    CoreEngine.Instance().removeEntity(block);
                }
            }
        });

        seq.play();
    }

    public Pane getPane() {
        return sortPane;
    }

    public SortableBlockade getBlock() {
        return block;
    }

    public void setBlock(SortableBlockade block) {
        this.block = block;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }


}