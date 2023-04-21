import java.io.IOException;
import java.math.*;
import java.util.Calendar;
import java.util.List;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * 
 * @author Jose E Ocampo
 * Performs linear regression analysis on the given stock
 * by constructing a line of best fit (LoBF) and checking
 * whether the slope is positive or negative
 *
 */

public class LinRegAnalysis {

    /**
     * Represents the computed slope of LoBF
     */
    private double slope;
    
    /**
     * Represents the computed y-intecept of LoBF
     * (Important for calculating future stock price)
     */
    private double yInt;
    
    /**
     * Represents computed prediction one day into the future
     */
    private BigDecimal OneDay;
    
    /**
     * Represents computed prediction two days into the future
     */
    private BigDecimal TwoDay;
    
    /**
     * Represents computed prediction three days into the future
     */
    private BigDecimal ThreeDay;
    
    /**
     * 
     * Constructs LinRegAnalysis object that automatically performs
     * the necessary computations and analysis upon creation for easy access
     * @param myStock Stock to perform linear regression analysis on
     * @throws IOException Throws IOException
     * 
     */
    public LinRegAnalysis(Stock myStock) throws IOException {   // Creating a LinRegAnalysis object will automatically
                                                                // perform the necessary calculations for a 1, 2, or 3
                                                                // day prediction into the future for a given stock
                                                                // These are saved into private variables with getters
        
        BigDecimal[] input = getStockData(myStock);             // Requests and cleans up data for calculations
        getSlopeAndYInt(input);
        CalcPredictions(input.length); 
        
    }
    
    /**
     * 
     * Main for testing
     * @throws IOException
     *
     */
    public static void main(String[] args) throws IOException{
        
        Stock SMP500 = YahooFinance.get("^GSPC");
        LinRegAnalysis myAnalysis = new LinRegAnalysis(SMP500);
        System.out.println("Current price: " + SMP500.getQuote().getPrice());
        System.out.println("One day Prediction: " + myAnalysis.getOneDay());
        System.out.println("Two day Prediction: " + myAnalysis.getTwoDay()); 
        System.out.println("Three day Prediction: " + myAnalysis.getThreeDay());
        
    }
    
    /**
     * 
     * @param myStock Stock to get data on
     * @return Array that is formatted to represent xy-coordinates in a plane (x = days, y = price)
     * @throws IOException
     * 
     */
    private BigDecimal[] getStockData(Stock myStock) throws IOException{
        
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
    
    public BigDecimal getOneDay() {
        
        return this.OneDay;
        
    }
    public BigDecimal getTwoDay() {
        
        return this.TwoDay;
        
    }
    public BigDecimal getThreeDay() {
        
        return this.ThreeDay;
        
    }
    
    /**
     * 
     * Computes the slope and y-intercept of LoBF using the method of least squares
     * @param myData Array that is formatted to represent xy-coordinates in a plane (x = days, y = price)
     * 
     */
    private void getSlopeAndYInt(BigDecimal[] myData) {
        
        double sumX = 0;
        double sumY = 0;
        double sumXSquared = 0;
        double sumXY = 0;
        
        for (int i = 0; i < myData.length; i++) {
            
            sumX += i;
            sumY += myData[i].doubleValue();
            sumXSquared += Math.pow(i, 2);
            sumXY += i * myData[i].doubleValue();
            
        }
        
        this.slope = ( (myData.length * sumXY) - (sumX * sumY) ) 
                         / ( (myData.length * sumXSquared) - Math.pow(sumX, 2) );
        
        this.yInt = (sumY - (slope * sumX))
                         /myData.length;
        
    }
    
    /**
     * Computes stock price one, two, and three days into the future based on LoBF
     * @param i Represents one day into the future ( f(i) = price 1 day into future)
     */
    private void CalcPredictions(int i) {
        
        this.OneDay = BigDecimal.valueOf(((this.slope * i) + this.yInt)).setScale(2, RoundingMode.HALF_UP);
        this.TwoDay = BigDecimal.valueOf(((this.slope * (i + 1)) + this.yInt)).setScale(2, RoundingMode.HALF_UP);
        this.ThreeDay = BigDecimal.valueOf(((this.slope * (i + 2)) + this.yInt)).setScale(2, RoundingMode.HALF_UP);
        
    }
}
