package tests;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sorts.logic.SelectionSort;
import sorts.logic.SortableComponent;
import sorts.visual.SortVisualBar;
import sorts.visual.Tuple;

import java.util.ArrayList;

/**
 * @author : First created by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 * <p>
 * TEST CLASS
 */
//TODO: 'TEMP' Pane at 0, growing vertically
public class TestVisualLive extends Application {
    public int HEIGHT = 300;
    public int WIDTH = 400;
    private static ArrayList<SortVisualBar> blocks;
    private ArrayList<SortableComponent> sorts;
    private ArrayList<Tuple> tuples;

    public void start(Stage stage) {
        tuples = new ArrayList<Tuple>();
        sorts = SelectionSort.sort(Test_Sort.generateUniqSortArray());
        Pane sortPane = new Pane();
        sortPane.setStyle("-fx-background-color: gray;");
        sortPane.setPrefSize(WIDTH, HEIGHT);
        //make blocks
        blocks = new ArrayList<SortVisualBar>();
        for (double x = 0; x < 11; x++) {              //width  //height
            SortVisualBar block = new SortVisualBar(25.0, (x * 15.0), Color.INDIANRED, (int) x - 1); //-1 because extra invis block, so 1 holds 0...etc
            int loc = 40;
            if (x != 0) {
                int pos = find(x - 1);
                loc = 40 + (30 * pos);
            }
            if (x == 0) loc = 10;
            block.relocate(loc, HEIGHT - (x * 15) - 50); //trying to make it state0

            sortPane.getChildren().add(block);
            block.setUpdateX(block.getLayoutX());
            blocks.add(block);
        }
        ArrayList<SortVisualBar> blocksTemp = new ArrayList<SortVisualBar>();
        blocksTemp.add(blocks.get(0));
        for (int x = 0; x < sorts.get(0).getValue().size(); x++) {
            blocksTemp.add(blocks.get(sorts.get(0).getValue().get(x) + 1));
        }
        blocks = blocksTemp;
        prepareTransitions();//makes new seqTransition
        Scene scene = new Scene(sortPane);
        stage = new Stage();
        stage.setTitle("TEST");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        swapTwo(tuples.get(0).getFirst(), tuples.get(0).getSecond(), 0);
    }


    public static void main(String[] args) {
        Application.launch(args);
    }

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
     * Gets the block corresponding to the value
     * @param value
     * @return block
     */
//    public int findBlock(int value){ // assumed never input -1
//        for(int x=1; x<blocks.size();x++){ //-1 is invis
//            if(blocks.get(x).getValue() == value){
//                return x;
//            }
//        }
//        return -1;
//    }

    /**
     * Generates the SequentialTransition
     */
    public void prepareTransitions() {
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
     *
     * @param sortState
     * @param currentID
     * @return Tuple
     */
    public Tuple findSwapped(SortableComponent sortState, int currentID) {
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
     * Make transition, add to animatePath
     * USAGE: input blocks from 1-10
     *
     * @param: int block1 first block id
     * @param: int block2 second block id
     */
    public void swapTwo(int block1, int block2, int swapIndex) {

        SortVisualBar b1 = blocks.get(block1);
        SortVisualBar b2 = blocks.get(block2);

        b1.relocate(b1.getUpdateX(), b1.getLayoutY());
        b2.relocate(b2.getUpdateX(), b2.getLayoutY());

        double oldX = b1.getLayoutX();
        double oldSecondX = b2.getLayoutX();

        // first block , 3 transitions
        TranslateTransition tty = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        tty.setFromY(0);
        tty.setToY(-100);

        TranslateTransition ttx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        ttx.setFromX(0);
        ttx.setToX(-oldX);//was -200

        TranslateTransition txx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        txx.setFromY(-100);
        txx.setToY(0);


        TranslateTransition ty = new TranslateTransition(Duration.seconds(0.25), blocks.get(block2));
        ty.setFromY(0);
        ty.setToY(-200);

        TranslateTransition tx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block2));
        tx.setFromX(0);
        tx.setToX(-(oldSecondX - (oldX)));//always left

        TranslateTransition txt = new TranslateTransition(Duration.seconds(0.25), blocks.get(block2));
        txt.setFromY(-200);
        txt.setToY(0);

        //last 3
        TranslateTransition gy = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        gy.setFromY(0);
        gy.setToY(-200);

        TranslateTransition gx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        gx.setFromX(-oldX);
        gx.setToX(oldSecondX - oldX);//old distance-  width - gap

        TranslateTransition gyy = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        gyy.setFromY(-200); //this is how it works...dont ask
        gyy.setToY(0);

        SequentialTransition seq = new SequentialTransition(tty, ttx, txx, ty, tx, txt, gy, gx, gyy);
        seq.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (swapIndex != tuples.size() - 1) {

                    b1.setUpdateX(oldSecondX);
                    b2.setUpdateX(oldX);
                    Tuple next = tuples.get(swapIndex + 1);
                    SortVisualBar temp = blocks.get(block1);
                    blocks.set(block1, blocks.get(block2));
                    blocks.set(block2, temp);
                    swapTwo(next.getFirst(), next.getSecond(), swapIndex + 1);
                }
            }
        });

        seq.play();
    }

}