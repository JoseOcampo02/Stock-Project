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
                                                // Stock[2] -> Tesla
    
    private Tooltip tooltip;
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
        /*
        stockArr = new Stock[3];
        stockData = new BigDecimal[3][];
        // Filling 26 days worth of data for S&P500 manually
        dataCollectionManual();        
        */
        
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
    
    public void dataCollectionManual() {
        
        stockData[0] = new BigDecimal[130];
        stockData[0][0] = new BigDecimal("3871.98");
        stockData[0][1] = new BigDecimal("3856.10");
        stockData[0][2] = new BigDecimal("3759.69");
        stockData[0][3] = new BigDecimal("3719.89");
        stockData[0][4] = new BigDecimal("3770.55");
        stockData[0][5] = new BigDecimal("3806.80");
        stockData[0][6] = new BigDecimal("3828.11");
        stockData[0][7] = new BigDecimal("3748.57");
        stockData[0][8] = new BigDecimal("3956.37");
        stockData[0][9] = new BigDecimal("3992.93");
        stockData[0][10] = new BigDecimal("3957.25");
        stockData[0][11] = new BigDecimal("3991.73");
        stockData[0][12] = new BigDecimal("3958.79");
        stockData[0][13] = new BigDecimal("3946.56");
        stockData[0][14] = new BigDecimal("3965.34");
        stockData[0][15] = new BigDecimal("3949.94");
        stockData[0][16] = new BigDecimal("4003.58");
        stockData[0][17] = new BigDecimal("4027.26");
        stockData[0][18] = new BigDecimal("4026.12");
        stockData[0][19] = new BigDecimal("3963.94");
        stockData[0][20] = new BigDecimal("3957.63");
        stockData[0][21] = new BigDecimal("4080.11");
        stockData[0][22] = new BigDecimal("4076.57");
        stockData[0][23] = new BigDecimal("4071.70");
        stockData[0][24] = new BigDecimal("3998.84");
        stockData[0][25] = new BigDecimal("3941.26");
        stockData[0][26] = new BigDecimal("3933.92");
        stockData[0][27] = new BigDecimal("3963.51");
        stockData[0][28] = new BigDecimal("3934.38");
        stockData[0][29] = new BigDecimal("3990.56");
        stockData[0][30] = new BigDecimal("4019.65");
        stockData[0][31] = new BigDecimal("3995.32");
        stockData[0][32] = new BigDecimal("3895.75");
        stockData[0][33] = new BigDecimal("3852.36");
        stockData[0][34] = new BigDecimal("3817.66");
        stockData[0][35] = new BigDecimal("3821.62");
        stockData[0][36] = new BigDecimal("3878.44");
        stockData[0][37] = new BigDecimal("3822.39");
        stockData[0][38] = new BigDecimal("3844.82");
        stockData[0][39] = new BigDecimal("3829.25");
        stockData[0][40] = new BigDecimal("3783.22");
        stockData[0][41] = new BigDecimal("3849.28");
        stockData[0][42] = new BigDecimal("3839.50");
        stockData[0][43] = new BigDecimal("3824.14");
        stockData[0][44] = new BigDecimal("3852.97");
        stockData[0][45] = new BigDecimal("3808.10");
        stockData[0][46] = new BigDecimal("3895.08");
        stockData[0][47] = new BigDecimal("3892.09");
        stockData[0][48] = new BigDecimal("3919.25");
        stockData[0][49] = new BigDecimal("3969.61");
        stockData[0][50] = new BigDecimal("3983.17");
        stockData[0][51] = new BigDecimal("3999.09");
        stockData[0][52] = new BigDecimal("3990.97");
        stockData[0][53] = new BigDecimal("3928.86");
        stockData[0][54] = new BigDecimal("3898.85");
        stockData[0][55] = new BigDecimal("3972.61");
        stockData[0][56] = new BigDecimal("4019.81");
        stockData[0][57] = new BigDecimal("4016.95");
        stockData[0][58] = new BigDecimal("4016.22");
        stockData[0][59] = new BigDecimal("4060.43");
        stockData[0][60] = new BigDecimal("4070.56");
        stockData[0][61] = new BigDecimal("4017.77");
        stockData[0][62] = new BigDecimal("4076.60");
        stockData[0][63] = new BigDecimal("4119.21");
        stockData[0][64] = new BigDecimal("4179.76");
        stockData[0][65] = new BigDecimal("4136.48");
        stockData[0][66] = new BigDecimal("4111.08");
        stockData[0][67] = new BigDecimal("4164.00");
        stockData[0][68] = new BigDecimal("4117.86");
        stockData[0][69] = new BigDecimal("4081.50");
        stockData[0][70] = new BigDecimal("4090.46");
        stockData[0][71] = new BigDecimal("4137.29");
        stockData[0][72] = new BigDecimal("4136.13");
        stockData[0][73] = new BigDecimal("4147.60");
        stockData[0][74] = new BigDecimal("4090.41");
        stockData[0][75] = new BigDecimal("4079.09");
        stockData[0][76] = new BigDecimal("3997.34");
        stockData[0][77] = new BigDecimal("3991.05");
        stockData[0][78] = new BigDecimal("4012.32");
        stockData[0][79] = new BigDecimal("3970.04");
        stockData[0][80] = new BigDecimal("3982.24");
        stockData[0][81] = new BigDecimal("3970.15");
        stockData[0][82] = new BigDecimal("3951.39");
        stockData[0][83] = new BigDecimal("3981.35");
        stockData[0][84] = new BigDecimal("4045.64");
        stockData[0][85] = new BigDecimal("4048.42");
        stockData[0][86] = new BigDecimal("3986.37");
        stockData[0][87] = new BigDecimal("3992.01");
        stockData[0][88] = new BigDecimal("3918.32");
        stockData[0][89] = new BigDecimal("3861.59");
        stockData[0][90] = new BigDecimal("3855.76");
        stockData[0][91] = new BigDecimal("3919.29");
        stockData[0][92] = new BigDecimal("3891.93");
        stockData[0][93] = new BigDecimal("3960.28");
        stockData[0][94] = new BigDecimal("3916.64");
        stockData[0][95] = new BigDecimal("3951.57");
        stockData[0][96] = new BigDecimal("4002.87");
        stockData[0][97] = new BigDecimal("3936.97");
        stockData[0][98] = new BigDecimal("3948.72");
        stockData[0][99] = new BigDecimal("3970.99");
        stockData[0][100] = new BigDecimal("3977.53");
        stockData[0][101] = new BigDecimal("3971.27");
        stockData[0][102] = new BigDecimal("4027.81");
        stockData[0][103] = new BigDecimal("4050.83");
        stockData[0][104] = new BigDecimal("4109.31");
        stockData[0][105] = new BigDecimal("4100.60");
        stockData[0][106] = new BigDecimal("4100.60");
        stockData[0][107] = new BigDecimal("4090.38");
        stockData[0][108] = new BigDecimal("4105.02");
        stockData[0][109] = new BigDecimal("4109.11");
        stockData[0][110] = new BigDecimal("4108.94");
        stockData[0][111] = new BigDecimal("4091.95");
        stockData[0][112] = new BigDecimal("4146.22");
        stockData[0][113] = new BigDecimal("4137.64");
        stockData[0][114] = new BigDecimal("4151.32");
        stockData[0][115] = new BigDecimal("4154.87");
        stockData[0][116] = new BigDecimal("4154.52");
        stockData[0][117] = new BigDecimal("4129.79");
        stockData[0][118] = new BigDecimal("4133.52");
        stockData[0][119] = new BigDecimal("4137.04");
        stockData[0][120] = new BigDecimal("4071.63");
        stockData[0][121] = new BigDecimal("4055.99");
        stockData[0][122] = new BigDecimal("4135.35");
        stockData[0][123] = new BigDecimal("4169.48");
        stockData[0][124] = new BigDecimal("4167.87");
        stockData[0][125] = new BigDecimal("4119.58");
        stockData[0][126] = new BigDecimal("4090.75");
        stockData[0][127] = new BigDecimal("4061.22");
        stockData[0][128] = new BigDecimal("4136.25");
        stockData[0][129] = new BigDecimal("4138.12");
        
        stockData[1] = new BigDecimal[26];
        stockData[1][0] = new BigDecimal("15374.91");
        stockData[1][1] = new BigDecimal("15487.76");
        stockData[1][2] = new BigDecimal("15374.11");
        stockData[1][3] = new BigDecimal("15368.26");
        stockData[1][4] = new BigDecimal("15379.13");
        stockData[1][5] = new BigDecimal("15427.18");
        stockData[1][6] = new BigDecimal("15520.92");
        stockData[1][7] = new BigDecimal("15500.93");
        stockData[1][8] = new BigDecimal("15630.89");
        stockData[1][9] = new BigDecimal("15601.78");
        stockData[1][10] = new BigDecimal("15668.34");
        stockData[1][11] = new BigDecimal("15684.96");
        stockData[1][12] = new BigDecimal("15653.62");
        stockData[1][13] = new BigDecimal("15582.94");
        stockData[1][14] = new BigDecimal("15578.93");
        stockData[1][15] = new BigDecimal("15606.71");
        stockData[1][16] = new BigDecimal("15363.53");
        stockData[1][17] = new BigDecimal("15228.58");
        stockData[1][18] = new BigDecimal("15431.64");
        stockData[1][19] = new BigDecimal("15545.88");
        stockData[1][20] = new BigDecimal("15535.89");
        stockData[1][21] = new BigDecimal("15314.89");
        stockData[1][22] = new BigDecimal("15233.85");
        stockData[1][23] = new BigDecimal("15117.67");
        stockData[1][24] = new BigDecimal("15380.87");
        stockData[1][25] = new BigDecimal("15391.27");
        
        stockData[2] = new BigDecimal[130];
        stockData[2][0] = new BigDecimal("150.65");
        stockData[2][1] = new BigDecimal("145.03");
        stockData[2][2] = new BigDecimal("138.88");
        stockData[2][3] = new BigDecimal("138.38");
        stockData[2][4] = new BigDecimal("138.92");
        stockData[2][5] = new BigDecimal("139.50");
        stockData[2][6] = new BigDecimal("134.87");
        stockData[2][7] = new BigDecimal("146.87");
        stockData[2][8] = new BigDecimal("149.70");
        stockData[2][9] = new BigDecimal("148.28");
        stockData[2][10] = new BigDecimal("150.04");
        stockData[2][11] = new BigDecimal("148.79");
        stockData[2][12] = new BigDecimal("150.72");
        stockData[2][13] = new BigDecimal("151.29");
        stockData[2][14] = new BigDecimal("148.01");
        stockData[2][15] = new BigDecimal("150.18");
        stockData[2][16] = new BigDecimal("151.07");
        stockData[2][17] = new BigDecimal("148.11");
        stockData[2][18] = new BigDecimal("144.22");
        stockData[2][19] = new BigDecimal("141.17");
        stockData[2][20] = new BigDecimal("148.03");
        stockData[2][21] = new BigDecimal("148.31");
        stockData[2][22] = new BigDecimal("147.81");
        stockData[2][23] = new BigDecimal("146.63");
        stockData[2][24] = new BigDecimal("142.91");
        stockData[2][25] = new BigDecimal("140.94");
        stockData[2][26] = new BigDecimal("142.65");
        stockData[2][27] = new BigDecimal("142.16");
        stockData[2][28] = new BigDecimal("144.49");
        stockData[2][29] = new BigDecimal("145.47");
        stockData[2][30] = new BigDecimal("143.21");
        stockData[2][31] = new BigDecimal("136.50");
        stockData[2][32] = new BigDecimal("134.51");
        stockData[2][33] = new BigDecimal("132.37");
        stockData[2][34] = new BigDecimal("132.30");
        stockData[2][35] = new BigDecimal("135.45");
        stockData[2][36] = new BigDecimal("132.23");
        stockData[2][37] = new BigDecimal("131.86");
        stockData[2][38] = new BigDecimal("130.03");
        stockData[2][39] = new BigDecimal("126.04");
        stockData[2][40] = new BigDecimal("129.61");
        stockData[2][41] = new BigDecimal("129.93");
        stockData[2][42] = new BigDecimal("125.07");
        stockData[2][43] = new BigDecimal("126.36");
        stockData[2][44] = new BigDecimal("125.02");
        stockData[2][45] = new BigDecimal("129.62");
        stockData[2][46] = new BigDecimal("130.15");
        stockData[2][47] = new BigDecimal("130.73");
        stockData[2][48] = new BigDecimal("133.49");
        stockData[2][49] = new BigDecimal("133.41");
        stockData[2][50] = new BigDecimal("134.76");
        stockData[2][51] = new BigDecimal("135.94");
        stockData[2][52] = new BigDecimal("135.21");
        stockData[2][53] = new BigDecimal("135.27");
        stockData[2][54] = new BigDecimal("137.87");
        stockData[2][55] = new BigDecimal("141.11");
        stockData[2][56] = new BigDecimal("142.53");
        stockData[2][57] = new BigDecimal("141.86");
        stockData[2][58] = new BigDecimal("143.96");
        stockData[2][59] = new BigDecimal("145.93");
        stockData[2][60] = new BigDecimal("143.00");
        stockData[2][61] = new BigDecimal("144.29");
        stockData[2][62] = new BigDecimal("145.43");
        stockData[2][63] = new BigDecimal("150.82");
        stockData[2][64] = new BigDecimal("154.50");
        stockData[2][65] = new BigDecimal("151.73");
        stockData[2][66] = new BigDecimal("154.65");
        stockData[2][67] = new BigDecimal("151.92");
        stockData[2][68] = new BigDecimal("150.87");
        stockData[2][69] = new BigDecimal("151.01");
        stockData[2][70] = new BigDecimal("153.85");
        stockData[2][71] = new BigDecimal("153.20");
        stockData[2][72] = new BigDecimal("155.33");
        stockData[2][73] = new BigDecimal("153.71");
        stockData[2][74] = new BigDecimal("152.55");
        stockData[2][75] = new BigDecimal("148.48");
        stockData[2][76] = new BigDecimal("148.91");
        stockData[2][77] = new BigDecimal("149.40");
        stockData[2][78] = new BigDecimal("146.71");
        stockData[2][79] = new BigDecimal("147.92");
        stockData[2][80] = new BigDecimal("147.41");
        stockData[2][81] = new BigDecimal("145.31");
        stockData[2][82] = new BigDecimal("145.91");
        stockData[2][83] = new BigDecimal("151.03");
        stockData[2][84] = new BigDecimal("153.83");
        stockData[2][85] = new BigDecimal("151.60");
        stockData[2][86] = new BigDecimal("152.87");
        stockData[2][87] = new BigDecimal("150.59");
        stockData[2][88] = new BigDecimal("148.50");
        stockData[2][89] = new BigDecimal("150.47");
        stockData[2][90] = new BigDecimal("152.59");
        stockData[2][91] = new BigDecimal("152.99");
        stockData[2][92] = new BigDecimal("155.85");
        stockData[2][93] = new BigDecimal("155.00");
        stockData[2][94] = new BigDecimal("157.40");
        stockData[2][95] = new BigDecimal("159.28");
        stockData[2][96] = new BigDecimal("157.83");
        stockData[2][97] = new BigDecimal("158.93");
        stockData[2][98] = new BigDecimal("160.25");
        stockData[2][99] = new BigDecimal("158.28");
        stockData[2][100] = new BigDecimal("157.65");
        stockData[2][101] = new BigDecimal("160.77");
        stockData[2][102] = new BigDecimal("162.36");
        stockData[2][103] = new BigDecimal("164.90");
        stockData[2][104] = new BigDecimal("166.17");
        stockData[2][105] = new BigDecimal("165.63");
        stockData[2][106] = new BigDecimal("163.76");
        stockData[2][107] = new BigDecimal("164.66");
        stockData[2][108] = new BigDecimal("162.03");
        stockData[2][109] = new BigDecimal("160.80");
        stockData[2][110] = new BigDecimal("160.10");
        stockData[2][111] = new BigDecimal("165.56");
        stockData[2][112] = new BigDecimal("165.21");
        stockData[2][113] = new BigDecimal("165.23");
        stockData[2][114] = new BigDecimal("166.47");
        stockData[2][115] = new BigDecimal("167.63");
        stockData[2][116] = new BigDecimal("166.65");
        stockData[2][117] = new BigDecimal("165.02");
        stockData[2][118] = new BigDecimal("165.33");
        stockData[2][119] = new BigDecimal("163.77");
        stockData[2][120] = new BigDecimal("163.76");
        stockData[2][121] = new BigDecimal("168.41");
        stockData[2][122] = new BigDecimal("169.68");
        stockData[2][123] = new BigDecimal("169.59");
        stockData[2][124] = new BigDecimal("168.54");
        stockData[2][125] = new BigDecimal("167.45");
        stockData[2][126] = new BigDecimal("165.79");
        stockData[2][127] = new BigDecimal("173.57");
        stockData[2][128] = new BigDecimal("173.50");
        stockData[2][129] = new BigDecimal("171.77");
        
        
    }
    
    public ArrayList<Double> toDoubleList(BigDecimal[] BDData){
        
        ArrayList<Double> toReturn = new ArrayList<Double>();
        for (BigDecimal BD : BDData) {
            toReturn.add(BD.doubleValue());
        }
        
        return toReturn;
        
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
        xAxis.setAutoRanging(false);
        xAxis.setForceZeroInRange(true);
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        
    }

    public void generateMACDChart(int stockSelection) {
        
///*
        
        
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
        
        //ArrayList<Double> realData = toDoubleList(stockData[stockSelection]);
        

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
        
    //*/
        
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
