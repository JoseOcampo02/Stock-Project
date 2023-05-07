import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.chart.NumberAxis;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import javafx.scene.control.Tooltip;
import javafx.geometry.Point2D;

/**
 * SampleController.java
 * Date: April 18, 2023
 
 * This class is a JavaFX controller responsible for managing the UI, calling data collector, managing mouse move events, displaying stock data,
 * and MACD indicators on line charts.
 * 
 *
 * Important Functions:
 * - initialize(URL arg0, ResourceBundle arg1): Initializes the controller and sets up the charts.
 *   Input: URL arg0 and ResourceBundle arg1 (JavaFX framework inputs).
 *   Output: None.
 *
 * - handleMouseMove(LineChart<Number, Number> chart, MouseEvent event): Handles mouse movement
 *   events on line charts and displays a tooltip with data values.
 *   Input: LineChart<Number, Number> chart and MouseEvent event.
 *   Output: None.
 *   
 * Important Data Structures:
 * - ArrayList<Double> closingPrices: Stores the closing prices of the stock.
 * - Series<Number, Number> lines: Stores the index and data value of lines that will be displayed.
 * - LineChart<Number, Number> lineChart: Displays the MACD indicators.
 * - LineChart<Number, Number> priceChart: Displays the stock price.
 * 
 * Algorithms: None.
 * 
 *
 *   
 *   
 * @version 1.0
 * @author Christian Jaime
 */

public class SampleController implements Initializable {	
	
	
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private LineChart<Number, Number> priceChart;
    
    private Tooltip tooltip;
  
    
    @FXML
    private Label TSLAPrice;
    
    /**
      
      @author Alonzo Garcia
      This allows the images to be seen by the user.
      
     */
    
    @FXML
    
    ImageView imageView;
    
    Image myImage = new Image(getClass().getResourceAsStream("chart.png"));
    
    public void displayImage() {
     imageView.setImage(myImage);
    }
    
    @FXML
    ImageView wsjLogo;
    @FXML
    ImageView cnbcLogo;
    @FXML
    ImageView marketWatchLogo;
    @FXML
    ImageView investopediaLogo;
    @FXML
    ImageView yahooFinLogo;
    @FXML
    ImageView bloomLogo;

	public void initialize(URL arg0, ResourceBundle arg1) {
	    
	    
	    
	}

	public void changeToProfileView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("profileView.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
	
	public void changeToInvestmentsView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("investmentsView.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
	
	public void changeToAnalysisView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("AnalysisView.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
	
	public void changeToChartsView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("chartsView.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
	
	public void wsjClick(ActionEvent event) throws IOException, URISyntaxException {
	    
	    Desktop.getDesktop().browse(new URI("https://www.wsj.com/"));
	    
	}
	
	public void cnbcClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://www.cnbc.com/"));
        
    }
	
	public void marketWatchClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://www.marketwatch.com/"));
        
    }
	
	public void investopediaClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://www.investopedia.com/"));
        
    }
	
	public void yahooFinClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://finance.yahoo.com/"));
        
    }
	
	public void bloomClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://www.bloomberg.com/"));
        
    }
}
