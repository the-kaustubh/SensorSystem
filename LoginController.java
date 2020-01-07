
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

public class LoginController implements DAO{
    @FXML
    private TextField login_mail;

    @FXML
    private PasswordField login_pwd;

    @FXML
    private Button loginBtn;

    private static HttpURLConnection con;

    @FXML
    protected int handleLoginAction(ActionEvent event) {
      Stage stage = (Stage) loginBtn.getScene().getWindow();
      String url = "http://" + HOST + "/SensorsIOT/login.php";

      String urlParameters  = "mail="+login_mail.getText();
             urlParameters += "&pwd="+login_pwd.getText();

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

          StringBuilder content;

          try (BufferedReader br = new BufferedReader(
            new InputStreamReader(con.getInputStream()))) {
              String line = br.readLine();
              if(!line.equals("")) User.setUser(line);
          }
      } catch (Exception e) {
      } finally {
          con.disconnect();
      }
      stage.close();
      return 1;
    }
}
