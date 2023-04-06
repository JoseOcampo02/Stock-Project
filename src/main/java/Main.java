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

        
        //index where the signal line starts in the MACD line
        int signalStartIndex = signalPeriod - 1; 

        histogram = new ArrayList<Double>();
        for (int i = 0; i < signalLine.size(); i++) {
            histogram.add(macdLine.get(i + signalStartIndex) - signalLine.get(i));
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
            //double ema = (prices.get(i) - emaValues.get(i - period)) * multiplier + emaValues.get(i - period);
        	double ema = (prices.get(i) - emaValues.get(emaValues.size() - 1)) * multiplier + emaValues.get(emaValues.size() - 1);

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



class DataCollection {
    private String ticker;
    private int daysBack;
    private List<Double> closingPrices;

    public DataCollection(String ticker, int daysBack) throws IOException {
        this.ticker = ticker;
        this.daysBack = daysBack;
        collectData();
    }

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

    public String getTicker() {
        return ticker;
    }

    public int getDaysBack() {
        return daysBack;
    }

    public List<Double> getClosingPrices() {
        return closingPrices;
    }

    public int getMarketDays() {
        return closingPrices.size();
    }
    
    public void printClosingPrices() {
        int i = 0;
        for (double price : closingPrices) {
            System.out.println(i + " Closing price: " + price);
            i++;
        }
    }
}




public class Main {

	public static void main(String[] args) throws IOException {
		
		
  
		List<Double> hellaSwag = new ArrayList<Double>();
        DataCollection history =  new DataCollection("AAPL", 120);
		
        hellaSwag = history.getClosingPrices();
     
        
  
        MACD myMACD = new MACD(hellaSwag, 12, 26, 9);
        myMACD.printResults();
        
  
    }

}