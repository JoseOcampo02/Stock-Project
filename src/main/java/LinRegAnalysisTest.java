import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class LinRegAnalysisTest {

    private BigDecimal[] myData = new BigDecimal[5];
    // BigDecimal[index] = B    ->     (index,B)
    
    
    //  ========================= Tests for getSlopeAndYInt method ===================================
    
    
    @Test
    void testEmptyDataSet() {
        
        BigDecimal[] myData = new BigDecimal[0];
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        assertFalse(myAnalysis.getSuccess());
        
    }
    
    //  --------------------------- Cases where points land on a predetermined line ---------------------------
    
    @Test
    void testYEqualsZero() {
        
        // Points are derived from the equation y(x)=0, so should have slope=0, yInt=0
        myData[0] = new BigDecimal("0");
        myData[1] = new BigDecimal("0");
        myData[2] = new BigDecimal("0");
        myData[3] = new BigDecimal("0");
        myData[4] = new BigDecimal("0");
        
        BigDecimal expectedSlope = new BigDecimal("0.00");
        BigDecimal expectedYInt = new BigDecimal("0.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testYEqualsPositive() {
        
        // Points are derived from the equation y(x)=5, so should have slope=0, yInt=5
        myData[0] = new BigDecimal("5");
        myData[1] = new BigDecimal("5");
        myData[2] = new BigDecimal("5");
        myData[3] = new BigDecimal("5");
        myData[4] = new BigDecimal("5");
        
        BigDecimal expectedSlope = new BigDecimal("0.00");
        BigDecimal expectedYInt = new BigDecimal("5.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testYEqualsNegative() {
        
        // Points are derived from the equation y(x)=-5, so should have slope=0, yInt=-5
        myData[0] = new BigDecimal("-5");
        myData[1] = new BigDecimal("-5");
        myData[2] = new BigDecimal("-5");
        myData[3] = new BigDecimal("-5");
        myData[4] = new BigDecimal("-5");
        
        BigDecimal expectedSlope = new BigDecimal("0.00");
        BigDecimal expectedYInt = new BigDecimal("-5.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testYEqualsX() {
        
     // Points are derived from the equation y(x)=x, so should have slope=1, yInt=0
        myData[0] = new BigDecimal("0");
        myData[1] = new BigDecimal("1");
        myData[2] = new BigDecimal("2");
        myData[3] = new BigDecimal("3");
        myData[4] = new BigDecimal("4");
        
        BigDecimal expectedSlope = new BigDecimal("1.00");
        BigDecimal expectedYInt = new BigDecimal("0.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
        
    }
    
    @Test
    void testYEqualsNegativeX() {
        
     // Points are derived from the equation y(x)= -x, so should have slope= -1, yInt=0
        myData[0] = new BigDecimal("0");
        myData[1] = new BigDecimal("-1");
        myData[2] = new BigDecimal("-2");
        myData[3] = new BigDecimal("-3");
        myData[4] = new BigDecimal("-4");
        
        BigDecimal expectedSlope = new BigDecimal("-1.00");
        BigDecimal expectedYInt = new BigDecimal("0.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
        
    }
    
    @Test
    void testPositiveSlopeZeroYInt() {
        
        // Points are derived from the equation y(x)=7x, so should have slope=7, yInt=0
        myData[0] = new BigDecimal("0");
        myData[1] = new BigDecimal("7");
        myData[2] = new BigDecimal("14");
        myData[3] = new BigDecimal("21");
        myData[4] = new BigDecimal("28");
        
        BigDecimal expectedSlope = new BigDecimal("7.00");
        BigDecimal expectedYInt = new BigDecimal("0.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testNegativeSlopeZeroYInt() {
        
        // Points are derived from the equation y(x)= -6x, so should have slope=-6, yInt= 0
        myData[0] = new BigDecimal("0");
        myData[1] = new BigDecimal("-6");
        myData[2] = new BigDecimal("-12");
        myData[3] = new BigDecimal("-18");
        myData[4] = new BigDecimal("-24");
        
        BigDecimal expectedSlope = new BigDecimal("-6.00");
        BigDecimal expectedYInt = new BigDecimal("0.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testPositiveSlopePositiveYInt() {
        
        // Points are derived from the equation y(x)= 4x + 5, so should have slope=4, yInt=5
        myData[0] = new BigDecimal("5");
        myData[1] = new BigDecimal("9");
        myData[2] = new BigDecimal("13");
        myData[3] = new BigDecimal("17");
        myData[4] = new BigDecimal("21");
        
        BigDecimal expectedSlope = new BigDecimal("4.00");
        BigDecimal expectedYInt = new BigDecimal("5.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testPositiveSlopeNegativeYInt() {
        
        // Points are derived from the equation y(x)= 4x - 5, so should have slope=4, yInt= -5
        myData[0] = new BigDecimal("-5");
        myData[1] = new BigDecimal("-1");
        myData[2] = new BigDecimal("3");
        myData[3] = new BigDecimal("7");
        myData[4] = new BigDecimal("11");
        
        BigDecimal expectedSlope = new BigDecimal("4.00");
        BigDecimal expectedYInt = new BigDecimal("-5.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testNegativeSlopePositiveYInt() {
        
        // Points are derived from the equation y(x)= -4x + 5, so should have slope= -4, yInt= 5
        myData[0] = new BigDecimal("5");
        myData[1] = new BigDecimal("1");
        myData[2] = new BigDecimal("-3");
        myData[3] = new BigDecimal("-7");
        myData[4] = new BigDecimal("-11");
        
        BigDecimal expectedSlope = new BigDecimal("-4.00");
        BigDecimal expectedYInt = new BigDecimal("5.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testNegativeSlopeNegativeYInt() {
        
        // Points are derived from the equation y(x)= -4x - 5, so should have slope= -4, yInt= -5
        myData[0] = new BigDecimal("-5");
        myData[1] = new BigDecimal("-9");
        myData[2] = new BigDecimal("-13");
        myData[3] = new BigDecimal("-17");
        myData[4] = new BigDecimal("-21");
        
        BigDecimal expectedSlope = new BigDecimal("-4.00");
        BigDecimal expectedYInt = new BigDecimal("-5.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    // ------------------------ Cases where points do not land on a straight line ------------------------------
    
    @Test
    void testRounding() {
        
        // According to Desmos, should have slope=1.962, yInt=16.398
        // This should round to slope=1.96, yInt=16.40
        
        myData[0] = new BigDecimal("19.2");  // (0,14.5)
        myData[1] = new BigDecimal("14.9");  // (1,19.2)
        myData[2] = new BigDecimal("20.2");  // (2,20.4)
        myData[3] = new BigDecimal("21.7");  // (1,19.2)
        myData[4] = new BigDecimal("25.61");  // (2,20.4)
        
        BigDecimal expectedSlope = new BigDecimal("1.96");
        BigDecimal expectedYInt = new BigDecimal("16.40");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
        
    }
    
    @Test
    void testRepeatingDecimalValues() {
        
        // According to Desmos, should have slope=2.95, yInt=15.0833...
        // yInt is rounded to yInt=15.08
        
        BigDecimal[] myData = new BigDecimal[3];
        
        myData[0] = new BigDecimal("14.5");  // (0,14.5)
        myData[1] = new BigDecimal("19.2");  // (1,19.2)
        myData[2] = new BigDecimal("20.4");  // (2,20.4)
        
        BigDecimal expectedSlope = new BigDecimal("2.95");
        BigDecimal expectedYInt = new BigDecimal("15.08");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testScatter1() {
        
        // According to Desmos, should have slope=1.5, yInt=4.4
        
        myData[0] = new BigDecimal("3");   // (0,3)
        myData[1] = new BigDecimal("7");   // (1,7)
        myData[2] = new BigDecimal("8");   // (2,8)
        myData[3] = new BigDecimal("10");  // (3,10)
        myData[4] = new BigDecimal("9");   // (4,9)
        
        BigDecimal expectedSlope = new BigDecimal("1.50");
        BigDecimal expectedYInt = new BigDecimal("4.40");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testScatter2() {
        
        // According to Desmos, should have slope= -0.326, yInt=14.976
        // Rounded to slope= -0.33, yInt=14.98
        myData[0] = new BigDecimal("16.42");   // (0,16.42)
        myData[1] = new BigDecimal("10.97");   // (1,10.97)
        myData[2] = new BigDecimal("15.80");   // (2,15.80)
        myData[3] = new BigDecimal("16.31");   // (3,16.31)
        myData[4] = new BigDecimal("12.12");   // (4,12.12)
        
        BigDecimal expectedSlope = new BigDecimal("-0.33");
        BigDecimal expectedYInt = new BigDecimal("14.98");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    @Test
    void testScatter3() {
        
        // According to Desmos, should have slope=1.004, yInt=127.275
        // Rounded to slope= 1.00, yInt=127.27
        myData[0] = new BigDecimal("124.57");  
        myData[1] = new BigDecimal("131.48");  
        myData[2] = new BigDecimal("130.91");  
        myData[3] = new BigDecimal("128.24");  
        myData[4] = new BigDecimal("131.21");   
        
        BigDecimal expectedSlope = new BigDecimal("1.00");
        BigDecimal expectedYInt = new BigDecimal("127.28");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedSlope.equals(myAnalysis.getSlope()));
        assertTrue(expectedYInt.equals(myAnalysis.getYInt()));
                
    }
    
    //  ================================= Tests for getFuturePredictions ========================================
    
    @Test
    void testPredictionsForYEqualsZero() {
        
     // Points are derived from the equation y(x)=0, so should have slope=0, yInt=0
        myData[0] = new BigDecimal("0");
        myData[1] = new BigDecimal("0");
        myData[2] = new BigDecimal("0");
        myData[3] = new BigDecimal("0");
        myData[4] = new BigDecimal("0");
        
        BigDecimal expectedOneDay = new BigDecimal("0.00");
        BigDecimal expectedTwoDay = new BigDecimal("0.00");
        BigDecimal expectedThreeDay = new BigDecimal("0.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedOneDay.equals(myAnalysis.getOneDay()));
        assertTrue(expectedTwoDay.equals(myAnalysis.getTwoDay()));
        assertTrue(expectedThreeDay.equals(myAnalysis.getThreeDay()));
        
    }
    
    @Test
    void testPredictionsForYEqualsX() {
        
     // Points are derived from the equation y(x)=x, so should have slope=1, yInt=0
        myData[0] = new BigDecimal("0");
        myData[1] = new BigDecimal("1");
        myData[2] = new BigDecimal("2");
        myData[3] = new BigDecimal("3");
        myData[4] = new BigDecimal("4");
        
        BigDecimal expectedOneDay = new BigDecimal("5.00");
        BigDecimal expectedTwoDay = new BigDecimal("6.00");
        BigDecimal expectedThreeDay = new BigDecimal("7.00");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedOneDay.equals(myAnalysis.getOneDay()));
        assertTrue(expectedTwoDay.equals(myAnalysis.getTwoDay()));
        assertTrue(expectedThreeDay.equals(myAnalysis.getThreeDay()));
        
    }
    
    @Test
    void testPredictionsForScatter1() {
        
        // slope=1.5, yInt=4.4
        myData[0] = new BigDecimal("3");   // (0,3)
        myData[1] = new BigDecimal("7");   // (1,7)
        myData[2] = new BigDecimal("8");   // (2,8)
        myData[3] = new BigDecimal("10");  // (3,10)
        myData[4] = new BigDecimal("9");   // (4,9)
        
        BigDecimal expectedOneDay = new BigDecimal("11.90");
        BigDecimal expectedTwoDay = new BigDecimal("13.40");
        BigDecimal expectedThreeDay = new BigDecimal("14.90");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedOneDay.equals(myAnalysis.getOneDay()));
        assertTrue(expectedTwoDay.equals(myAnalysis.getTwoDay()));
        assertTrue(expectedThreeDay.equals(myAnalysis.getThreeDay()));
        
    }
    
    @Test
    void testPredictionsForScatter2() {
        
        // Rounded to slope= -0.33, yInt=14.98
        myData[0] = new BigDecimal("16.42");   // (0,16.42)
        myData[1] = new BigDecimal("10.97");   // (1,10.97)
        myData[2] = new BigDecimal("15.80");   // (2,15.80)
        myData[3] = new BigDecimal("16.31");   // (3,16.31)
        myData[4] = new BigDecimal("12.12");   // (4,12.12)
        
        BigDecimal expectedOneDay = new BigDecimal("13.33");
        BigDecimal expectedTwoDay = new BigDecimal("13.00");
        BigDecimal expectedThreeDay = new BigDecimal("12.67");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedOneDay.equals(myAnalysis.getOneDay()));
        assertTrue(expectedTwoDay.equals(myAnalysis.getTwoDay()));
        assertTrue(expectedThreeDay.equals(myAnalysis.getThreeDay()));
        
    }
    
    @Test
    void testPredictionsForScatter3() {
        
        // Rounded to slope= 1.00, yInt=127.28
        myData[0] = new BigDecimal("124.57");  
        myData[1] = new BigDecimal("131.48");  
        myData[2] = new BigDecimal("130.91");  
        myData[3] = new BigDecimal("128.24");  
        myData[4] = new BigDecimal("131.21");  
        
        BigDecimal expectedOneDay = new BigDecimal("132.28");
        BigDecimal expectedTwoDay = new BigDecimal("133.28");
        BigDecimal expectedThreeDay = new BigDecimal("134.28");
        
        LinRegAnalysis myAnalysis = new LinRegAnalysis(myData);
        
        assertTrue(expectedOneDay.equals(myAnalysis.getOneDay()));
        assertTrue(expectedTwoDay.equals(myAnalysis.getTwoDay()));
        assertTrue(expectedThreeDay.equals(myAnalysis.getThreeDay()));
        
    }
    
}
