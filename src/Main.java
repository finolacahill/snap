import controller.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the beginning of the game application.
 */
public class Main extends Application {
	/**
	 * Starts the application
	 * @param primaryStage
	 * the primary Stage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			MenuController manager = new MenuController();
			primaryStage = manager.getMainStage();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
