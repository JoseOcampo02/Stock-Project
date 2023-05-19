import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.chart.NumberAxis;
import javafx.event.ActionEvent;
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
import javafx.geometry.Point2D;

public class chartsViewController implements Initializable{
    
    private Tooltip tooltip;
    private int stockSelection;
    private int chartSelection;
    
    private Stock[] stockArr = new Stock[3];    // Stock[0] -> S&P500
                                                // Stock[1] -> NYSE Composite
                                                // Stock[2] -> Apple
    
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
            stockData[0] = DataProcessing.collectData(stockArr[0]);
            stockArr[1] = YahooFinance.get("^NYA");
            stockData[1] = DataProcessing.collectData(stockArr[1]);
            stockArr[2] = YahooFinance.get("TSLA");
            stockData[2] = DataProcessing.collectData(stockArr[2]);
            
        } catch (Exception e) {
            e.printStackTrace();
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
        
        lineChart.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            handleMouseMove(lineChart, event);
        });
        
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

        //collects data from yahooFinace
        List<Double> realData = new ArrayList<Double>();
        
        try {
            realData = DataProcessing.collectDataMACD(stockArr[stockSelection], 190);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create MACD object
        MACD myMACD = new MACD(realData, 12, 26, 9);
        
        //offsets for lines
        int offsetSignalLine = realData.size() - myMACD.getSignalLine().size();
        int offsetMACDLine = realData.size() - myMACD.getMACDline().size();
        int offsetHistogram = realData.size() - myMACD.getHistogram().size();
       
       
        List<Integer> buySignalIndices = myMACD.findBuyInstances();
        List<Integer> sellSignalIndices = myMACD.findSellInstances();
        
        plotBuySignalIndices(lineChart, buySignalIndices, offsetSignalLine); // buy signal offset is same as signal line offset
        plotSellSignalIndices(lineChart, sellSignalIndices, offsetSignalLine);
        
        // structure for line chart (individual line)  
        Series<Number, Number> priceLine = new XYChart.Series<>();
        priceLine.setName("Price");
        for (int i = 0; i < realData.size(); i++) {
            priceLine.getData().add(new Data<Number, Number>(i + 0, realData.get(i)));
        }
        
        Series<Number, Number> macdline = new XYChart.Series<>();
        macdline.setName("MACD Line");
        for (int i = 0; i < myMACD.getMACDline().size(); i++) {
            macdline.getData().add(new Data<Number, Number>(i + 0 + offsetMACDLine, myMACD.getMACDline().get(i)));
        }
        
        Series<Number, Number> signalLine = new XYChart.Series<>();
        signalLine.setName("MACD Signal Line");
        for (int i = 0; i < myMACD.getSignalLine().size(); i++) {
            signalLine.getData().add(new Data<Number, Number>(i + 0 + offsetSignalLine, myMACD.getSignalLine().get(i)));
        }
        
        
        Series<Number, Number> histogramLine = new XYChart.Series<>();
        histogramLine.setName("Histogram");
        
        for (int i = 0; i < myMACD.getHistogram().size(); i++) {
            histogramLine.getData().add(new Data<Number, Number>(i + 0 + offsetHistogram, myMACD.getHistogram().get(i)));
        }
        
        // adds lines to lineChart
        lineChart.getData().add(macdline);
        lineChart.getData().add(signalLine);
        //lineChart.getData().add(histogramLine);
        //lineChart.getData().add(tempResizePrevention);

        // Adds mouseMoved event to lineChart
        lineChart.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            handleMouseMove(lineChart, event);
        });

        xAxis.setAutoRanging(true);
        xAxis.setForceZeroInRange(true);
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        
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
    
    private void plotSellSignalIndices(LineChart<Number, Number> chart, List<Integer> indices, int slOffset) {
        int i = 0;
        //int offsetSL 
        for (Integer index : indices) {
            XYChart.Series<Number, Number> sellSignalSeries = new XYChart.Series<>();
            sellSignalSeries.setName("Sell Signal at " + (index + slOffset));

            // Add two data points to create a vertical line
            sellSignalSeries.getData().add(new Data<Number, Number>(indices.get(i) + slOffset, 0));
       

            chart.getData().add(sellSignalSeries);
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
    private void handleMouseMove(LineChart<Number, Number> chart, MouseEvent event) {
	    if (tooltip == null) {
	        tooltip = new Tooltip();
	    }

	    double mouseX = event.getX();
	    double mouseY = event.getY();


	    
	    // Calculate X axis value based on mouse position
	    NumberAxis xAxis = (NumberAxis) chart.getXAxis();
	    double xValue = xAxis.getValueForDisplay(mouseX).doubleValue() - 7; //offset adjuster
	

	    
	    // Create a StringBuilder to build the tooltip text
	    StringBuilder tooltipText = new StringBuilder("Mouse Position X: " + String.format("%.2f", xValue) + "\n");
	  
	    
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
	        

	    // Set the tool tip text and show it
	    tooltip.setText(tooltipText.toString());
	    Node node = (Node) event.getSource();
	    Point2D point = node.localToScene(mouseX, mouseY);
	    tooltip.show(node, point.getX() + node.getScene().getWindow().getX() + 35, point.getY() + node.getScene().getWindow().getY() + 35); 
    
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
