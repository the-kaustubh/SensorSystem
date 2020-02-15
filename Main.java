import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
// import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.collections.ObservableList;
import javafx.beans.binding.*;

public class Main extends Application {
    Stage window;
    private String[] propertyName = {"UID", "Location",
    "MachineName", "setCO2_min", "setCO2_max", "setTEMP_min",
    "setTEMP_max", "co2", "temperature", "humidity", "status"};

    private String[] propertyLabel = {"UID", "Location",
    "Machine Name", "Set CO2(%) min", "Set CO2 max", "Set TEMP min",
    "Set TEMP max", "CO2", "Temperature", "Humidity", "Status"};

    private char[] propertyType = {'s', 's',
    's', 'd', 'd', 'd',
    'd', 'd', 'd', 'd', 's'};

    public TableView<Sensor> report;
    public ObservableList<Sensor> list;
    private NodeFetcher sensorMaster = new NodeFetcher();
    public static void main(String[] args) {
        launch (args);
    }

    public void start(Stage primaryStage) {
        window = primaryStage;

        Collection<TableColumn<Sensor, ?>> tc_list = new ArrayList<>();

        for(int i = 0; i < 11; i++) {
            if(propertyType[i] == 's')
            tc_list.add(getStrColumn(propertyLabel[i], propertyName[i]));
            if(propertyType[i] == 'd')
            tc_list.add(getDblColumn(propertyLabel[i], propertyName[i]));
        }
        Canvas canvas = new Canvas( 1000, 70 );
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // ImageView logo = new ImageView("./AtechnoLogo.jpg");
        // logo.setFitHeight(50);
        // logo.setFitWidth(50);
        // logo.setX(15);
        // logo.setY(15);

        //

        // Rectangle rect = new Rectangle(0,0,dataDisplayAreaAnchorPane.getWidth(),dataDisplayAreaAnchorPane.getHeight());
        // dataDisplayAreaAnchorPane.setClip(rect);
        // WritableImage writableImage;
        // writableImage = new WritableImage((int) dataDisplayAreaAnchorPane.getPrefWidth(),
        //         (int) dataDisplayAreaAnchorPane.getPrefHeight());
        // dataDisplayAreaAnchorPane.snapshot(null, writableImage);
        // eventDispatcher.printLandscape(writableImage);
        //


        HBox hb1 = new HBox(5);

        gc.setFill( Color.ORANGE );
        gc.setStroke( Color.BLUE );
        gc.setLineWidth(1);

        Font theFont = Font.font("Trebuchet MS", FontWeight.BOLD, 12 );
        gc.setFont( theFont );
        gc.fillText("ATECHNO", 30, 15);
        gc.fillText("EMBEDDED", 30, 25);
        gc.fillText("SOLUTIONS", 30, 35);
        gc.fillText("(OPC) PVT LTD", 30, 45);

        Font theFont1 = Font.font("Trebuchet MS", FontWeight.BOLD, 24 );
        gc.setFont( theFont1 );
        gc.fillText("NIV MCC Central Monitoring System", 150, 20);

        Font theFont2 = Font.font("Trebuchet MS", FontWeight.NORMAL, 20 );
        gc.setFont( theFont2 );
        gc.fillText("National Institute of Virology Microbial Containment Complex", 150, 45);

        report = new TableView<>();
        list = sensorMaster.getSensors(User.getUser());
        report.setItems(list);
        // report.getProperty().bind(list);
        Timer timer = new Timer();
        Label UserName = new Label(User.getUser());
        // UserName.textProperty().bind(Bindings.convert(User.getUser()));
        Button Login    = new Button("Login");
        Button Logout   = new Button("Logout");
        Button Register = new Button("Register");
        Button PrintReport = new Button("Print Report");
        HBox hb = new HBox(10, Login, UserName, Logout, Register, PrintReport);
        // UserName.textProperty().bind(User.getUser());
        Register.setOnAction(e -> {
          try { RegisterPage.evoke(); }
          catch (Exception ex) { System.out.println(ex);}
        });

        Login.setOnAction(e -> {
          try {
            LoginPage.evoke();
            String user = User.getUser();
            UserName.setText(user);
            list = sensorMaster.getRTReadings(user,
                   sensorMaster.getSensors(user));
            report.setItems(list);
          }
          catch (Exception ex) { System.out.println(ex);}
        });

        Logout.setOnAction(e -> {
          User.setUser("None");
          UserName.setText(User.getUser());
          report.setItems(sensorMaster.getRTReadings(User.getUser(),
                          sensorMaster.getSensors(User.getUser())));
        });

        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            // UserName.setText(User.getUser());
            String user = User.getUser();
            list = sensorMaster.getRTReadings(user,
                   sensorMaster.getSensors(user));
            report.setItems(list);
          }
        }, 0, 1000 );  // Every 5 minutes
        // hb1.getChildren().addAll(logo, canvas);
        hb1.getChildren().addAll( canvas);
        VBox vb = new VBox(10, hb1, report, hb);

        tc_list.forEach((tc) -> {
            report.getColumns().add(tc);
        });
        GridPane root = new GridPane();
        try {
            root = FXMLLoader.load(getClass().getResource("configure.fxml"));
        } catch (Exception e) {
            System.out.println("It's me" + e);
        }
        PrintReport.setOnAction(e -> {
          System.out.println("Printing");
          try {
            PrintNode.sendPrint(window, report);
          } catch (Exception ex) {
            System.out.println(ex);
          }
        });

        Tab configureView = new Tab("Configure", root);
        Tab reportView  = new Tab("Report View", vb);

        TabPane tp = new TabPane();
        tp.getTabs().add(reportView);
        tp.getTabs().add(configureView);

        // System.out.println(report.getHeight());

        Scene scene = new Scene( tp);
        window.setScene(scene);
        window.setTitle("Central Monitoring System");
        window.setMaximized(true);
        window.show();
    }

    static TableColumn<Sensor, String> getStrColumn(String caption, String id) {
        TableColumn<Sensor, String> col = new TableColumn<>(caption);
        col.setMinWidth(100);
        col.setCellValueFactory(new PropertyValueFactory<>(id));
        return col;
    }


    static TableColumn<Sensor, Double> getDblColumn(String caption, String id) {
        TableColumn<Sensor, Double> col = new TableColumn<>(caption);
        col.setMinWidth(100);
        col.setCellValueFactory(new PropertyValueFactory<>(id));
        return col;
    }
}
