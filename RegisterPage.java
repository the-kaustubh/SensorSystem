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

class RegisterPage {
  public static int evoke() throws Exception {
    Stage window = new Stage();
    FXMLLoader fl = new FXMLLoader();
    // fl.setLocation(new URL("file:///home/kaustubh/Documents/Java/SensorSystem/register.fxml"));
    fl.setLocation(new URL("file:///C:/register.fxml"));
    GridPane gp = fl.<GridPane>load();

    // window.setScene(gp, 800, 600);
    Scene scene;
    if(User.getUser().equals("Admin")) {
      scene = new Scene(gp, 800, 600);
    } else {
      Alert a = new Alert(AlertType.ERROR);
      a.setContentText("You have to be Admin to add new user");
      a.show();
      return 0;
    }
    window.setScene(scene);
    window.show();
    return 1;
  }
}
