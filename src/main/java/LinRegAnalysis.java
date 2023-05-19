import java.io.IOException;
import java.math.*;

/**
 * 
 * @author Jose E Ocampo
 * 03/27/2023
 * Performs linear regression analysis on the given stock
 * by constructing a line of best fit (LoBF) and checking
 * whether the slope is positive or negative
 *
 */

public class LinRegAnalysis {
    
    /**
     * Represents the computed slope of LoBF
     */
    private BigDecimal slope;
    
    /**
     * Represents the computed y-intecept of LoBF
     * (Important for calculating future stock price)
     */
    private BigDecimal yInt;
    
    private boolean successfullyComputed;
    
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
    
    private BigDecimal[] myData;
    
    /**
     * 
     * Constructs LinRegAnalysis object that automatically performs
     * the necessary computations and analysis upon creation for easy access
     * @param myStock Stock to perform linear regression analysis on
     * @throws IOException Throws IOException
     * 
     */
    public LinRegAnalysis(BigDecimal[] myData) {   // Creating a LinRegAnalysis object will automatically
                                                                // perform the necessary calculations for a 1, 2, or 3
                                                                // day prediction into the future for a given stock
                                                                // These are saved into private variables with getters
        
        //this.myData = getStockData(myStock);             // Requests and cleans up data for calculations
        this.myData = myData;
        successfullyComputed = false;
        getSlopeAndYInt(myData);
        if(successfullyComputed) {
            getFuturePredictions();
        }
        
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
    
    public BigDecimal[] getMyData() {
        
        return this.myData;
        
    }
    
    public BigDecimal getSlope() {
        return this.slope;
    }
    public BigDecimal getYInt() {
        return this.yInt;
    }
    
    public boolean getSuccess() {
        return this.successfullyComputed;
    }
    
    /**
     * 
     * Computes the slope and y-intercept of LoBF using the method of least squares
     * @param myData Array that is formatted to represent xy-coordinates in a plane (x = days, y = price)
     * 
     */
    
    private void getSlopeAndYInt (BigDecimal[] myData) {
        
        if (myData.length == 0) {
            
            System.out.println("Invalid array size, no data to work with");
            return;
            
        }
        
        BigDecimal sumX = new BigDecimal("0");
        BigDecimal sumY = new BigDecimal("0");
        BigDecimal sumXSquared = new BigDecimal("0");
        BigDecimal sumXY = new BigDecimal("0");
        BigDecimal N = new BigDecimal(Integer.toString(myData.length));
        
        for (int i = 0; i < myData.length; i++) {
            
            BigDecimal indexAsBD = new BigDecimal(Integer.toString(i));
            
            sumX = sumX.add(indexAsBD);
            sumY = sumY.add(myData[i]);
            sumXSquared = sumXSquared.add(indexAsBD.multiply(indexAsBD));
            sumXY = sumXY.add(indexAsBD.multiply(myData[i]));         
            
        }
        
        this.slope = ((N.multiply(sumXY)).subtract((sumX.multiply(sumY)))).
                divide((N.multiply(sumXSquared)).subtract(sumX.multiply(sumX)), 2, RoundingMode.HALF_UP);
        this.yInt = (sumY.subtract(slope.multiply(sumX))).divide(N, 2, RoundingMode.HALF_UP); 
        this.successfullyComputed = true;
    }
    
    private void getFuturePredictions() {
        
        // Let i = myData.length. Recall that myData[i-1] gives the most recent price close.
        // Then indices i, i+1, i+2 would correspond to 3 days into the future, and can be
        // fed into our computed function y(x) = (slope)x + yInt to get predictions
        
        BigDecimal i = new BigDecimal(Integer.toString(myData.length));
        this.OneDay = (this.slope.multiply(i)).add(this.yInt).
                        setScale(2, RoundingMode.HALF_UP);
        this.TwoDay = (this.slope.multiply(i.add(new BigDecimal("1")))).add(this.yInt).
                        setScale(2, RoundingMode.HALF_UP);;
        this.ThreeDay = (this.slope.multiply(i.add(new BigDecimal("2")))).add(this.yInt).
                        setScale(2, RoundingMode.HALF_UP);;
    }
    
}
