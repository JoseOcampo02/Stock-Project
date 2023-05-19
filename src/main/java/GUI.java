import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * 
 * @author Alonzo Garcia
 * Main code that establishes the GUI and sets border
 *
 */

public class GUI extends Application {


	@Override
	public void start(Stage primaryStage) {
		try {

			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));


			Scene scene = new Scene(root, 1000, 700);


			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("WallStreetBits");
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	    // This line downgrades the YahooFinance API from v7 to v6, since data collection had not been working for v7 lately
	    System.setProperty("yahoofinance.baseurl.quotesquery1v7", "https://query1.finance.yahoo.com/v6/finance/quote");
	    
		launch(args);
	}
}

