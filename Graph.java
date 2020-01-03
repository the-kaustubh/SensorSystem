import javafx.stage.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

class Graph {
 public static void displayGraph() {
  Stage gr = new Stage();

  // gr.initModality(Modality.APPLICATION_MODAL);
  gr.setTitle("Trend");
  gr.setMinHeight(400);
  gr.setMinWidth(300);

  VBox root = new VBox();
  Scene scene = new Scene(root);

  Canvas canvas = new Canvas( 400, 200 );
  root.getChildren().add( canvas );

  GraphicsContext gc = canvas.getGraphicsContext2D();

  gc.setFill( Color.RED );
  gc.setStroke( Color.BLACK );
  gc.setLineWidth(2);
  Font theFont = Font.font("Arial", FontWeight.BOLD, 48 );
  gc.setFont( theFont );
  gc.fillText( "Hello, World! ", 60, 50);
  gc.strokeText( "Hello, World! ", 60, 50);

  gr.setScene(scene);
  gr.showAndWait();
 }
}
