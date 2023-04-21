import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.chart.NumberAxis;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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


//import com.example.main.Main;

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
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		//collects data from yahooFinace
		List<Double> realData = new ArrayList<Double>();
        DataCollection history = null;
        
		try {
			history = new DataCollection("TSLA", 120);
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
	    
	    
	    // structure for line chart (individual line)  
	    
	    
	    Series<Number, Number> priceLine = new XYChart.Series<>();
	    priceLine.setName("Price");
	    for (int i = 0; i < realData.size(); i++) {
	    	priceLine.getData().add(new Data<Number, Number>(i + 1, realData.get(i)));
	    }
	    
	    Series<Number, Number> histogramLine = new XYChart.Series<>();
	    histogramLine.setName("Histogram");
	    
	    for (int i = 0; i < myMACD.getHistogram().size(); i++) {
	    	histogramLine.getData().add(new Data<Number, Number>(i + 1 + offsetHistogram, myMACD.getHistogram().get(i)));
	    }
	    
	    Series<Number, Number> signalLine = new XYChart.Series<>();
	    signalLine.setName("MACD Signal Line");
	    for (int i = 0; i < myMACD.getSignalLine().size(); i++) {
	    	signalLine.getData().add(new Data<Number, Number>(i + 1 + offsetSignalLine, myMACD.getSignalLine().get(i)));
	    }
	    
	    Series<Number, Number> macdline = new XYChart.Series<>();
	    macdline.setName("MACD Line");
	    for (int i = 0; i < myMACD.getMACDline().size(); i++) {
	    	macdline.getData().add(new Data<Number, Number>(i + 1 + offsetMACDLine, myMACD.getMACDline().get(i)));
	    }
	    
	    
	    
	    
	    
	    // adds lines to lineChart
	    lineChart.getData().add(histogramLine);
	    lineChart.getData().add(signalLine);
	    lineChart.getData().add(macdline);
	    
	    
	    
	    
	    //adds price line to priceChart
	    priceChart.getData().add(priceLine);

	   
	 // Add mouseMoved event to priceChart
	    priceChart.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
	        handleMouseMove(priceChart, event);
	    });

	    // Add mouseMoved event to lineChart
	    lineChart.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
	        handleMouseMove(lineChart, event);
	    });


	    
	}
	
	
	


	
	private void handleMouseMove(LineChart<Number, Number> chart, MouseEvent event) {
	    if (tooltip == null) {
	        tooltip = new Tooltip();
	    }

	    double mouseX = event.getX();
	    double mouseY = event.getY();

	    // Calculate X-axis value based on mouse position
	    NumberAxis xAxis = (NumberAxis) chart.getXAxis();
	    double xValue = xAxis.getValueForDisplay(mouseX).doubleValue();

	    // Create a StringBuilder to build the tooltip text
	    StringBuilder tooltipText = new StringBuilder("X: " + String.format("%.2f", xValue) + "\n");

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
	            tooltipText.append(series.getName() + ": Y(" + String.format("%.2f", closestDataPoint.getYValue().doubleValue()) + ")\n");
	        }
	    }

	    // Set the tooltip text and show it
	    tooltip.setText(tooltipText.toString());
	    Node node = (Node) event.getSource();
	    Point2D point = node.localToScene(mouseX, mouseY);
	    tooltip.show(node, point.getX() + node.getScene().getWindow().getX() + 15, point.getY() + node.getScene().getWindow().getY() + 15);
	}

	
	
	
}