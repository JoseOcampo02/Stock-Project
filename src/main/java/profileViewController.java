import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class profileViewController implements Initializable {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField sharesTextField;
    
    @FXML
    private Text numberText;

    private String[] userData;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userData = new String[2];
    }

    public void returnToHomeView(ActionEvent event) throws IOException {
        Parent profileParent = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
    }

    public void saveUserInfo(ActionEvent event) {
    	int totalInv;
        // Another way to store data? database
        userData[0] = nameTextField.getText();
        userData[1] = sharesTextField.getText();
        System.out.println("User: " + userData[0] + "\nShares: " + userData[1]);
        
        //totalInv = userData[1] * 
        
        
        
        // Set the number text to the value entered in the sharesTextField
        numberText.setText(sharesTextField.getText());
    }

}
