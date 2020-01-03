import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ConfigureControl implements DAO  {
    @FXML
    private Button trendBtn;
    @FXML
    private TextField add_uid;
    @FXML
    private TextField add_loc;
    @FXML
    private TextField add_co2min;
    @FXML
    private TextField add_co2max;
    @FXML
    private TextField add_tempmin;
    @FXML
    private TextField add_tempmax;
    @FXML
    private TextField uid;


    @FXML
    private Button addDeviceBtn;
    private static HttpURLConnection con;

    @FXML
    protected void addDeviceHandle(ActionEvent event) {

        String url = "http://192.168.43.213/SensorsIOT/writeSensor.php";
        String urlParameters = "uid="+add_uid.getText();
        urlParameters += "&loc="+add_loc.getText();
        urlParameters += "&cmin="+add_co2min.getText();
        urlParameters += "&cmax="+add_co2max.getText();
        urlParameters += "&tmin="+add_tempmin.getText();
        urlParameters += "&tmax="+add_tempmax.getText();
        urlParameters += "&usr="+GUI_USER;
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

                String line;
                content = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());

        } catch (Exception e) {

        } finally {

            con.disconnect();
        }
    }

    @FXML
    protected void handleTrend(ActionEvent event) {
      Graph.displayGraph();
    }
}
