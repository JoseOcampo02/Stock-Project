import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class Main {

    public static void main(String[] args) throws IOException {
        
        Stock INTC = YahooFinance.get("INTC");
        
        BigDecimal price = INTC.getQuote().getPrice();
        System.out.println(INTC.getSymbol() + ": " + price);
        
    }
    
    // MACD method
    
    // Linear regression method
    
    // etc
    
}
