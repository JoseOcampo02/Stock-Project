import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
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
  
    
    /**
     * 
     * @author Alonzo Garcia
     * This allows the images to be seen by the user.
     */
    
    @FXML
    
    ImageView imageView;
    
    Image myImage = new Image(getClass().getResourceAsStream("chart.png"));
    
    public void displayImage() {
     imageView.setImage(myImage);
    }
    
    
    private static List<Integer> findInstances(List<Double> macd, List<Double> signal, List<Double> close) {
        // Start at index 8 to account for the 9-day EMA offset
        int offset = 8;
        List<Integer> buySignalIndices = new ArrayList<>();
        //maybe dont need -1!!
        for (int i = 8; i < macd.size() - 1; i++) {
            double currentMacd = macd.get(i);
            double currentSignal = signal.get(i - offset);

            // Check for MACD line above Signal Line, both lines below zero line (negative numbers)
            if (currentMacd > currentSignal && currentMacd < 0 && currentSignal < 0) {
                buySignalIndices.add(i - offset);
            }

        
            // Check for MACD line below Signal Line, both lines above zero line (positive numbers)
            // if (currentMacd < currentSignal && currentMacd > 0 && currentSignal > 0) {
            //     System.out.println("Sell signal at index: " + indexWithOffset + " with price: " + close.get(i));
            // }
        }
        return buySignalIndices;
    }
  
    
    /**
     * Initializes the controller and sets up the charts.
     *
     * @param arg0 The URL for the FXML file. (JavaFX framework input)
     * @param arg1 The ResourceBundle for the FXML file. (JavaFX framework input)
     * @author Christian Jaime
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
	    
		//collects data from yahooFinace
		List<Double> realData = new ArrayList<Double>();
        DataCollection history = null;
        
		try {
			history = new DataCollection("AAPL", 190);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		realData = history.getClosingPrices();
	
    
		
	    //create MACD object
	    MACD myMACD = new MACD(realData, 12, 26, 9);
	    
	    //offsets for lines
	    int offsetSignalLine = realData.size() - myMACD.getSignalLine().size();
	    int offsetMACDLine = realData.size() - myMACD.getMACDline().size();
	    int offsetHistogram = realData.size() - myMACD.getHistogram().size();
	    
	    //trying to display instances correctly still off
	    List<Integer> buySignalIndices = findInstances(myMACD.getMACDline(), myMACD.getSignalLine(), realData);
	    
	    //for (int i = 0; i < buySignalIndices.size(); i++) {
	    //	buySignalIndices.set(i, myMACD.getMACDline().size() - buySignalIndices.get(i));
	    //}
	    plotBuySignalIndices(lineChart, buySignalIndices, offsetSignalLine);
	    

	    
	    // structure for line chart (individual line)  
	    Series<Number, Number> priceLine = new XYChart.Series<>();
	    priceLine.setName("Price");
	    for (int i = 0; i < realData.size(); i++) {
	    	priceLine.getData().add(new Data<Number, Number>(i + 1, realData.get(i)));
	    }
	   
	
	    
	    Series<Number, Number> macdline = new XYChart.Series<>();
	    macdline.setName("MACD Line");
	    for (int i = 0; i < myMACD.getMACDline().size(); i++) {
	    	macdline.getData().add(new Data<Number, Number>(i + 1 + offsetMACDLine, myMACD.getMACDline().get(i)));
	    }
	    
	    Series<Number, Number> signalLine = new XYChart.Series<>();
	    signalLine.setName("MACD Signal Line");
	    for (int i = 0; i < myMACD.getSignalLine().size(); i++) {
	    	signalLine.getData().add(new Data<Number, Number>(i + 1 + offsetSignalLine, myMACD.getSignalLine().get(i)));
	    }
	    
	    
	    Series<Number, Number> histogramLine = new XYChart.Series<>();
	    histogramLine.setName("Histogram");
	    
	    for (int i = 0; i < myMACD.getHistogram().size(); i++) {
	    	histogramLine.getData().add(new Data<Number, Number>(i + 1 + offsetHistogram, myMACD.getHistogram().get(i)));
	    }
	    
	    
	    // adds lines to lineChart
	    lineChart.getData().add(macdline);
	    lineChart.getData().add(signalLine);
	    //lineChart.getData().add(histogramLine);
	    
	    
	    
	    
	    //adds price line to priceChart
	    priceChart.getData().add(priceLine);
	    //priceChart.getData().add(longTrendLine);

	   
	   // Adds mouseMoved event to priceChart
	    priceChart.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
	        handleMouseMove(priceChart, event);
	    });

	    // Adds mouseMoved event to lineChart
	    lineChart.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
	        handleMouseMove(lineChart, event);
	    });

        
	
	    
	}
	
	
	
	
	private void plotBuySignalIndices(LineChart<Number, Number> chart, List<Integer> indices, int slOffset) {
	    int i = 0;
	    //int offsetSL 
		for (Integer index : indices) {
	        XYChart.Series<Number, Number> buySignalSeries = new XYChart.Series<>();
	        buySignalSeries.setName("Buy Signal at " + (index + slOffset));

	        // Add two data points to create a vertical line
	        buySignalSeries.getData().add(new Data<Number, Number>(indices.get(i) + slOffset, 0));
	   

	        chart.getData().add(buySignalSeries);
	        i++;
	    }
	}

	
	
	

	 /**
     * Handles mouse movement events on line charts and displays a tooltip with data values.
     *
     * @param chart The LineChart instance where the mouse event occurred.
     * @param event The MouseEvent containing information about the mouse event.
     * @author Christian Jaime
     */
	
	//X axis location is a little off(by like 2) for the MACD chart 
	private void handleMouseMove(LineChart<Number, Number> chart, MouseEvent event) {
	    if (tooltip == null) {
	        tooltip = new Tooltip();
	    }

	    double mouseX = event.getX();
	    double mouseY = event.getY();

	    // Calculate X axis value based on mouse position
	    NumberAxis xAxis = (NumberAxis) chart.getXAxis();
	    double xValue = xAxis.getValueForDisplay(mouseX).doubleValue() - 10;

	    // Create a StringBuilder to build the tooltip text
	    StringBuilder tooltipText = new StringBuilder("Mouse Position X: " + String.format("%.2f", xValue) + "\n");
	    //StringBuilder tooltipText = new StringBuilder();
	    // Loop through all Series in the chart
	    for (XYChart.Series<Number, Number> series : chart.getData()) {
	        // Find the closest data point to the current X-axis value
	        Data<Number, Number> closestDataPoint = null;
	        double minDistance = Double.MAX_VALUE;
	        for (Data<Number, Number> data : series.getData()) {
	            double dataXValue = data.getXValue().doubleValue();
	            
	            double distance = Math.abs(dataXValue - xValue);
	            if (distance < minDistance) {
	                minDistance = distance;
	                closestDataPoint = data;
	            }
	        }

	        // Append the data point to the tooltip text
	        if (closestDataPoint != null) {
	            tooltipText.append(series.getName() + ": Closest index X(" + String.format("%.2f", closestDataPoint.getXValue().doubleValue()) + ")  Y Values (" + String.format("%.2f", closestDataPoint.getYValue().doubleValue()) + ")\n");
	        }
	    }
	        
	        
	        

	    // Set the tooltip text and show it
	    tooltip.setText(tooltipText.toString());
	    Node node = (Node) event.getSource();
	    Point2D point = node.localToScene(mouseX, mouseY);
	    tooltip.show(node, point.getX() + node.getScene().getWindow().getX() + 15, point.getY() + node.getScene().getWindow().getY() + 15);
	   
	    
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
	
	
}
