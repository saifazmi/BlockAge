package core;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import menus.MenuHandler;

public class CoreGUI extends Application {
    private static final Logger LOG = Logger.getLogger(CoreGUI.class.getName());

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static Stage primaryStage;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        	CoreGUI.primaryStage = primaryStage;
        	primaryStage.setResizable(false);
			@SuppressWarnings("unused")
			MenuHandler menuHandler = new MenuHandler(primaryStage);
			MenuHandler.switchScene(MenuHandler.MAIN_MENU);
			
			primaryStage.setOnCloseRequest(e -> {
				CoreEngine.setEngineState(false);
				Platform.exit();
			});
            primaryStage.show();
        }
		catch (Exception e)
		{
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }
}