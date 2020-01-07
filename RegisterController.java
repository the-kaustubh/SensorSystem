
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegisterController implements DAO {
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField mobileField;

    @FXML
    private Button submitButton;

    private static HttpURLConnection con;

    @FXML
    protected int handleSubmitButtonAction(ActionEvent event) {
      Stage stage = (Stage) submitButton.getScene().getWindow();
      String url = "http://" + HOST + "/SensorsIOT/register.php";

      String urlParameters  = "name="+nameField.getText();
             urlParameters += "&mail="+emailField.getText();
             urlParameters += "&mobile="+mobileField.getText();
             urlParameters += "&pwd="+passwordField.getText();

      byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

      try {

          URL myurl = new URL(url);
          con = (HttpURLConnection) myurl.openConnection();

          con.setDoOutput(true);
          con.setRequestMethod("POST");
          con.setRequestProperty("User-Agent", "Java client");
          con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

          try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {

              wr.write(postData);
          }

            try (BufferedReader br = new BufferedReader(
            new InputStreamReader(con.getInputStream()))) {
              String line = br.readLine();
              if(Integer.parseInt(line) == 1 ) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("You have been successfully registered!");
                a.show();
              }
          }
      } catch (Exception e) {
      } finally {
          con.disconnect();
      }
      stage.close();
      return 1;
    }
}
