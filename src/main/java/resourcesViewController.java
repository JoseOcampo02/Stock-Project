

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class resourcesViewController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void SAlinkOneClick(ActionEvent event) throws IOException, URISyntaxException {
        
        Desktop.getDesktop().browse(new URI("https://www.fool.com/investing/how-to-invest/stocks/how-to-research-stocks/"));
        
    }

    public void SAlinkTwoClick(ActionEvent event) throws IOException, URISyntaxException {
        
        Desktop.getDesktop().browse(new URI("https://www.investopedia.com/terms/t/technicalanalysis.asp"));
        
    }
    
    public void SAlinkThreeClick(ActionEvent event) throws IOException, URISyntaxException {
        
        Desktop.getDesktop().browse(new URI("https://www.investopedia.com/top-7-technical-analysis-tools-4773275"));
        
    }
    
    public void SAlinkFourClick(ActionEvent event) throws IOException, URISyntaxException {
        
        Desktop.getDesktop().browse(new URI("https://www.barchart.com/education/technical-indicators"));
        
    }
    
    public void LRLinkeOneClick(ActionEvent event) throws IOException, URISyntaxException {
        
        Desktop.getDesktop().browse(new URI("https://www.investopedia.com/articles/trading/09/linear-regression-time-price.asp#:~:text=Linear%20regression%20is%20the%20analysis,stock%20is%20overbought%20or%20oversold."));
        
    }

    public void LRLinkTwoClick(ActionEvent event) throws IOException, URISyntaxException {
        
        Desktop.getDesktop().browse(new URI("https://www.investopedia.com/terms/l/least-squares-method.asp#:~:text=The%20least%20squares%20method%20is%20a%20statistical%20procedure%20to%20find,the%20behavior%20of%20dependent%20variables."));
        
    }
    
    public void MACDLinkOneClick(ActionEvent event) throws IOException, URISyntaxException {
        
        Desktop.getDesktop().browse(new URI("https://www.investopedia.com/terms/m/macd.asp"));
        
    }
    
    public void MACDLinkTwoClick(ActionEvent event) throws IOException, URISyntaxException {
        
        Desktop.getDesktop().browse(new URI("https://www.investopedia.com/articles/forex/05/macddiverge.asp#:~:text=Moving%20average%20convergence%20divergence%20is,and%20exit%20points%20for%20trades."));
        
    }
    
    public void returnToHomeView(ActionEvent event) throws IOException {
        
        Parent profileParent = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
        
    }

}
