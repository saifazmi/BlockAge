package test;

import core.GameRunTime;
import entity.Blockade;
import entity.Unit;
import graph.GraphNode;
import lambdastorage.LambdaStore;
import sceneElements.SpriteImage;
import searches.AStar;
import searches.DepthFristSearch;

import java.util.logging.Logger;

/**
 * Created by Dominic on 05/02/2016.
 */
public class Test {
    private static final Logger LOG = Logger.getLogger(Test.class.getName());
    private static LambdaStore lambdaStore;

    public static void test(GameRunTime runTime) {
        lambdaStore = new LambdaStore(runTime);
        //unit//
        /*
        SpriteImage sprite = new SpriteImage("http://imgur.com/FAt5VBo.png", null);
        Unit unit = new Unit(0, "testUnit", runTime.getEngine().getGraph().nodeWith(new GraphNode(0,10)), sprite, DepthFristSearch.Instance().findPathFrom(runTime.getEngine().getGraph().getNodes().get(0), runTime.getEngine().getGraph().getNodes().get(runTime.getEngine().getGraph().getNodes().size() - 1)), runTime.getEngine().getGraph(), runTime.getRenderer());
        sprite.setEntity(unit);*/
        //unit end, blockade start//
        /*
        SpriteImage spriteBlockade = new SpriteImage("http://imgur.com/dZZdmUr.png", null);
        Blockade block = new Blockade(0, "block", "desc", new GraphNode(2, 2), spriteBlockade);
        spriteBlockade.setOnMouseClicked(e ->
        {
            runTime.getRenderer().produceRouteVisual(runTime.getEngine().getGraph().getNodes());
            ((Unit) sprite.getEntity()).cancelRouteTransition();
        });
        spriteBlockade.setEntity(block);*/
        //blockade end, 2nd unit start//
        /*SpriteImage sprite2 = new SpriteImage("http://imgur.com/FAt5VBo.png", null);
        Unit unit2 = new Unit(0, "testUnit", runTime.getEngine().getGraph().nodeWith(new GraphNode(0,10)), sprite2, AStar.search(runTime.getEngine().getGraph().getNodes().get(0), runTime.getEngine().getGraph().getNodes().get(runTime.getEngine().getGraph().getNodes().size() - 1)), runTime.getEngine().getGraph(), runTime.getRenderer());
        sprite2.setEntity(unit2);*/
        //unit end//
        /*runTime.getEngine().getEntities().add(unit);
        runTime.getEngine().getEntities().add(unit2);
        runTime.getRenderer().drawInitialEntity(block);
        runTime.getRenderer().drawInitialEntity(unit);
        runTime.getRenderer().drawInitialEntity(unit2);
        unit.setCurrentPixel(sprite.getX(), sprite.getY());
        unit2.setCurrentPixel(sprite2.getX(), sprite2.getY());*/


        runTime.getScene().setOnMouseClicked(lambdaStore.getSceneClickPlaceBlockade());
    }
}