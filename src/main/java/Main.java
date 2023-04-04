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



class MACD {
    private final List<Double> closingPrices;
    private final int shortPeriod;
    private final int longPeriod;
    private final int signalPeriod;
    private EMA shortEma;
    private EMA longEma;
    private EMA signalEma;
    private List<Double> macdLine;
    private List<Double> signalLine;
    private List<Double> histogram;

    public MACD(List<Double> closingPrices, int shortPeriod, int longPeriod, int signalPeriod) {
        this.closingPrices = closingPrices;
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.signalPeriod = signalPeriod;
        calculateMACD();
    }

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

        histogram = new ArrayList<Double>();
        for (int i = 0; i < signalLine.size(); i++) {
            histogram.add(macdLine.get(i) - signalLine.get(i));
        }
    }

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
}

class EMA {
    private final int period;
    private final List<Double> emaList;
    
    
    public EMA(int period, List<Double> closingPricesArg) {
        this.period = period;
        this.emaList = calculate(closingPricesArg);
    }

    public List<Double> calculate(List<Double> prices) {
        List<Double> emaValues = new ArrayList<Double>();
        double multiplier = 2.0 / (period + 1);

        // Calculate the initial SMA
        double sma = 0;
        for (int i = 0; i < period; i++) {
            sma += prices.get(i);
        }
        sma /= period;

        // Initialize the EMA values list with the initial SMA
        emaValues.add(sma);

        // Calculate the remaining EMA values
        for (int i = period; i < prices.size(); i++) {
            double ema = (prices.get(i) - emaValues.get(i - period)) * multiplier + emaValues.get(i - period);
            emaValues.add(ema);
        }

        return emaValues;
    }
    
    
    public void printEma() {
    	System.out.println("\nSize: " + this.getEmaList().size());
        System.out.println("EMA values: ");
        for (Double emaValue : this.getEmaList()) {
            System.out.println(emaValue);
        }
    }

	public List<Double> getEmaList() {
		return emaList;
	}
    
}



public class Main {
	
	
	  
	
	 

     

	public static void main(String[] args) throws IOException {
  
		//How many days back? Defaults to 50 for the default 26 12 9 but could be changed with ease 
		//Scanner scanner = new Scanner(System.in);
	    System.out.print("\nSpanning Back how many days of closing marketprice would you like: ");
	    //int dayX = scanner.nextInt();
	    int dayX = 70;
	    //scanner.close();
	    System.out.println("You entered: " + dayX);
		
		
	    String ticker = "AAPL";
		
		Calendar from = Calendar.getInstance();
		from.add(Calendar.DAY_OF_MONTH, -dayX); // set the 'from' calendar x(neg) days ago/// needs to be larger than desired days to skip over closed days
        Calendar to = Calendar.getInstance();
        Stock google = YahooFinance.get(ticker, from, to, Interval.DAILY);
        
        
        int marketDays = google.getHistory().size();
        System.out.println("\nOpen Market Days: " + marketDays + " This is how much data will be considered in the calculation.");
        
      
        List<HistoricalQuote> history = google.getHistory();
        double[] closingPrices = new double[marketDays];
        List<Double> closingPricesL = new ArrayList<Double>();
        
        //fills up closingPrices array with closing prices
        int i = 0; 
        for (HistoricalQuote quote : history) {
       
           double closePrice = quote.getAdjClose().doubleValue(); // get the closing price of the quote // adj stock splits // originally in big decimal
           closingPricesL.add(quote.getAdjClose().doubleValue());
           closingPrices[i] = closePrice; // add the closing price to the array

           //this just info print
           Calendar date = quote.getDate(); // get the date of the historical quote
           int year = date.get(Calendar.YEAR); // extract the year from the date
           int month = date.get(Calendar.MONTH) + 1; // extract the month from the date (note that Calendar.MONTH is zero-based, so we add 1)
           int dayOfMonth = date.get(Calendar.DAY_OF_MONTH); // extract the day of the month from the date
           System.out.println(i + "Historical quote date: " + year + "-" + month + "-" + dayOfMonth + "\tPrice "+ closePrice);
           //
           
           i++;  
        }
        
        
     
        MACD myMACD = new MACD(closingPricesL, 12, 26, 9);
        myMACD.printResults();
        
        EMA shortEMA = new EMA(12, closingPricesL);
        
        shortEMA.printEma();
        
        EMA longEMA = new EMA(26, closingPricesL);
        
        longEMA.printEma();
        
  
    }

}