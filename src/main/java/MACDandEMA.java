
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * MACDandEMA.java
 * Date: April 18, 2023
 *
 * This class consists of the main method to calculate MACD (Moving Average Convergence Divergence) and EMA (Exponential Moving Average) 
 * for a given stock's historical data. It uses the YahooFinance API to fetch historical stock data. These classes are temporarily in the same .java file for 
 * visibility since the macd class depends on ema.
 *
 * Classes:
 * 1. MACD - Class to calculate the MACD line, signal line, and histogram.
 * 2. EMA - Class to calculate the Exponential Moving Average (EMA) for a given set of prices.
 * 3. DataCollection - Class to fetch historical stock data using the YahooFinance API.(There are currently two versions being developed one using arrays and the other lists. 
 * The array implementation will be used for backlog testing and the list will be used for live collection)
 *
 * Algorithms:
 * - EMA: Exponential Moving Average is used to calculate MACD. It gives more weight to recent prices and thus is more sensitive to recent price changes.
 * An in depth exploration of the math behind the calculation can be found in the ema calculate function.
 * 
 *
 * Data Structures:
 * - ArrayList: Used for storing historical prices, EMA values, MACD values, and signal line values.
 * 
 * @version 1.0
 * @author Christian Jaime
 */

public class MACDandEMA {




	public static void main(String[] args) throws IOException {
		
		
        //DataCollection history =  new DataCollection("AAPL", 190);
		
		List<Double> history = Arrays.asList(
		148.875351, 144.339813, 155.245056, 152.852661, 150.171204, 144.569077, 138.438629, 138.169037, 138.708206, 139.287338, 134.664383, 146.646088, 149.471771, 148.05394, 149.811249, 148.563156, 150.490234, 
		 151.059357, 147.784348, 149.951035, 150.839706, 147.884201, 144.000137, 140.954788, 147.804321, 148.083893, 147.584656, 146.406464, 142.692139, 140.725143, 142.432526, 141.943283, 144.26973, 145.24823, 
		 142.991684, 136.291901, 134.304932, 132.168198, 132.098312, 135.2435, 132.028412, 131.658981, 129.831772, 125.847855, 129.412415, 129.731918, 124.879326, 126.167366, 124.829399, 129.422394, 129.951584, 
		 130.530701, 133.286499, 133.206619, 134.55455, 135.732758, 135.003876, 135.063782, 137.659805, 140.894882, 142.312714, 141.643738, 143.74054, 145.70752, 142.781998, 144.070023, 145.208282, 150.590088, 
		 154.264465, 151.498688, 154.41423, 151.6884, 150.639999, 151.009995, 153.850006, 153.199997, 155.330002, 153.710007, 152.550003, 148.479996, 148.910004, 149.399994, 146.710007, 147.919998, 147.410004, 
		 145.309998, 145.910004, 151.029999, 153.830002, 151.600006, 152.869995, 150.589996, 148.5, 150.470001, 152.589996, 152.990005, 155.850006, 155.0, 157.399994, 159.279999, 157.830002, 158.929993, 160.25, 
		 158.279999, 157.649994, 160.770004, 162.360001, 164.899994, 166.169998, 165.630005, 163.759995, 164.660004, 162.029999, 160.800003, 160.100006, 165.559998, 165.210007, 165.229996, 166.470001, 167.630005, 
		 166.649994, 165.020004, 165.330002, 163.770004, 163.759995, 168.410004, 169.679993, 169.589996, 168.539993, 167.449997);
  
        MACD myMACD = new MACD(history, 12, 26, 9);
        

        
        
        //System.out.println("Data Points " + history.getClosingPrices());
        //myMACD.printResults();

        //EMA longTrendPP = new EMA(5, testPrices);
        //longTrendPP.printEma();
   
        System.out.println( myMACD.findBuyInstances());

        

    }

	
	

	
}

class MACD {
    private List<Double> closingPrices;
    private int shortPeriod;
    private int longPeriod;
    private int signalPeriod;
    private EMA shortEma;
    private EMA longEma;
    private EMA signalEma;
    private List<Double> macdLine;
    private List<Double> signalLine;
    private List<Double> histogram;

    /**
     * Constructs a new MACD object with the given closing prices and periods.
     *
     * @param closingPrices the list of closing prices for a stock
     * @param shortPeriod   the short period for EMA calculation
     * @param longPeriod    the long period for EMA calculation
     * @param signalPeriod  the signal period for EMA calculation
     */
    
    public MACD(List<Double> closingPrices, int shortPeriod, int longPeriod, int signalPeriod) {
        this.closingPrices = closingPrices;
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.signalPeriod = signalPeriod;
        calculateMACD();
    }

    
    /**
     * Calculates the MACD line, signal line, and histogram.
     */
    public void calculateMACD() {
        shortEma = new EMA(shortPeriod, closingPrices);
        longEma = new EMA(longPeriod, closingPrices);

        
        List<Double> shortEmaValues = shortEma.calculate(closingPrices);
        List<Double> longEmaValues = longEma.calculate(closingPrices);

        macdLine = new ArrayList<Double>();

        int longEmaStartIndex = longPeriod - shortPeriod;

        for (int i = 0; i < shortEmaValues.size() - longEmaStartIndex; i++) {
            macdLine.add(shortEmaValues.get(i + longEmaStartIndex) - longEmaValues.get(i));
        }

        signalEma = new EMA(signalPeriod, macdLine);
        signalLine = signalEma.calculate(macdLine);

        
        //index where the signal line starts in the MACD line
        int signalStartIndex = signalPeriod - 1; 

        histogram = new ArrayList<Double>();
        for (int i = 0; i < signalLine.size(); i++) {
            histogram.add(macdLine.get(i + signalStartIndex) - signalLine.get(i));
        }

    }

    /**
     * Prints the results including short-term EMA, long-term EMA, MACD line, MACD signal line, and histogram.
     */
    public void printResults() {
    	
        System.out.println("\nSize: " + shortEma.getEmaList().size());
        System.out.println("Short-term EMA: ");
        for (Double emaValue : shortEma.getEmaList()) {
            System.out.println(emaValue);
        }

        System.out.println("\nSize: " + longEma.getEmaList().size());
        System.out.println("Long-term EMA: ");
        for (Double emaValue : longEma.getEmaList()) {
            System.out.println(emaValue);
        }

        System.out.println("\nSize: " + macdLine.size());
        System.out.println("MACD line: ");
        for (Double macdValue : macdLine) {
            System.out.println(macdValue);
        }

        System.out.println("\nSize: " + signalLine.size());
        System.out.println("MACD signal line: ");
        for (Double signalValue : signalLine) {
            System.out.println(signalValue);
        }
        
        System.out.println("\nSize: " + histogram.size());
        System.out.println("histogram line: ");
        for (Double signalValue : histogram) {
            System.out.println(signalValue);
        }
    }
    
    
    /**
     * @return the macdLine list
     */
    public List<Double> getMACDline() {
		return macdLine;
	}
    
    /**
     * @return the signalLine list
     */
    public List<Double> getSignalLine() {
		return signalLine;
	}
    
    /**
     * @return the histogram list
     */
    public List<Double> getHistogram() {
		return histogram;
	}
    
    
    

    public List<Integer> findBuyInstances() {
        // Start at index 8 to account for the 9-day EMA offset. makes scense since day 9 is on index 8(0-8) and with 9 days we have our first ema value
        int offset = this.signalPeriod - 1;
        List<Integer> buySignalIndices = new ArrayList<>();
        
        
        //maybe dont need -1!! yea i dont see y either
        for (int i = offset; i < this.macdLine.size() - 0; i++) {
            double currentMacd = this.macdLine.get(i);
            double currentSignal = this.signalLine.get(i - offset);

            // Check for MACD line above Signal Line, both lines below zero line (negative numbers)
            if (currentMacd > currentSignal && currentMacd < 0 && currentSignal < 0) {
                buySignalIndices.add(i - offset);
            }

        
            // Check for MACD line cross below Signal Line while both lines above zero line 
            // if (currentMacd < currentSignal && currentMacd > 0 && currentSignal > 0) {
            //     System.out.println("Sell signal at index: " + indexWithOffset + " with price: " + close.get(i));
            // }
        }
        return buySignalIndices;
    }
    
    
    public List<Integer> findSellInstances() {
        // Start at index 8 to account for the 9-day EMA offset. makes scense since day 9 is on index 8(0-8) and with 9 days we have our first ema value
        int offset = this.signalPeriod - 1;
        List<Integer> indices = new ArrayList<>();
        
        
       
        for (int i = offset; i < this.macdLine.size() - 0; i++) {
            double currentMacd = this.macdLine.get(i);
            double currentSignal = this.signalLine.get(i - offset);

    
        
            //Check for MACD line cross below Signal Line while both lines above zero line 
            if (currentMacd < currentSignal && currentMacd > 0 && currentSignal > 0) {
            	indices.add(i - offset);
             }
        }
        return indices;
    }


	public Integer getShortPeriod() {

		return this.shortPeriod;
	}


	public Integer getLongPeriod() {
		
		return this.longPeriod;
	}


	public Integer getSignalPeriod() {

		return this.signalPeriod;
	}
    
    

    
}

class EMA {
    private final int period;
    private final List<Double> emaList;
    
    /**
     * Constructs a new EMA object with the given period and closing prices.
     *
     * @param period           the period for EMA calculation
     * @param closingPricesArg the list of closing prices for a stock
     */
    public EMA(int period, List<Double> closingPricesArg) {
        this.period = period;
        this.emaList = calculate(closingPricesArg);
    }

    
    /**
     * Calculates the Exponential Moving Average for a given set of prices.
     *
     * @param prices the list of closing prices for a stock
     * @return a list of EMA values
     */
    public List<Double> calculate(List<Double> prices) {
        List<Double> emaValues = new ArrayList<Double>();
        
        double multiplier = 2.0 / (period + 1);

        // initial SMA
        double sma = 0.0;
        for (int i = 0; i < period; i++) {
            sma += prices.get(i);
        }
        sma /= period;

        // first ema value is just sma
        emaValues.add(sma);

        // remaining EMA values
        for (int i = period; i < prices.size(); i++) {
        	double ema = (prices.get(i) - emaValues.get(emaValues.size() - 1)) * multiplier + emaValues.get(emaValues.size() - 1);

        	emaValues.add(ema);
        }

        return emaValues;
    }
    
    
    
    /**
     * Prints the EMA values.
     */
    public void printEma() {
    	System.out.println("\nSize: " + this.getEmaList().size());
        System.out.println("EMA values: ");
        for (Double emaValue : this.getEmaList()) {
            System.out.println(emaValue);
        }
    }

    /**
     * @return a list of the calculated ema
     */
	public List<Double> getEmaList() {
		return emaList;
	}


	public Integer getPeriod() {
		
		return period;
	}
	
	
	
    
}


/**
 * Constructs a new DataCollection object with the given ticker and number of days back.
 *
 * @param ticker the stock's ticker symbol
 * @param daysBack the number of days back to fetch historical data
 * @throws IOException if there is an error fetching the data
 */
class DataCollection {
    private String ticker;
    private int daysBack;
    private List<Double> closingPrices;

    public DataCollection(String ticker, int daysBack) throws IOException {
        this.ticker = ticker;
        this.daysBack = daysBack;
        collectData();
    }

    
    
    /**
     * Collects historical data using the YahooFinance API.
     *
     * @throws IOException if there is an issue fetching the data
     */
    private void collectData() throws IOException {
        Calendar from = Calendar.getInstance();
        from.add(Calendar.DAY_OF_MONTH, -daysBack);
        Calendar to = Calendar.getInstance();
        Stock stock = YahooFinance.get(ticker, from, to, Interval.DAILY);
        List<HistoricalQuote> history = stock.getHistory();
        closingPrices = new ArrayList<Double>();
        for (HistoricalQuote quote : history) {
            double closePrice = quote.getAdjClose().doubleValue();
            closingPrices.add(closePrice);
        }
    }


    /**
     * @return the ticker symbol in string
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * @return the int of how many days back in time.
     */
    public int getDaysBack() {
        return daysBack;
    }

    /**
     * @return the list closing prices
     */
    public List<Double> getClosingPrices() {
        return closingPrices;
    }

    /**
     * @return the int amount of market days and or size
     */
    public int getMarketDays() {
        return closingPrices.size();
    }
    
    
    /**
     * Prints the closing prices.
     */
    public void printClosingPrices() {
        int i = 0;
        for (double price : closingPrices) {
            System.out.println(i + " Closing price: " + price);
            i++;
        }
    }
}