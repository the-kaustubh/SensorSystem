import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;

class LoginPage {

  public static int evoke() throws Exception {
    Stage window = new Stage();
    FXMLLoader fl = new FXMLLoader();
    fl.setLocation(new File("login.fxml").toURI().toURL());
    GridPane gp = fl.<GridPane>load();
    Scene scene = new Scene(gp, 800, 600);
    window.setScene(scene);
    window.show();
    return 1;
  }
}
