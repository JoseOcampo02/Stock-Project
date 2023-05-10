import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MACDtest {


	public static final List<Double> testPrices = Arrays.asList(
	        //
			148.875351, 144.339813, 155.245056, 152.852661, 150.171204, 144.569077, 138.438629, 138.169037, 138.708206, 139.287338, 134.664383, 146.646088, 149.471771, 148.05394, 149.811249, 148.563156, 150.490234, 
			 151.059357, 147.784348, 149.951035, 150.839706, 147.884201, 144.000137, 140.954788, 147.804321, 148.083893, 147.584656, 146.406464, 142.692139, 140.725143, 142.432526, 141.943283, 144.26973, 145.24823, 
			 142.991684, 136.291901, 134.304932, 132.168198, 132.098312, 135.2435, 132.028412, 131.658981, 129.831772, 125.847855, 129.412415, 129.731918, 124.879326, 126.167366, 124.829399, 129.422394, 129.951584, 
			 130.530701, 133.286499, 133.206619, 134.55455, 135.732758, 135.003876, 135.063782, 137.659805, 140.894882, 142.312714, 141.643738, 143.74054, 145.70752, 142.781998, 144.070023, 145.208282, 150.590088, 
			 154.264465, 151.498688, 154.41423, 151.6884, 150.639999, 151.009995, 153.850006, 153.199997, 155.330002, 153.710007, 152.550003, 148.479996, 148.910004, 149.399994, 146.710007, 147.919998, 147.410004, 
			 145.309998, 145.910004, 151.029999, 153.830002, 151.600006, 152.869995, 150.589996, 148.5, 150.470001, 152.589996, 152.990005, 155.850006, 155.0, 157.399994, 159.279999, 157.830002, 158.929993, 160.25, 
			 158.279999, 157.649994, 160.770004, 162.360001, 164.899994, 166.169998, 165.630005, 163.759995, 164.660004, 162.029999, 160.800003, 160.100006, 165.559998, 165.210007, 165.229996, 166.470001, 167.630005, 
			 166.649994, 165.020004, 165.330002, 163.770004, 163.759995, 168.410004, 169.679993, 169.589996, 168.539993, 167.449997
	    );
	 
	 @Test
	 public void testCalculateMACD() {
		//also tests find instances which depend on macd instances

	     List<Double> expectedMACDLine = Arrays.asList(
	    		 0.6491112192568096, 0.6427717500658616, 0.5364930690750498, 0.1508129848905071, -0.30998804955481774, -0.5312803808204762, -0.7376310177856453, -0.7053101775984203, 
	    		 -0.5938928791314879, -0.679841481425143, -1.2738882726076213, -1.8832969520513814, -2.509743536207594, -2.9775231501623693, -3.059187073914245, -3.344780506210867, 
	    		 -3.5598891667713417, -3.8336133177315617, -4.322187005033953, -4.37136460044141, -4.33459051592655, -4.6434832244584925, -4.729826363912764, -4.850305377856557, 
	    		 -4.52303086880562, -4.172860118195274, -3.8047584860282484, -3.253164962409869, -2.79030388308027, -2.288337842202907, -1.774993578356515, -1.4107173650482991, 
	    		 -1.1044603960455674, -0.6448388802052989, -0.019319141239776627, 0.5840839948869245, 0.9968139050159834, 1.4760843485072428, 1.9916697461313504, 2.1395463540006006, 
	    		 2.3337702336246196, 2.5501455554540087, 3.119926749987542, 3.8238944921435802, 4.111227241785542, 4.52207274943342, 4.574981534634077, 4.4806646166615565, 4.385223240852184, 
	    		 4.487026764182701, 4.46380058144527, 4.564648836207169, 4.462411841092319, 4.238922003425785, 3.690843401226374, 3.253678827979229, 2.913179531811494, 2.398622094280512, 
	    		 2.0646675766219857, 1.7388100419707655, 1.2961712095007556, 0.9824669480555883, 1.133923701076526, 1.463026254570707, 1.5263060528402548, 1.659800105013403, 1.5635940991089967, 
	    		 1.3036769594504847, 1.2423325342541887, 1.3492294372000515, 1.4495142878020886, 1.7397145862760226, 1.8794469387980541, 2.158958167254923, 2.5033168429148134, 2.628916422501163, 
	    		 2.785110059812382, 2.981044549901668, 2.943431383448541, 2.8301621916866964, 2.95805564894809, 3.1513844072132997, 3.4695601191622245, 3.7806147981061144, 3.9381583123999917, 
	    		 3.8675359567909027, 3.8399261573046886, 3.564733737111169, 3.2103840547702305, 2.8403338699758365, 2.9535949517468794, 2.9807535548239628, 2.969657525270975, 3.0260395816009975, 
	    		 3.128264707425359, 3.0945283620464465, 2.902803779632251, 2.7442410124458263, 2.4642932374428312, 2.216079382526601, 2.3672962610677075, 2.560102952106263, 2.6748081151466465, 
	    		 2.6504337657891597, 2.5141814320670335);

	     List<Double> expectedSignalLine = Arrays.asList(
	    		 -0.09987927573362437, -0.21587171687192808, -0.4274750280190668, -0.7186394128255297, -1.0768602375019425, -1.4569928200340279, -1.7774316708100713, -2.0909014378902304, 
	    		 -2.3846989836664525, -2.6744818504794745, -3.0040228813903704, -3.2774912252005786, -3.488911083345773, -3.7198255115683168, -3.9218256820372064, -4.107521621201077, 
	    		 -4.190623470721985, -4.1870708002166435, -4.110608337378965, -3.9391196623851457, -3.7093565065241707, -3.425152773659918, -3.0951209345992376, -2.75824022068905, 
	    		 -2.4274842557603535, -2.0709551806493427, -1.6606279727674296, -1.2116855792365588, -0.7699856823860504, -0.32077167620739166, 0.14171660826035676, 0.5412825574084055, 
	    		 0.8997800926516484, 1.2298531852121204, 1.6078678981672048, 2.0510732169624797, 2.463104021927092, 2.8748977674283576, 3.2149145208695016, 3.4680645400279126, 3.651496280192767, 
	    		 3.818602376990754, 3.947642017881657, 4.07104338154676, 4.1493170734558715, 4.167238059449854, 4.071959127805158, 3.9083030678399724, 3.7092783606342765, 3.4471471073635236, 
	    		 3.170651201215216, 2.8842829693663257, 2.566660617393212, 2.249821883525687, 2.0266422470358547, 1.9139190485428252, 1.836396449402311, 1.8010771805245294, 1.7535805642414228, 
	    		 1.6635998432832353, 1.579346381477426, 1.5333229926219512, 1.5165612516579787, 1.5611919185815875, 1.6248429226248808, 1.7316659715508893, 1.885996145823674, 2.034580201159172, 
	    		 2.184686172889814, 2.343957848292185, 2.4638525553234563, 2.5371144825961043, 2.621302715866501, 2.7273190541358607, 2.8757672671411334, 3.0567367733341295, 3.233021081147302, 
	    		 3.3599240562760224, 3.455924476481756, 3.4776863286076383, 3.4242258738401565, 3.3074474730672927, 3.23667696880321, 3.185492286007361, 3.1423253338600836, 3.1190681834082663, 
	    		 3.1209074882116847, 3.115631662978637, 3.0730660863093595, 3.007301071536653, 2.8986995047178885, 2.7621754802796312, 2.6831996364372466, 2.65858029957105, 2.661825862686169, 
	    		 2.659547443306767, 2.6304742410588204);

	     List<Double> expectedHistogram = Arrays.asList(
	    		 -0.49401360339786354, -0.4639697645532149, -0.8464132445885545, -1.1646575392258516, -1.4328832987056517, -1.5205303301283415, -1.2817554031041738, -1.2538790683206367, 
	    		 -1.1751901831048892, -1.1591314672520872, -1.3181641236435828, -1.0938733752408316, -0.845679432580777, -0.9236577128901757, -0.8080006818755576, -0.7427837566554807, 
	    		 -0.3324073980836344, 0.014210682021369436, 0.3058498513507164, 0.6859546999752766, 0.9190526234439007, 1.1368149314570113, 1.3201273562427227, 1.3475228556407508, 
	    		 1.323023859714786, 1.4261163004440438, 1.641308831527653, 1.7957695741234834, 1.766799587402034, 1.7968560247146343, 1.8499531378709937, 1.598263796592195, 1.4339901409729712, 
	    		 1.3202923702418883, 1.512058851820337, 1.7728212751811006, 1.6481232198584501, 1.6471749820050623, 1.3600670137645752, 1.0126000766336438, 0.7337269606594172, 0.6684243871919473, 
	    		 0.5161585635636126, 0.4936054546604094, 0.3130947676364473, 0.07168394397593048, -0.3811157265787841, -0.6546242398607434, -0.7960988288227826, -1.0485250130830117, 
	    		 -1.1059836245932302, -1.1454729273955602, -1.2704894078924562, -1.2673549354700988, -0.8927185459593288, -0.45089279397211834, -0.3100903965620563, -0.14127707551112634, 
	    		 -0.18998646513242612, -0.3599228838327506, -0.33701384722323735, -0.18409355542189965, -0.06704696385589015, 0.17852266769443514, 0.2546040161731733, 0.42729219570403365, 
	    		 0.6173206970911393, 0.5943362213419912, 0.600423886922568, 0.637086701609483, 0.47957882812508457, 0.2930477090905921, 0.3367529330815886, 0.424065353077439, 0.5937928520210911, 
	    		 0.7238780247719849, 0.7051372312526896, 0.5076119005148803, 0.38400168082293273, 0.08704740850353065, -0.21384181906992605, -0.4671136030914562, -0.28308201705633085, 
	    		 -0.20473873118339814, -0.17266780858910868, -0.0930286018072688, 0.00735721921367416, -0.021103300932190372, -0.17026230667710873, -0.26306005909082675, -0.43440626727505727, 
	    		 -0.54609609775303, -0.31590337536953905, -0.09847734746478709, 0.012982252460477461, -0.009113677517607499, -0.11629280899178696);
	     
	     List<Integer> expectedBuySignalIndices = Arrays.asList(
	     17, 18, 19, 20, 21, 22, 23, 24, 25, 26);
	     
	     

	     MACD myMACD = new MACD(testPrices, 12, 26, 9);
	     myMACD.calculateMACD();

	     List<Double> macdLine = myMACD.getMACDline();
	     List<Double> signalLine = myMACD.getSignalLine();
	     List<Double> histogram = myMACD.getHistogram();
	     List<Integer> buySignalIndices = myMACD.findBuyInstances();
	     
	     

	     //tests if sizes are as expected 
	     assertEquals(expectedMACDLine.size(), macdLine.size(), "MACD line size should match the expected data size");
	     assertEquals(expectedSignalLine.size(), signalLine.size(), "Signal line size should match the expected data size");
	     assertEquals(expectedHistogram.size(), histogram.size(), "Histogram size should match the expected data size");
	     
	     
	     
	     // checks the expected values of histogram, MACD, and Signal line
	     double delta = 0.00001;
	     for (int i = 0; i < expectedMACDLine.size(); i++) {
	         assertEquals(expectedMACDLine.get(i), macdLine.get(i), delta, "MACD line values should match the precalculated values");
	     }
	     for (int i = 0; i < expectedSignalLine.size(); i++) {
	    	 assertEquals(expectedSignalLine.get(i), signalLine.get(i), delta, "Signal line values should match the precalculated values");
	     }
	     for (int i = 0; i < expectedHistogram.size(); i++) {
	    	 assertEquals(expectedHistogram.get(i), histogram.get(i), delta, "Histogram values should match the precalculated values");
	     }
	 
	     //also tests find instances 
	     for (int i = 0; i < expectedBuySignalIndices.size(); i++) {
	     
	    	 assertEquals(expectedBuySignalIndices.get(i), buySignalIndices.get(i), delta, "Buy singal Indices should match the precalculated values");
	     }

	    
	 }
	 
}
