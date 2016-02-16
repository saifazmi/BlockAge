package test;

import core.GameRunTime;
import entity.Blockade;
import entity.Unit;
import graph.GraphNode;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;
import searches.AStar;
import searches.BreadthFirstSearch;
import searches.DepthFristSearch;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dominic on 05/02/2016.
 */
public class Test {
    private static final Logger LOG = Logger.getLogger(Test.class.getName());
    private static EventHandler<MouseEvent> sceneClickPlaceBlockade = null;

    public static void test(GameRunTime runTime) {
        //unit//
        SpriteImage sprite = new SpriteImage("http://imgur.com/FAt5VBo.png", null);
        Unit unit = new Unit(0, "testUnit", runTime.getEngine().getGraph().getNodes().get(0), sprite, DepthFristSearch.Instance().findPathFrom(runTime.getEngine().getGraph().getNodes().get(0), runTime.getEngine().getGraph().getNodes().get(runTime.getEngine().getGraph().getNodes().size() - 1)), runTime.getEngine().getGraph(), runTime.getRenderer());
        sprite.setEntity(unit);
        //unit end, blockade start//
        SpriteImage spriteBlockade = new SpriteImage("http://imgur.com/dZZdmUr.png", null);
        Blockade block = new Blockade(0, "block", "desc", new GraphNode(2, 2), spriteBlockade);
        spriteBlockade.setOnMouseClicked(e ->
        {
            runTime.getRenderer().produceRouteVisual(runTime.getEngine().getGraph().getNodes());
            ((Unit)sprite.getEntity()).cancelRouteTransition();
        });
        spriteBlockade.setEntity(block);
        //blockade end//
        runTime.getEngine().getEntities().add(unit);
        runTime.getRenderer().drawInitialEntity(block);
        runTime.getRenderer().drawInitialEntity(unit);
        unit.setCurrentPixel(sprite.getX(), sprite.getY());

        sceneClickPlaceBlockade = e ->
        {
            LOG.log(Level.INFO, "Click registered at:  (x, " + e.getX() + "), (y, " + e.getY() + ")");
            Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(0,0), null);
            SpriteImage spriteImage = new SpriteImage("http://imgur.com/dZZdmUr.png", blockadeInstance);
            blockadeInstance.setSprite(spriteImage);
            Blockade blockade = Blockade.createBlockade(e, runTime, blockadeInstance);
            if (blockade != null)
            {
                LOG.log(Level.INFO, "Blockade created at: (x, " + blockade.getPosition().getX() + "), (y, " + blockade.getPosition().getY() + ")");
                runTime.getRenderer().drawInitialEntity(blockade);
            }
            else
            {
                LOG.log(Level.INFO, "Blockade creation failed. Request rejected, node has contents.");
            }
        };
        runTime.getScene().setOnMouseClicked(sceneClickPlaceBlockade);
    }
}