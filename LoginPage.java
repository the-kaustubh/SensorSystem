import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.net.URL;

class LoginPage {

  public static int evoke() throws Exception {
    Stage window = new Stage();
    FXMLLoader fl = new FXMLLoader();
    if(System.getProperty("os.name").equals("Linux")) {
      fl.setLocation(new URL("file:///home/kaustubh/Documents/Java/SensorSystem/login.fxml"));
    } else {
      fl.setLocation(new URL("file:///C:/login.fxml"));
    }
    GridPane gp = fl.<GridPane>load();

    // window.setScene(gp, 800, 600);
    Scene scene = new Scene(gp, 800, 600);
    window.setScene(scene);
    window.show();
    return 1;
  }
}
