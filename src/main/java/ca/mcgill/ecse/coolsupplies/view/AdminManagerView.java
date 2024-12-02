package ca.mcgill.ecse.coolsupplies.view;

import java.util.List;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.Iteration3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdminManagerView extends ManagerView {
  /* Menu Items */
  private static final String ACCOUNT = "Account";
  private static final String PARENTS = "Parents";
  private static final String STUDENTS = "Students";
  private static final String GRADES = "Grades";
  private static final String ITEMS = "Items";
  private static final String BUNDLES = "Bundles";
  private static final String ORDERS = "Orders";
  public static String YEAR = "School Year";

  public static Button schoolYearButton;

  public AdminManagerView(BorderPane root, StackPane mainContent) {
    this.root = root;
    this.mainContent = mainContent;
  }

  @Override
  public void updateContent(String page) {
    if (!page.equals(YEAR)) {
      mainContent.getChildren().clear();
    }

    if (page.equals(ACCOUNT)) {
      setMain("Update_Admin_password.fxml");
      return;
    }
    else if (page.equals(STUDENTS)) {
      setMain("students.fxml");
      return;
    } 
    else if (page.equals(PARENTS)) {
      setMain("remove_parent.fxml");
    }

    else if (page.equals(ITEMS)){
      setMain("Item.fxml");
      return;
    }
    else if (page.equals(YEAR)){
      String status = CoolSuppliesApplication.schoolYearStarted ? "ended" : "started";

      List<TOOrder> orders = Iteration3Controller.viewAllOrders();
      String result = "";

      for (TOOrder order : orders) {
        if (!CoolSuppliesApplication.schoolYearStarted) {
          result = Iteration3Controller.startSchoolYear(order.getNumber());
          CoolSuppliesFxmlView.handleErr(result);
        }
      }

      if (!result.isEmpty()) {
        return;
      }

      CoolSuppliesApplication.schoolYearStarted = !CoolSuppliesApplication.schoolYearStarted;

      if (result.isEmpty()) {
        System.out.println("Firing event");
        Event.fireEvent(mainContent, new Event(CoolSuppliesFxmlView.START_SCHOOL_YEAR_EVENT));
      }

      String action = CoolSuppliesApplication.schoolYearStarted ? "End" : "Start";
      AdminManagerView.schoolYearButton.setText(action + " school year");

      Dialog<String> dialog = new Dialog<>();

      VBox content = new VBox(10);
      content.setPadding(new Insets(16, 16, 16, 16));
      Text message = new Text("School year " + status);

      content.getChildren().add(message);

      dialog.getDialogPane().setContent(content);
      dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
      dialog.showAndWait();
    }
    else if (page.equals(BUNDLES)) {
      setMain("view_bundles.fxml");
      return;
    }
    else if (page.equals(GRADES)) {
      setMain("add-updategrade.fxml");
      return;
    }

    else if (page.equals(ORDERS)) {
      setMain("ViewOrdersAdmin.fxml");
    }


    else {
      StackPane content = new StackPane();
      Text text = new Text("This is the " + page + " page");
      text.setStyle("-fx-font-size: 24px;");
      content.getChildren().add(text);
      mainContent.getChildren().add(content);
    }
  }

  @Override
  public String[] getTabs() {
    return new String[] {ACCOUNT, PARENTS, STUDENTS, GRADES, ITEMS, BUNDLES, ORDERS, YEAR};
  }
}
