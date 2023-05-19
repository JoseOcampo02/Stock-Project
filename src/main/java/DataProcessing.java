import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;


/**
 * 
 * @author Jose E Ocampo and Christian Jaime
 * 04/30/2023
 * Utility class used for the collection and formatting of data
 * from YahooFinance API to be more easily usable by controller
 * and analysis methods
 *
 */

public class DataProcessing {
    
    /**
     * Static method to be used by controller and LinRegAnalysis classes
     * in order to generate price chart or Linear Regression chart.
     * Collects one month of market data by default
     * 
     * @author Jose E Ocampo
     * @param myStock Stock to collect data from
     * @throws IOException
     * @return and array of data as BigDecimal for given stock
     */
    public static BigDecimal[] collectData(Stock myStock) throws IOException{
        
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
    
    /**
     * Static method to be used by controller and LinRegAnalysis classes
     * in order to generate price chart or Linear Regression chart.
     * Collects one month of market data by default
     * 
     * @author Christian Jaime
     * @param myStock Stock to collect data from
     * @param daysBack Total market days to collect data from
     * @throws IOException
     * @return a list of data as doubles for for a given stock
     */
    public static List<Double> collectDataMACD(Stock myStock, int daysBack) throws IOException{
        
        Calendar from = Calendar.getInstance();
        from.add(Calendar.DAY_OF_MONTH, -daysBack);
        Calendar to = Calendar.getInstance();
        Stock stock = YahooFinance.get(myStock.getSymbol());
        List<HistoricalQuote> history = stock.getHistory(from, to, Interval.DAILY);
        List<Double> closingPrices = new ArrayList<Double>();
        for (HistoricalQuote quote : history) {
            double closePrice = quote.getAdjClose().doubleValue();
            closingPrices.add(closePrice);
        }
        
        return closingPrices;
    }

}
