
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;



public class SampleController implements Initializable {	
	
	
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private LineChart<Number, Number> priceChart;
  
    
    @FXML
    private Label TSLAPrice;
    
    
    

    ImageView wsjLogo;
    @FXML
    ImageView cnbcLogo;
    @FXML
    ImageView marketWatchLogo;
    @FXML
    ImageView investopediaLogo;
    @FXML
    ImageView yahooFinLogo;
    @FXML
    ImageView bloomLogo;

  
    

	@Override

	public void initialize(URL arg0, ResourceBundle arg1) {

	
	    
	}
	
	

	public void changeToProfileView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("profileView.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
	
	public void changeToInvestmentsView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("investmentsView.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
	
	public void changeToAnalysisView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("AnalysisView.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
	
	public void changeToChartsView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("chartsView.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
	
	public void changeToResourcesView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("resourcesView.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }
	
	public void wsjClick(ActionEvent event) throws IOException, URISyntaxException {
	    
	    Desktop.getDesktop().browse(new URI("https://www.wsj.com/"));
	    
	}
	
	public void cnbcClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://www.cnbc.com/"));
        
    }
	
	public void marketWatchClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://www.marketwatch.com/"));
        
    }
	
	public void investopediaClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://www.investopedia.com/"));
        
    }
	
	public void yahooFinClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://finance.yahoo.com/"));
        
    }
	
	public void bloomClick(ActionEvent event) throws IOException, URISyntaxException {
        
	    Desktop.getDesktop().browse(new URI("https://www.bloomberg.com/"));
        
    }
}
