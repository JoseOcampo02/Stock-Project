import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
    @FXML
    private TextArea consoleOutputTextArea;
    
    @FXML
    private Button saveButton;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userData = new String[2];
        
        redirectConsoleOutputToTextArea();
        
    }
    
    private void redirectConsoleOutputToTextArea() {
        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                consoleOutputTextArea.appendText(String.valueOf((char) b));
            }
        };

        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        System.setErr(printStream);
    }

    public void returnToHomeView(ActionEvent event) throws IOException {
        Parent profileParent = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        Scene profileViewScene = new Scene(profileParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
    }
    
    double product;
    
    // temporary price
    double closingPrice = 169.15;
    
    private double calculateProduct(int shares, double closingPrice) {
        product = shares * closingPrice;
    	return product;
    }

    public void saveUserInfo(ActionEvent event) {
        userData[0] = nameTextField.getText();
        userData[1] = sharesTextField.getText();
        
        int shares = Integer.parseInt(userData[1]);
        double product = calculateProduct(shares, closingPrice);
        
        System.out.println("Hello,  " + userData[0]);
        System.out.println("You currently have " + userData[1] + " shares of");
        System.out.println("TSLA worth $" + product);

        // Set the number text to the value entered in the sharesTextField
        numberText.setText(sharesTextField.getText());
    }


    
}
