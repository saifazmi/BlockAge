package core;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;

/**
 * Created by hung on 13/02/16.
 */
public class UnitSpawner {

    private ArrayList<Unit> unitPool;
    private int unitPoolCount = 0;
    private int totalSpawnables = 10;
    private int spawnCount = 0;
    private int spawnlimit;
    private GameRunTime runTime;
    Random rndSearchGen;

    private String[] names;
    private String[] descriptions;
    private int cooldown = 60;
    private Image image = null;

    public UnitSpawner(GameRunTime runTime) {
        names = new String[]{"Banshee", "Demon", "Death knight"};
        descriptions = new String[]{"Depth First Search", "Breadth First Search", "A* Search", "Selection Sort", "Insertion Sort", "Bubble Sort"};
        rndSearchGen = new Random(System.currentTimeMillis());
        unitPool = new ArrayList<>();

        this.runTime = runTime;

        Graph graph = runTime.getEngine().getGraph();
        Renderer renderer = runTime.getRenderer();
        // this should be passed in
        GraphNode goal = graph.getNodes().get(graph.getNodes().size() - 1);
        
        for (unitPoolCount = 0; unitPoolCount < totalSpawnables; unitPoolCount++) {
        	CreateUnit(graph, renderer, goal);
        }
    }

    private Unit CreateUnit(Graph graph, Renderer renderer, GraphNode goal) {
        // doing random for now, could return sequence of numbers representing units wanted
        int index = rndSearchGen.nextInt(3);
        String SEPARATOR = File.separator;
        
        // Load the appropriate image for each search
        Image imageDemon = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 2.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
        Image imageDk = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 3.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
        Image imageBanshee = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit sprite 4.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
        
        Image imagePressedDemon = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 2.0s.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
        Image imagePressedDk = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 3.0s.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
        Image imagePressedBanshee = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 4.0s.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
        
        if(Unit.Search.values()[index] == Unit.Search.BFS) {
        	image = imageDemon;
        }
        else if (Unit.Search.values()[index] == Unit.Search.A_STAR) {
        	image = imageDk;
        }
        else {
        	image = imageBanshee;
    	}
        SpriteImage sprite = new SpriteImage(image, null);
        Unit unit = new Unit(unitPoolCount, names[index], graph.nodeWith(new GraphNode(0, 0)), sprite, Unit.Search.values()[index], Unit.Sort.values()[index], graph, goal, renderer);
        sprite.setEntity(unit);

        // focus sprite and displays text when clicked on it
        sprite.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
			public void handle(MouseEvent e) {
    			sprite.requestFocus();
        		GameInterface.unitDescriptionText.setFont(GameInterface.bellotaFont);
        		GameInterface.unitDescriptionText.setText("Name:   " + sprite.getEntity().getName() + "\n" + 
		    										  "Search:  " + Unit.Search.values()[index] + "\n" +
		    										  "Sort:      " + Unit.Sort.values()[index]);
        		// sets the image pressed for each unit accordingly to the search
        		if(Unit.Search.values()[index] == Unit.Search.BFS) {
        			sprite.setImage(imagePressedDemon);
        		}
        		else if (Unit.Search.values()[index] == Unit.Search.A_STAR) {
        			sprite.setImage(imagePressedDk);
        		}
        		else {
        			sprite.setImage(imagePressedBanshee);
        		}
			}
        });
        // if S key is pressed, the sprite gets unfocused and the text area gets cleared
        sprite.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				Image img = new Image(sprite.getImage().impl_getUrl().substring(0, sprite.getImage().impl_getUrl().length()-5) + ".png");
				KeyCode k = e.getCode();
				if (k == KeyCode.S) {
					System.out.println(runTime.getEngine().getEntities().size());
					for(int i=0; i<runTime.getEngine().getEntities().size(); i++) {
						System.out.println(runTime.getEngine().getEntities().get(i).getName());
						if (runTime.getEngine().getEntities().get(i).getSprite().getImage().impl_getUrl().contains("2.0s")) { 
							runTime.getEngine().getEntities().get(i).getSprite().setImage(imageDemon);
						}
						else if (runTime.getEngine().getEntities().get(i).getSprite().getImage().impl_getUrl().contains("3.0s")) {
							runTime.getEngine().getEntities().get(i).getSprite().setImage(imageDk);
						}
						else if(runTime.getEngine().getEntities().get(i).getSprite().getImage().impl_getUrl().contains("4.0s")) {
							runTime.getEngine().getEntities().get(i).getSprite().setImage(imageBanshee);
						}
					}
					GameInterface.unitDescriptionText.clear();
					//sets the images back to originals if they are selected
				}
			}
        });
        // adds the units into an array list
        unitPool.add(unit);
        return unit;
    }

    private void spawnUnit() {
        Unit newUnit;
        Graph graph = this.runTime.getEngine().getGraph();

        if (unitPool.size() > 0) {
            newUnit = unitPool.remove(0);
        } else {
            newUnit = CreateUnit(runTime.getEngine().getGraph(), runTime.getRenderer(), graph.getNodes().get(graph.getNodes().size() - 1));
        }
        spawnCount++;
        runTime.getEngine().getEntities().add(newUnit);
        runTime.getRenderer().drawInitialEntity(newUnit);
        runTime.getRenderer().produceRouteVisual(runTime.getRenderer().produceRoute(newUnit.getRoute())).play();
    }

    private void despawnUnit(Unit unit) {
        unitPool.add(unit);
        //remove from list here?
    }

    public void update() {
        /*if (cooldown > 0)
        {
            cooldown--;
        }
        else*/
        {
            if (spawnCount < spawnlimit) {
                cooldown = 60;
                spawnUnit();
            }
        }
    }
    public void setSpawnlimit(int spawnlimit) {
        this.spawnlimit = spawnlimit;
    }
}
