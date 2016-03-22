package sorts.visual;

import core.CoreEngine;
import entity.SortableBlockade;
import entity.Unit;
import entity.Unit.Sort;
import gui.GameInterface;
import gui.Renderer;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sorts.logic.BubbleSort;
import sorts.logic.InsertSort;
import sorts.logic.SelectionSort;
import sorts.logic.SortableComponent;
import stores.ImageStore;
import stores.LambdaStore;

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
 */
public class SortVisual {
    public int HEIGHT = 260;
    public int WIDTH = 280;
    public static ArrayList<SequentialTransition> seq = new ArrayList<>();
    private ArrayList<SortVisualBar> blocks; //sorts all the visual block objects
    private ArrayList<SortableComponent> sorts; //all RELEVANT sort states, swapped and the state before a swap
    private ArrayList<Tuple> tuples; //used to store WHAT is swapped in a state
    private SortableBlockade block; //the physical blockade on the map passed in

    public ArrayList<SortVisualBar> getBlocks() {
        return blocks;
    }

    public static SortVisual rendered = null;
    private Pane sortPane = null;
    private Unit unit; //the physical unit on the map passed in
    public Sort sort; //enum to choose sort
    private boolean remove = false; //flag for game elements to know whether the sort pane is removable ( done with animating )


    public SortVisual(SortableBlockade block, Unit unit) {
        block.setSortVisual(this);
        this.sort = unit.getSort();
        this.block = block;
        this.unit = unit;
        highlightBlock();
        start();
    }


    /**
     * Decides which sort to use.
     * Creates the pane, and generates 11 bars, with one invisible using a custom SortVisualBar object.
     * Then repositions them according to the first state of the sort (initial sort input)
     * Then repositions the block objects in the same order, but logically within the data structure they are stored in
     * This allows for the swap to cleanly move them logically AND visually simultaneously.
     */
    public void start() {
        tuples = new ArrayList<>();
        //DECIDING WHICH SORT TO USE
        // DFS unit -> Set to Bubble Sort
        if (this.sort == Unit.Sort.BUBBLE) sorts = BubbleSort.sort(block.getToSortArray());
        // BFS unit -> Set to Selection Sort
        if (this.sort == Unit.Sort.SELECTION) sorts = SelectionSort.sort(block.getToSortArray());
        // Astar unit -> Set to Insertion Sort
        if (this.sort == Unit.Sort.INSERT) sorts = InsertSort.sort(block.getToSortArray());

        sortPane = new Pane();//create a pane to store block objects on
        sortPane.setStyle("-fx-background-color: #838b83;");
        sortPane.setOpacity(0.0);
        sortPane.setPrefSize(WIDTH, HEIGHT);
        //GENERATING BLOCKS, AND PLACING THEM IN THE CORRECT PLACE VISUALLY
        blocks = new ArrayList<>();
        for (double x = 0; x < 11; x++) {              //width  //height

            SortVisualBar block = new SortVisualBar(15.0, (x * 15.0), Color.web("#7092BE"), (int) x - 1); //-1 because extra invis block, so 1 holds 0...etc
            if (x == 0) block.setStroke(null);
            int loc = 40;
            if (x != 0) { //loc used to calculate location to decide where to place blocks
                int pos = find(x - 1);
                loc = 40 + (20 * pos);
            }
            if (x == 0) loc = 10;// if 0th block, then hard set it to nearly the edge, invis block
            block.relocate(loc, HEIGHT - (x * 15) - 5); //place in location calculated
            block.setOpacity(0.0);
            sortPane.getChildren().add(block); //add to pane
            block.setUpdateX(block.getLayoutX()); //set custom variable needed for animation
            blocks.add(block); //add to global list of blocks
        }
        //SIMPLE TEXT ON THE LEFT
        Text tempSign = new Text("TEMP"); //simple text on the left of pane
        tempSign.setFill(Color.AQUA);
        tempSign.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        tempSign.setRotate(-90);
        sortPane.getChildren().add(tempSign);
        tempSign.relocate(-WIDTH / 15, HEIGHT - 45);
        tempSign.relocate(-WIDTH / 15, HEIGHT - 45);
        //LOGICALLY ORDERING BLOCKS IN THE DATA STRUCTURE
        ArrayList<SortVisualBar> blocksTemp = new ArrayList<>();
        //logical positioning in the data structure, they are ordered visually above,
        //but now need to be in the corresponding place in the data structure as well
        blocksTemp.add(blocks.get(0));
        for (int x = 0; x < sorts.get(0).getValue().size(); x++) {
            blocksTemp.add(blocks.get(sorts.get(0).getValue().get(x) + 1));
        }
        blocks = blocksTemp;
        prepareTransitions();//processes sorts state list, populates the Tuple list
        swapTwo(tuples.get(0).getFirst(), tuples.get(0).getSecond(), 0);//play the FIRST swap animation, it will chain
    }

    /**
     * Used to generate a correct location for a block inside a data structure.
     * This is done to synchronize the data structure that contains the blocks, with the first sort state.
     * Giving the correct visual order.
     *
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
     * Populates the tuple list which dictates how animations play.
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
     * returns a tuple of what indexes are to be swapped
     *
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
                if (first == -1 && previous.getValue().get(x) != sortState.getValue().get(x)) { //fill first incidence of different values
                    first = x; //0 is
                }
                if (previous.getValue().get(x) != sortState.getValue().get(x)) { //second
                    second = x;
                }
            }
        }
        return new Tuple(first, second);
    }

    /**
     * General Pattern:
     * Make transition, play it, after it's played make a new one (REQUIRED for nature of JavaFX not updating x)
     * Take block1, push it to x=0 (edge of screen) , replace its old pos with block2, put block 1 in old block2 pos
     * USAGE: formatted to use the same corresponding numbers as the Tuples generated.
     *
     * @param: int block1 first block id
     * @param: int block2 second block id
     * @param: int swapIndex keep track of where you are in all swaps
     */
    private void swapTwo(int block1, int block2, int swapIndex) {

        SortVisualBar b1 = blocks.get(block1);
        SortVisualBar b2 = blocks.get(block2);
        //update possible movements from older transitions (this value is updated after this animation ends)
        b1.relocate(b1.getUpdateX(), b1.getLayoutY());
        b2.relocate(b2.getUpdateX(), b2.getLayoutY());

        double oldX = b1.getLayoutX();
        double oldSecondX = b2.getLayoutX();

        FillTransition col1 = new FillTransition(Duration.millis(10), b1, Color.web("#7092BE"), Color.AQUA);
        FillTransition col2 = new FillTransition(Duration.millis(10), b2, Color.web("#7092BE"), Color.AQUA);
        ParallelTransition colx = new ParallelTransition(col1, col2);


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

        SequentialTransition temp = new SequentialTransition(colx, tty, ttx, txx, ty, tx, txt, gy, gx, gyy);
        seq.add(temp);
        //animations have to be calculated after the previous set is finished, as to keep the correct coordinates of X values hence onFinished
        temp.setOnFinished(event -> {
            FillTransition col1x = new FillTransition(Duration.millis(300), b1, Color.AQUA, Color.web("#7092BE"));
            FillTransition col2x = new FillTransition(Duration.millis(300), b2, Color.AQUA, Color.web("#7092BE"));
            ParallelTransition colShift = new ParallelTransition(col1x, col2x);
            colShift.play();//colShift is highlighting of bars into teal when they are being swapped

            if (swapIndex != tuples.size() - 1) {//SWAPS still exist, more to animate
                //update the special var x value, for the next time the block is used to update logically
                b1.setUpdateX(oldSecondX);
                b2.setUpdateX(oldX);
                //prepare next swap from Tuple list
                Tuple next = tuples.get(swapIndex + 1);
                //swap logical position in the data structure as well as visual
                SortVisualBar temp1 = blocks.get(block1);
                blocks.set(block1, blocks.get(block2));
                blocks.set(block2, temp1);
                seq.remove(temp);
                swapTwo(next.getFirst(), next.getSecond(), swapIndex + 1); //next transition
            } else { // no more swaps exist, do logic to cleanly remove the pane and its objects
                remove = true;
                CoreEngine.Instance().removeEntity(block);
                unit.setSorting(null);
                block.setSortVisual(null);
                if (SortVisual.rendered != null && SortVisual.rendered.equals(this)) {
                    SortVisual.rendered = null;
                }
                seq.remove(temp);
            }
        });

        temp.play();
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

    public void display(boolean display) {
        double opacity;
        if (display) {
            opacity = 1.0;
            GameInterface.sortVisualisationPane.setStyle("-fx-background-color: #838b83;");
            rendered = this;
        } else {
            opacity = 0.0;
            GameInterface.sortVisualisationPane.setStyle("-fx-background-color: transparent;");
            rendered = null;
        }
        ArrayList<SortVisualBar> bars = getBlocks();
        for (SortVisualBar bar : bars) {
            bar.setOpacity(opacity);
        }
        sortPane.setOpacity(opacity);
        //GameInterface.sortVisualisationPane.setOpacity(opacity);
    }

    @Override
    /**
     * Multiple sort blocks can be sorted at the same time. So need equals to distinguish.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SortVisual that = (SortVisual) o;

        if (HEIGHT != that.HEIGHT) return false;
        if (WIDTH != that.WIDTH) return false;
        if (remove != that.remove) return false;
        if (blocks != null ? !blocks.equals(that.blocks) : that.blocks != null) return false;
        if (sorts != null ? !sorts.equals(that.sorts) : that.sorts != null) return false;
        if (tuples != null ? !tuples.equals(that.tuples) : that.tuples != null) return false;
        if (block != null ? !block.equals(that.block) : that.block != null) return false;
        if (sortPane != null ? !sortPane.equals(that.sortPane) : that.sortPane != null) return false;
        return unit != null ? unit.equals(that.unit) : that.unit == null && sort == that.sort;

    }

    @Override
    /**
     * Accompanies equals method
     */
    public int hashCode() {
        int result = HEIGHT;
        result = 31 * result + WIDTH;
        result = 31 * result + (blocks != null ? blocks.hashCode() : 0);
        result = 31 * result + (sorts != null ? sorts.hashCode() : 0);
        result = 31 * result + (tuples != null ? tuples.hashCode() : 0);
        result = 31 * result + (block != null ? block.hashCode() : 0);
        result = 31 * result + (sortPane != null ? sortPane.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (remove ? 1 : 0);
        return result;
    }

    /**
     * Highlights the current block the unit is sorting
     * In addition to changing colour in the transition, subtly changes.
     */
    public void highlightBlock() {
        Platform.runLater(() -> {
            Renderer.Instance().remove(block.getSprite());
            ImageStore.setSpriteProperties(block, ImageStore.sortableBiggerImage);
            Renderer.Instance().drawInitialEntity(block);
            block.getSprite().setOnMouseClicked(LambdaStore.Instance().getShowSort());
        });
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
}