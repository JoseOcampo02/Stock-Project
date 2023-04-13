import javafx.scene.control.Tooltip;
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
    

    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		//collects data from yahooFinace
		List<Double> realData = new ArrayList<Double>();
        DataCollection history = null;
        
		try {
			history = new DataCollection("AAPL", 120);
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

	   


	    
	}
	
	
	



	
	
	
	
}
	