import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
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
    private TextField add_uid, del_uid, get_uid, mod_uid;
    @FXML
    private TextField add_loc, mod_loc;
    @FXML
    private TextField add_model, mod_model;
    @FXML
    private TextField add_co2min, mod_co2min;
    @FXML
    private TextField add_co2max, mod_co2max;
    @FXML
    private TextField add_tempmin, mod_tempmin;
    @FXML
    private TextField add_tempmax, mod_tempmax;
    @FXML
    private TextField uid;
    @FXML
    private TextField from, to;


    @FXML
    private Button addDeviceBtn;
    private static HttpURLConnection con;

    @FXML
    protected void addDeviceHandle(ActionEvent event) {

        String url = "http://"+ HOST +"/SensorsIOT/writeSensor.php";
        String urlParameters = "uid="+add_uid.getText();
        urlParameters += "&loc="+add_loc.getText();
        urlParameters += "&cmin="+add_co2min.getText();
        urlParameters += "&cmax="+add_co2max.getText();
        urlParameters += "&tmin="+add_tempmin.getText();
        urlParameters += "&tmax="+add_tempmax.getText();
        urlParameters += "&mac="+add_model.getText();
        urlParameters += "&usr="+User.getUser();
        // System.out.println(User.getUser());
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

            // StringBuilder content;

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

            }
        } catch (Exception e) {
          System.out.println(e);
        } finally {

            con.disconnect();
        }
        //
        add_uid.setText("");
        add_loc.setText("");
        add_co2min.setText("");
        add_co2max.setText("");
        add_tempmin.setText("");
        add_tempmax.setText("");

    }

    @FXML
    protected void delDeviceHandle(ActionEvent event) {

              String url = "http://" + HOST + "/SensorsIOT/deleteSensor.php";
              String urlParameters = "uid="+del_uid.getText();
              urlParameters += "&user="+User.getUser();

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

              } catch (Exception e) {}
               finally {
                  con.disconnect();
              }
              //
              del_uid.setText("");
    }


    @FXML
    protected void handleTrend(ActionEvent event) {
      Graph.displayGraph(get_uid.getText());
    }

    @FXML
    protected void handleSearchSensor(ActionEvent event) {
      String url = "http://" + HOST + "/SensorsIOT/searchSensor.php";
      String urlParameters = "uid="+mod_uid.getText();
      urlParameters += "&user="+User.getUser();

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

              br.readLine(); //UID
              mod_loc.setText(br.readLine());
              mod_co2min.setText(br.readLine());
              mod_co2max.setText(br.readLine());
              mod_tempmin.setText(br.readLine());
              mod_tempmax.setText(br.readLine());
              br.readLine(); // Username
              mod_model.setText(br.readLine());

          }
      } catch (Exception e) {}
       finally { con.disconnect(); }
    }

    @FXML
    protected void handleModifyDevice(ActionEvent event) {
      String url = "http://" + HOST + "/SensorsIOT/modifySensor.php";
      String urlParameters = "uid="+mod_uid.getText();
      urlParameters += "&usr="+User.getUser();
      urlParameters += "&loc="+mod_loc.getText();
      urlParameters += "&cmin="+mod_co2min.getText();
      urlParameters += "&cmax="+mod_co2max.getText();
      urlParameters += "&tmin="+mod_tempmin.getText();
      urlParameters += "&tmax="+mod_tempmax.getText();

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

                    String line;
                    while((line = br.readLine()) != null) {
                      System.out.println(line);
                    }
          }
      } catch (Exception e) {}
       finally {
         con.disconnect();
       }
       mod_uid.setText("");
       mod_loc.setText("");
       mod_co2min.setText("");
       mod_co2max.setText("");
       mod_tempmin.setText("");
       mod_tempmax.setText("");
    }
}
