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

	/*
        Stock TSLA = YahooFinance.get("TSLA");
        BigDecimal price = TSLA.getQuote().getPrice();
        System.out.println(TSLA.getSymbol() + ": " + price);
        System.out.println("\n\nay yuh");
        //System.out.println(tesla.getHistory());
        
	 */

     
	public static double calculateSMA(double[] prices, int period) {
	    double sum = 0;
	    for (int i = 0; i < period; i++) {
	        sum += prices[i];
	    }
	    return sum / period;
	}

	public static double calculateEMA(double[] prices, int period) {
	    double weight = 2.0 / (period + 1);
	    double ema = calculateSMA(prices, period);
	    for (int i = period; i < prices.length; i++) {
	        ema = (prices[i] - ema) * weight + ema;
	    }
	    return ema;
	}
        
      
	public static void main(String[] args) throws IOException {
  

		//from.set(2023, 1, 22, 0, 0, 0); year, month 0-11, day, timestamp
		//from.add(Calendar.YEAR, -5); // from 5 years ago
		Scanner scanner = new Scanner(System.in);
	    System.out.print("\nSpanning Back how many days of of closing marketprice would you like: ");
	    int dayX = scanner.nextInt();
	    scanner.close(); // close the scanner
	    System.out.println("You entered: " + dayX);
		
		
		
		Calendar from = Calendar.getInstance();
		from.add(Calendar.DAY_OF_MONTH, -dayX); // set the 'from' calendar x(neg) days ago/// needs to be larger than desired days to skip over closed days //mf should tick or self correct
        Calendar to = Calendar.getInstance();
        Stock google = YahooFinance.get("GOOG", from, to, Interval.DAILY);
        
        
        int marketDays = google.getHistory().size();
        
        System.out.println("\nOpen Market Days: " + marketDays);
      
        List<HistoricalQuote> history = google.getHistory();
        BigDecimal[] closingPrices = new BigDecimal[marketDays];
        
        
        //int i = marketDays -1;//for most recent first
        int i = 0; //recent last
        for (HistoricalQuote quote : history) {
           if (i >= marketDays) { // if we've already added 15 quotes, exit the loop //great place to flip array // (i >= 15) i = 0
              break;
              //(i < 0)// recent first
              //(i >= 15)//recent last
              
           }
           
           
           BigDecimal closePrice = quote.getAdjClose(); // get the closing price of the quote


           closingPrices[i] = closePrice; // add the closing price to the array
           Calendar date = quote.getDate(); // get the date of the historical quote
           int year = date.get(Calendar.YEAR); // extract the year from the date
           int month = date.get(Calendar.MONTH) + 1; // extract the month from the date (note that Calendar.MONTH is zero-based, so we add 1)
           int dayOfMonth = date.get(Calendar.DAY_OF_MONTH); // extract the day of the month from the date
           System.out.println("Historical quote date: " + year + "-" + month + "-" + dayOfMonth + "\tPrice "+ closePrice);
           //i--; //recent first
           i++; //recent last
          
        }
        
        
        // Print out the closing prices array using single variable "price"
        for (BigDecimal price : closingPrices) {
        	System.out.println(price);	
        }
        
    
       //double pToday = closingPrices[0].doubleValue();
       //System.out.println(pToday);
       
       
       double[] priceD = new double[closingPrices.length];
       for (int x = 0; x < closingPrices.length; x++) {
           priceD[x] = closingPrices[x].doubleValue();
       }

       System.out.println(Arrays.toString(priceD));
       
       
     int numPeriods = marketDays;
     
     double p = calculateEMA(priceD, numPeriods);
     
     System.out.println(p);
     
     
       
       
 
    }

}
