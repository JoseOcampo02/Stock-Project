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

//


public class Main {
	
	
	
	public static double calculateSMA(double[] prices) {
	    double sum = 0.0;
	    
	    for (int i = 0; i < prices.length; i++) {
	        sum += prices[i];
	    }
	    
	    return sum / prices.length;
	}
	
	public static double[] calculateEMA(double[] prices, int period) {
		
		//ATM period = 26;
		double weight = 2.0 / (period + 1);
	    
		
		//creates array at size of period
	    double[] sma = new double[period]; // Example: 26 -> 0-25
	    
	    
	    //populates with first indices, 0 - (period - 1)
	    for (int i = 0; i < period; i++) { 
	    	sma[i] = prices[i];
	    }
	    
	    
	    
	    //when calculating the first EMA, the previous EMA is just the SMA of the desired period
	    double ema = calculateSMA(sma); 
	    
	    
	    //is the closingPrice for rest of the prices to calculate, starting from index period
	    double lastPrice;
	    
	    //this is the EMA array to fill up using remaining data. i need closing price for previous day
	    double[] emaArray = new double[prices.length - period]; // rn its 53 - 26 (prices.length - period)
	    
	    for(int i = 0; i < emaArray.length; i++) {
	    	
	    	lastPrice = prices[period];
	    	ema = (lastPrice - ema) * weight + ema;
	    	emaArray[i] = ema;
	    	
	    	period++;
	    }
	    
	    
	    
	    //double lastEma = emaArray[emaArray.length - 1];
	    
	    return emaArray;
	    
	   
	    
	    ////////////????????!!!!!!!!!!!!!!!!!!!!
	}

      
	public static void main(String[] args) throws IOException {
  
		Scanner scanner = new Scanner(System.in);
	    System.out.print("\nSpanning Back how many days of of closing marketprice would you like: ");
	    int dayX = scanner.nextInt();
	    scanner.close();
	    System.out.println("You entered: " + dayX);
		
		
		
		Calendar from = Calendar.getInstance();
		from.add(Calendar.DAY_OF_MONTH, -dayX); // set the 'from' calendar x(neg) days ago/// needs to be larger than desired days to skip over closed days
        Calendar to = Calendar.getInstance();
        Stock google = YahooFinance.get("AAPL", from, to, Interval.DAILY);
        
        
        int marketDays = google.getHistory().size();
        System.out.println("\nOpen Market Days: " + marketDays + " This is how much data will be considered in the calculation.");
        
      
        List<HistoricalQuote> history = google.getHistory();
        double[] closingPrices = new double[marketDays];
        
        
        //fills up closingPrices array with closing prices
        int i = 0; 
        for (HistoricalQuote quote : history) {
       
           double closePrice = quote.getAdjClose().doubleValue(); // get the closing price of the quote // adj stock splits // originally in big decimal
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
        

     //numPeriods should be the desired ema 12, 26, etc 
     //later have user decide
     int numPeriods = 20;
     
     double p26ema[] = calculateEMA(closingPrices, numPeriods);
     
     numPeriods = 12;
     double p12ema[] = calculateEMA(closingPrices, numPeriods);
     
     //double macd = p12 - p26; (last elements)
     
     System.out.println("\nThis is 20 day EMA: " + p26ema[p26ema.length - 1]);
     System.out.println("\nThis is 12 day EMA: " + p12ema[p12ema.length - 1]);
     
     //System.out.println("\nThis is MACD: " + (p12ema[p12ema.length - 1] - p26ema[p26ema.length - 1]));
     
    }

}

