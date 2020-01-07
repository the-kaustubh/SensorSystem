import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NodeFetcher implements DAO {

    private static HttpURLConnection con;

    // private Sensor createSensor(ResultSet rs) {
    //     Sensor s = new Sensor();
    //     try {
    //         s.setUID(rs.getString("uid"));
    //         s.setLocation(rs.getString("loc"));
    //         s.setSetCO2_min(rs.getDouble("co2min"));
    //         s.setSetCO2_max(rs.getDouble("co2max"));
    //         s.setSetTEMP_min(rs.getDouble("tempmin"));
    //         s.setSetTEMP_max(rs.getDouble("tempmax"));
    //         s.setMachineName(rs.getString("machinename"));
    //         s.setStatus(rs.getBoolean("status"));
    //     } catch (SQLException e ) {}
    //     return s;
    // }

    public ObservableList<Sensor> getSensors(String user) {
    // public void getSensors(String user) {

      String url = "http://192.168.43.213/SensorsIOT/getSensor.php";
      String urlParameters = "user="+User.getUser();
      byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
      ObservableList<Sensor> list = FXCollections.observableArrayList();

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
              int times = Integer.parseInt(br.readLine());
              for(int i = 0;i < times; i++ ) {
                int j = 0;
                String lines[]  = new String[7];
                Sensor s = new Sensor();
                while ((line = br.readLine()) != null) {
                  lines[j] = line;
                  j++;
                  if(j == 7) break;
                }
                list.add(s);
                s.setValues(lines);
                br.readLine();
              }
          }
      } catch (Exception e) {
      } finally {
          con.disconnect();
      }
      return getRTReadings(user, list);
      // return list;
    }

    public ObservableList<Sensor> getRTReadings(String user, ObservableList<Sensor> list) {
      String url = "http://192.168.43.213/SensorsIOT/getRTReadings.php";
      for(Sensor s: list) {
        String urlParameters = "uid="+s.getUID();
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

                s.setTemperature(Double.parseDouble(br.readLine()));
                s.setHumidity(Double.parseDouble(br.readLine()));
                s.setCo2(Double.parseDouble(br.readLine()));

            }
        } catch (Exception e) {}
         finally {
            con.disconnect();
        }
      }
      return list;
    }
}
