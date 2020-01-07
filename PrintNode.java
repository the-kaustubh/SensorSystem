import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.print.*;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;

import java.net.URL;

class PrintNode {

  public static void sendPrint(Stage owner, Node node) throws Exception {
    Printer printer = Printer.getDefaultPrinter();
    printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL);
    PrinterJob job = PrinterJob.createPrinterJob();

    if (job == null)
    {
        return;
    }

    // Show the print setup dialog
    boolean proceed = job.showPrintDialog(owner);

    if (proceed)
    {
      boolean printed = job.printPage(node);

      if (printed)
      {
          job.endJob();
      }
    }
  }
}
