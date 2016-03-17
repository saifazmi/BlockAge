package tutorial;

import core.BaseSpawner;
import core.CoreEngine;
import core.GameRunTime;
import entity.Blockade;
import gui.GameInterface;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dominic on 13/03/16.
 */
public class Tutorial {

    private static Label tutorial = new Label();
    public static boolean active = false;
    private static int step = 0;
    private static int mapBlockadeCount;
    public static boolean routeShown = false;
    public static boolean visualShown = false;
    private static List<Node> oldKids = new ArrayList<>();

    public static void setup() {
        //@TODO cleanup fonts
        String SEPARATOR = "/";
        InputStream fontStream = GameInterface.class.getResourceAsStream(SEPARATOR + "resources" + SEPARATOR + "fonts" + SEPARATOR + "basis33.ttf");

        active = true;
        oldKids.addAll(GameInterface.sortVisualisationPane.getChildren().stream().collect(Collectors.toList()));
        GameInterface.sortVisualisationPane.getChildren().clear();

        GameInterface.sortVisualisationLabel.setText("Tutorial");
        GameInterface.sortVisualisationLabel.setLayoutX(212 - 87.5 / 2);

        tutorial.setPrefSize(270, 260);
        tutorial.setFont(Font.loadFont(fontStream, 17));
        tutorial.setLayoutX(15);
        tutorial.setTextFill(Color.web("#FFE130"));
        tutorial.setWrapText(true);
        tutorial.setOnKeyPressed(e ->
        {
            KeyCode k = e.getCode();
            if (k == KeyCode.ENTER && Tutorial.active) {
                Tutorial.inc();
            }
        });
        GameInterface.sortVisualisationPane.getChildren().add(tutorial);
        CoreEngine.Instance().setPaused(true);

        tutorial.setText("Hello, and welcome to the tutorial. " +
                "This tutorial will instruct you as " +
                "to the basics of the game. " +
                "For the duration of the tutorial, " +
                "the sort visuals will be disabled. " +
                "Press the ENTER key to continue.");
    }

    public static void inc() {
        step++;

        switch (step) {
            case 1:
                tutorial.setText("The aim of the game is to survive " +
                        "as long as possible against the " +
                        "wave of enemy units. " +
                        "Before we get onto the topic of the " +
                        "game, you need to place your base, " +
                        "click anywhere to place it. " +
                        "Once placed some starting blockades " +
                        "will spawn, and the game will start. " +
                        "I will pause it for you. " +
                        "Press the ENTER key to continue.");
                break;
            case 2:
                if (BaseSpawner.Instance().getGoal() == null) {
                    tutorial.setText("You haven't placed the base yet, " +
                            "please place it. " +
                            "Press the ENTER key to continue.");
                    step--;
                } else {
                    mapBlockadeCount = Blockade.getBlockades().size();
                    System.out.println(mapBlockadeCount);
                    tutorial.setText("Good, the enemy units will spawn in the upper right hand corner and try to reach the base. " +
                            "Once these units reach your base, you lose the game. " +
                            "You can place blockades to slow down the units and/or redirect them. " +
                            "However, if you fully surround a unit so it can't reach the base, the game will end and your score will halve. " +
                            "Place one or more blockades of any type on the grid. " +
                            "Press the ENTER key to continue.");
                }
                break;
            case 3:
                if (Blockade.getBlockades().size() == mapBlockadeCount) {
                    System.out.println(Blockade.getBlockades().size());
                    tutorial.setText("You haven't placed any blockades, please place one. " +
                            "Press the ENTER key to continue.");
                    step--;
                } else {
                    tutorial.setText("Good, there are 2 types of blockade: sortable and unsortable. " +
                            "Unsortable blockades can't be broken. " +
                            "Sortable blockades can be brute forced by the units, but this takes time. " +
                            "Units performing a sort will show the sort once they are selected. " +
                            "This feature is disabled during the tutorial. " +
                            "Press the ENTER key to continue.");
                }
                break;
            case 4:
                tutorial.setText("There are 3 different types of search algorithm employed by the units. " +
                        "BFS, DFS, and A* search. These may be familiar to you. " +
                        "The educational aim of this game is to help you understand how these algorithms work. " +
                        "You can click on a unit to select it. This will show you information in the panel above. " +
                        "Click on a unit. " +
                        "Press the ENTER key to continue.");
                break;
            case 5:
                if (GameRunTime.Instance().getLastClicked() != null) {
                    tutorial.setText("Good, here you can see what search and sort this unit employs. " +
                            "You can also press R when selecting a unit to show its current route. " +
                            "Press R to show the current route of the unit you selected. " +
                            "Press the ENTER key to continue.");
                } else {
                    tutorial.setText("You haven't selected a unit, please select one. " +
                            "Press the ENTER key to continue.");
                    step--;
                }
                break;
            case 6:
                if (!Tutorial.routeShown) {
                    tutorial.setText("You haven't shown the route, please show it. " +
                            "Press the ENTER key to continue.");
                    step--;
                } else {
                    tutorial.setText("Good, this should give you some more insight as to how the search is implemented. " +
                            "You can also press SHIFT+R to show a more advanced visual. " +
                            "This visual shows all the paths that the algorithm attempted. " +
                            "Press SHIFT+R to show this visual. " +
                            "Press the ENTER key to continue.");
                }
                break;
            case 7:
                if (!Tutorial.visualShown) {
                    tutorial.setText("You haven't shown the visual, please show it. " +
                            "Press the ENTER key to continue.");
                    step--;
                } else {
                    tutorial.setText("Good, this visual shows all the nodes that are considered by the search in the order they are considered. " +
                            "By studying this, along with the current route, you should be able to place blockades intelligently. " +
                            "This will allow you to survive longer and improve your score. " +
                            "This is the end of the tutorial, the game will unpause once you continue. " +
                            "Press the ENTER key to continue.");
                }
                break;
            case 8:
                reset();
        }
    }

    public static void reset() {
        Tutorial.active = false;
        step = 0;
        tutorial = new Label();
        mapBlockadeCount = 0;
        routeShown = false;
        visualShown = false;

        GameInterface.sortVisualisationPane.getChildren().clear();
        GameInterface.sortVisualisationPane.getChildren().addAll(oldKids);
        GameInterface.sortVisualisationLabel.setText("Sort Visualisation");
        GameInterface.sortVisualisationLabel.setLayoutX(212 - 197 / 2);
        //@TODO reinitialise sort stuff if needed
        CoreEngine.Instance().setPaused(false);
    }

}
