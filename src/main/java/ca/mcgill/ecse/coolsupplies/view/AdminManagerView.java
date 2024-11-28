package ca.mcgill.ecse.coolsupplies.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
  private static final String YEAR = "Start School Year";

  public AdminManagerView(BorderPane root, StackPane mainContent) {
    this.root = root;
    this.mainContent = mainContent;
  }

  @Override
  public void updateContent(String page) {
    mainContent.getChildren().clear();

    if (page.equals(ACCOUNT)) {
      setMain("Update_Admin_password.fxml");
      return;
    }
    else if (page.equals(STUDENTS)) {
      setMain("students.fxml");
      return;
    } 
    else if (page.equals("Start School Year")){
      StackPane content = new StackPane();
      Text text = new Text("School year started successfully!");
      text.setStyle("-fx-font-size: 24px;");
      content.getChildren().add(text);
      mainContent.getChildren().add(content);
    
    }
    else if (page.equals(BUNDLES)) {
      setMain("viewBundles.fxml");
      return;
    }
    else if (page.equals(GRADES)) {
      setMain("add-updategrade.fxml");
      return;
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
