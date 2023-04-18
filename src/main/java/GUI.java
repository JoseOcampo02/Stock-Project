import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class GUI extends Application {


	@Override
	public void start(Stage primaryStage) {
		try {

			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));

			Scene scene = new Scene(root,500,500);


			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("WallStreetBits");
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		
		launch(args);
	}
}

