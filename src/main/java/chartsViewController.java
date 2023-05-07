import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
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

public class chartsViewController implements Initializable{

    private Stock[] myStocks = new Stock[3];    // Stock[0] -> S&P500
                                                // Stock[1] -> NYSE Composite
                                                // Stock[2] -> Apple
    
    private int stockSelection;
    private int chartSelection;
    private Stock[] stockArr;
    private BigDecimal[][] stockData;
    
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        stockSelection = 0;
        chartSelection = 0;
        
        
        try {
            
            stockArr = new Stock[3];
            stockData = new BigDecimal[3][];
            
            stockArr[0] = YahooFinance.get("^GSPC");
            stockData[0] = DataCollection(stockArr[0]);
            stockArr[1] = YahooFinance.get("^NYA");
            stockData[1] = DataCollection(stockArr[1]);
            stockArr[2] = YahooFinance.get("TSLA");
            stockData[2] = DataCollection(stockArr[2]);
            
        } catch (Exception e) {
            
        }
        
    }

    public void stockSelection0 (ActionEvent event) throws IOException{
        stockSelection = 0;
    }
    public void stockSelection1 (ActionEvent event) throws IOException{
        stockSelection = 1;
    }
    public void stockSelection2 (ActionEvent event) throws IOException{
        stockSelection = 2;
    }
    
    public void chartSelection0 (ActionEvent event) throws IOException{
        chartSelection = 0;
    }
    public void chartSelection1 (ActionEvent event) throws IOException{
        chartSelection = 1;
    }
    public void chartSelection2 (ActionEvent event) throws IOException{
        chartSelection = 2;
    }
    
    public void generateChart(ActionEvent event) throws IOException{
        
        lineChart.getData().clear();
        
        if (chartSelection == 0) {
            
            generatePriceChart(stockSelection);
        }
        
        if (chartSelection == 1) {
            generateLinRegChart(stockSelection);
        }
        
        if (chartSelection == 2) {
            generateMACDChart(stockSelection);
        }
        
    }
    
    public void generatePriceChart(int stockSelection) throws IOException{
        
        BigDecimal[] myData = this.stockData[stockSelection];
        
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Price Chart");
        for (int i = 0; i < myData.length; i++) {
            
            series1.getData().add(new XYChart.Data<>(i, myData[i]));
            
        }
        lineChart.getData().add(series1);
        xAxis.setAutoRanging(true);
        xAxis.setForceZeroInRange(true);
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        
        
    }
    
    public BigDecimal[] DataCollection(Stock myStock) throws IOException{
        
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.MONTH, -1); // from 1 month ago, default
        List<HistoricalQuote> myQuotes = myStock.getHistory(from, to, Interval.DAILY);
        
        
        int dataSetSize = myQuotes.size();
        BigDecimal[] myData = new BigDecimal[dataSetSize];
        for (int i = 0; i < dataSetSize; i++)
            myData[i] = myQuotes.get(i).getClose().setScale(2, RoundingMode.HALF_UP);
        
        return myData;
        
    }
    
    public void generateLinRegChart(int stockSelection) throws IOException{
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(stockData[stockSelection]);
        BigDecimal[] myData = stockData[stockSelection];
        
        BigDecimal lastIndexAsBD = new BigDecimal(Integer.toString(stockData[stockSelection].length - 1));
        BigDecimal left = myAnalysis.getYInt();
        BigDecimal right = (myAnalysis.getSlope().multiply(lastIndexAsBD)).add(left);
        
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Closing prices");
        for (int i = 0; i < myData.length; i++)
            series1.getData().add(new XYChart.Data<>(i, myData[i]));
        
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Regression line");
        series2.getData().add(new XYChart.Data<>(0, left));
        series2.getData().add(new XYChart.Data<>((myData.length - 1), right));
        
        lineChart.getData().addAll(series1, series2);
        xAxis.setAutoRanging(true);
        xAxis.setForceZeroInRange(true);
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        
    }

    public void generateMACDChart(int stockSelection) {
        
        
        
    }
    
    public void returnToHomeView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
    
}
