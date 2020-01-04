import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javafx.stage.*;

public class Graph implements DAO{

  private static HttpURLConnection con;

  public static void displayGraph(String uid, String from, String to) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Trend for "+uid);
    window.setMinHeight(100);
    window.setMinHeight(300);

    String url = "http://"+ HOST +"/SensorsIOT/getTrend.php";
    String urlParameters = "uid="+uid;
    urlParameters += "&from="+from+"&to="+to;
    byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Timestamp");
    final LineChart<String, Number> lineChart =
            new LineChart<String, Number>(xAxis,yAxis);


    lineChart.setTitle("Daily Trend");

    XYChart.Series tempSeries = new XYChart.Series();
    tempSeries.setName("Temperature");
    XYChart.Series humiSeries = new XYChart.Series();
    humiSeries.setName("Humidity");
    XYChart.Series co2Series = new XYChart.Series();
    co2Series.setName("CO2");
    // Reading readings[] = null;

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

            int times = Integer.parseInt(br.readLine());
            Reading readings[] = new Reading[times];

            for(int i = 0;i < times; i++ ) {
              readings[i] = new Reading();
              readings[i].setTimestamp(br.readLine());
              readings[i].setTemperature(Double.parseDouble(br.readLine()));
              readings[i].setHumidity(Double.parseDouble(br.readLine()));
              readings[i].setCO2(100 * Double.parseDouble(br.readLine()));
            }
            for(int i = 0; i < readings.length; i++) {
              tempSeries.getData().add(new XYChart.Data(readings[i].timestamp, readings[i].temperature));
            }

            for(int i = 0; i < readings.length; i++) {
              humiSeries.getData().add(new XYChart.Data(readings[i].timestamp, readings[i].humidity));
            }

            for(int i = 0; i < readings.length; i++) {
              co2Series.getData().add(new XYChart.Data(readings[i].timestamp, readings[i].co2));
            }

            lineChart.getData().addAll(tempSeries, humiSeries, co2Series);
        }
    } catch (Exception e) {
      System.out.println(e);
    } finally {
        con.disconnect();
    }


    Scene scene  = new Scene(lineChart,800,600);

    window.setScene(scene);
    window.showAndWait();


  }
  //
  //
  // public static Reading[] getTrend(String uid, String from, String to) {
  // // public void getSensors(String user) {
  //
  //   Reading readings[] = null;
  //
  //   try {
  //
  //       URL myurl = new URL(url);
  //       con = (HttpURLConnection) myurl.openConnection();
  //
  //       con.setDoOutput(true);
  //       con.setRequestMethod("POST");
  //       con.setRequestProperty("User-Agent", "Java client");
  //       con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
  //
  //       try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
  //           wr.write(postData);
  //       }
  //
  //       StringBuilder content;
  //
  //       try (BufferedReader br = new BufferedReader(
  //               new InputStreamReader(con.getInputStream()))) {
  //
  //           String line;
  //           // content = new StringBuilder();
  //           int times = Integer.parseInt(br.readLine());
  //           // System.out.println(times);
  //           readings = new Reading[times];
  //           int ul = times*4;
  //           for(int i = 0;i < ul; i++ ) {
  //             readings[i].timestamp   = br.readLine();
  //             System.out.println(readings[i].timestamp);
  //             readings[i].temperature = Double.parseDouble(br.readLine());
  //             readings[i].humidity    = Double.parseDouble(br.readLine());
  //             readings[i].temperature = Double.parseDouble(br.readLine());
  //             // System.out.println(br.readLine());
  //           }
  //           for(int i = 0; i < readings.length; i++) {
  //             System.out.println(readings[i].timestamp);
  //             System.out.println(readings[i].temperature);
  //             System.out.println(readings[i].humidity);
  //             System.out.println(readings[i].co2);
  //           }
  //
  //       }
  //   } catch (Exception e) {
  //   } finally {
  //       con.disconnect();
  //   }
  //   return readings;
  // }
}
