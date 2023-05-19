
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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