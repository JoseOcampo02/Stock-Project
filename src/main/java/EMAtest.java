import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;


public class EMAtest {
  
	
    @Test
    public void testCalculate() { //test the calculator or ema 
    	List<Double> testPrices = Arrays.asList(
    			
        	    22.27, 22.19, 22.08, 22.17, 22.18, 22.13, 22.23, 22.43, 22.24, 22.29,
        	    22.15, 22.39, 22.38, 22.61, 23.36, 24.05, 23.75, 23.83, 23.95, 23.63,
        	    23.82, 23.87, 23.65, 23.19, 23.10, 23.33, 22.68, 23.10, 22.40, 22.17
        	);
        EMA ema = new EMA(5, testPrices);
        List<Double> emaValues = ema.calculate(testPrices);

        // Expected EMA values for the test prices with a 5-day period
        List<Double> expectedEmaValues = Arrays.asList(
        		
        		
        		22.177999999999997, 22.162, 22.184666666666665, 22.266444444444442,22.257629629629626,22.268419753086416,22.22894650205761,22.28263100137174,22.315087334247828,22.41339155616522,
        		22.728927704110145,23.16928513607343,23.362856757382286,23.51857117158819,23.662380781058793,23.651587187372527,23.707724791581686,23.761816527721123,23.72454435181408,
        		23.54636290120939,23.397575267472927,23.375050178315284,23.143366785543524,23.12891119036235,22.8859407935749,22.64729386238327
        		);

        assertEquals(expectedEmaValues.size(), emaValues.size());

        double delta = 0.00001;
        for (int i = 0; i < emaValues.size(); i++) {
            assertEquals(expectedEmaValues.get(i), emaValues.get(i), delta, "EMA values should be equal within a tolerance of 0.001");
        }
    }
    
}
