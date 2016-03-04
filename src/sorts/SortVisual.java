package sorts;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 *
 * PRESET SIZE OF 10, 1 block for invisible spot
 */
public class SortVisual {
    private static final Logger LOG = Logger.getLogger(SortVisual.class.getName());
    public int HEIGHT = 300;
    public int WIDTH = 400;
    private static ArrayList<SortVisualBar> blocks;
    private ArrayList<SortableComponent> sorts;
    public SequentialTransition animatePath;
    public Pane sortPane;

    public SortVisual(int sortID) {
        if(sortID<0 || sortID>2) {
            LOG.log(Level.SEVERE, "WRONG INPUT (Expected 0-2)");
        }
        //prepare sortables
        if(sortID == 0){ //bubble
            sorts = BubbleSort.sort(10);
        }
        if(sortID == 1){ //selection
            sorts = SelectionSort.sort(10);
        }
        if(sortID == 2){ //quick
            QuickSort q = new QuickSort();
            sorts = q.sort(10); //most tentative one
        }

        sortPane = new Pane();
        sortPane.setStyle("-fx-background-color: white;");
        //make blocks
        blocks = new ArrayList<SortVisualBar>();
        for (double x = 0; x < 11; x++) {              //width  //height
            SortVisualBar block = new SortVisualBar(25.0, (x * 15.0), Color.LIGHTBLUE, (int) x-1); //-1 because extra invis block, so 1 holds 0...etc
            //attempting to reorder according to 0th sort state during creation
            block.relocate(10 + (x * 30), HEIGHT - (x * 15) - 50); //COMPLETE CORRECT COORDS   for reference not counting invis block, 40 70 100 130 160 190 220 250 280 310
            sortPane.getChildren().add(block);
            blocks.add(block);
        }

        animatePath = new SequentialTransition();
        prepareTransitions();//makes new seqTransition

        //animatePath.play();
    }
    /**
     * Gets the block corresponding to the value
     * @param value
     * @return block
     */
    public int findBlock(int value){ // assumed never input -1
        for(int x=1; x<blocks.size();x++){ //-1 is invis
            if(blocks.get(x).getValue() == value){
                return x;
            }
        }
        return -1;
    }

    /**
     * Generates the SequentialTransition
     */
    public void prepareTransitions(){
        int count=0;
        while(count<sorts.size()){
            Tuple x = findSwapped(sorts.get(count),count);
            if(x.getFirst() != -1 && x.getSecond() != -1){
                swapTwo(findBlock(x.getFirst()),findBlock(x.getSecond())); //use findBlock to get block corresponding to logical value
            }
            count++;
        }

    }

    /**
     * Finds what needs to be swapped LOGICALLY
     * @param sortState
     * @param currentID
     * @return Tuple
     */
    public Tuple findSwapped(SortableComponent sortState,int currentID){
        int first=-1;//flag for no swap
        int second=-1;
        if(sortState.swapped) {
            SortableComponent previous = sorts.get(currentID-1);
            for (int x = 0; x < sortState.getValue().size(); x++) {
                if(first==-1 && previous.getValue().get(x) != sortState.getValue().get(x)){
                    first = x; //0 is
                }
                if(previous.getValue().get(x) != sortState.getValue().get(x)){
                    second = x;
                }
            }
        }
        return new Tuple(first,second);
    }
    /**
     * General Pattern:
     * Make transition, add to animatePath
     * USAGE: input blocks from 1-10
     * @param: int block1 first block id
     * @param: int block2 second block id
     */
    public void swapTwo(int block1, int block2) {
        //contextual swap, one thing to consider is TODO: test for moves on the same block even though it was moved before relevant transition
        double oldY = blocks.get(block1).getLayoutY();
        double oldX = blocks.get(block1).getLayoutX();

        double oldSecondY = blocks.get(block2).getLayoutY();
        double oldSecondX = blocks.get(block2).getLayoutX();

        System.out.println(oldX);
        System.out.println(oldSecondX);
        // first block , 3 transitions
        TranslateTransition tty = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        tty.setFromY(0);
        tty.setToY(-100);
        tty.setCycleCount(1);

        TranslateTransition ttx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        ttx.setFromX(0);
        ttx.setToX(-oldX);//was -200
        ttx.setCycleCount(1);

        TranslateTransition txy = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        txy.setFromY(-100);
        txy.setToY(0);
        txy.setCycleCount(1);
        // second block, take note of values on the last transitions of each one
        //you can change the dimensions as you wish, easier to navigate if you have some sort of class-wide var for sizes


        TranslateTransition ty = new TranslateTransition(Duration.seconds(0.25), blocks.get(block2));
        ty.setFromY(0);
        ty.setToY(-200);
        ty.setCycleCount(1);

        TranslateTransition tx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block2));
        tx.setFromX(0);
        tx.setToX(- (oldSecondX - (oldX))  );//always left
        tx.setCycleCount(1);

        TranslateTransition txt = new TranslateTransition(Duration.seconds(0.25), blocks.get(block2));
        txt.setFromY(-200);
        txt.setToY(0);
        txt.setCycleCount(1);

        //last 3
        TranslateTransition gy = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        gy.setFromY(0);
        gy.setToY(-200);
        gy.setCycleCount(1);

        TranslateTransition gx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        gx.setFromX(-oldX);
        gx.setToX(oldSecondX-oldX);//old distance-  width - gap
        gx.setCycleCount(1);

        TranslateTransition gyy = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        gyy.setFromY(-200); //this is how it works...dont ask
        gyy.setToY(0);
        gyy.setCycleCount(1);

        animatePath.getChildren().add(tty);
        animatePath.getChildren().add(ttx);
        animatePath.getChildren().add(txy);
        animatePath.getChildren().add(ty);
        animatePath.getChildren().add(tx);
        animatePath.getChildren().add(txt);
        animatePath.getChildren().add(gy);
        animatePath.getChildren().add(gx);
        animatePath.getChildren().add(gyy);
    }

}
