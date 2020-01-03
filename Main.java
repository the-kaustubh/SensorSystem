import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.collections.ObservableList;

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

    public String user = "kaustubh";


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

        gc.setFill( Color.ORANGE );
        gc.setStroke( Color.BLUE );
        gc.setLineWidth(1);
        Font theFont = Font.font("Trebuchet MS", FontWeight.BOLD, 52 );
        gc.setFont( theFont );
        gc.fillText( "Name ", 500, 50);
        gc.strokeText( "Name ", 500, 50);

        report = new TableView<>();
        list = sensorMaster.getSensors(user);
        report.setItems(list);
        Timer timer = new Timer();
         timer.schedule(new TimerTask() {
            @Override
            public void run() {
              list = sensorMaster.getRTReadings(user, sensorMaster.getSensors(user));
              report.setItems(list);
            }
         }, 0, 1000 * 60 * 1);  // Every 5 minutes
        VBox vb = new VBox(10, canvas, report);

        // // works
        // for (TableColumn<Sensor, ?> tableColumn : tc_list) {
        //     report.getColumns().add(tableColumn);
        // }


        // // works
        tc_list.forEach((tc) -> {
            report.getColumns().add(tc);
        });
        GridPane root = new GridPane();
        try {
            root = FXMLLoader.load(getClass().getResource("configure.fxml"));
        } catch (Exception e) {
            System.out.println(e);
        }

        Tab configureView = new Tab("Configure", root);
        Tab reportView  = new Tab("Report View", vb);

        TabPane tp = new TabPane();
        tp.getTabs().add(reportView);
        tp.getTabs().add(configureView);

        // System.out.println(report.getHeight());

        Scene scene = new Scene( tp);
        window.setScene(scene);
        window.setTitle("Window1r");
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

    // static ObservableList<Sensor> getSensor() {
    //     ObservableList<Sensor> sensors = FXCollections.observableArrayList();
    //     sensors.add(new Sensor("100000", "LOC1"));
    //     return sensors;

    // }
}
