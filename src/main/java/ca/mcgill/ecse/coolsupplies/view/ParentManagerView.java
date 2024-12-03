package ca.mcgill.ecse.coolsupplies.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ParentManagerView extends ManagerView {
  public static final String ACCOUNT = "Account";
  public static final String ORDERS = "Orders";
  public static final String STUDENTS = "Students";

  /**
   * @author Trevor Piltch
   * @param root - The root page to link this view to 
   * @param mainContent - The main page to add this view to 
   * @brief Initialize this manager view
   */
  public ParentManagerView(BorderPane root, StackPane mainContent) {
    this.root = root;
    this.mainContent = mainContent;
  }

  /**
   * @author Trevor Piltch
   * @param page - The page triggering the update
   * @brief Changes the main page depending on which button was pressed
   */
  @Override
  public void updateContent(String page) {
    mainContent.getChildren().clear();

    if (page.equals(ACCOUNT)) {
      setMain("update_parent.fxml");
    }
    else if (page.equals(STUDENTS)) {
      new ParentStudentView(mainContent);
      return;
    }
    else if (page.equals(ORDERS)) {
      setMain("ViewOrdersParent.fxml");
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


  /**
   * @author Trevor Piltch
   * @return Array of strings representing the tabs
   * @brief Returns an array of strings representing the tabs in the sidebar
   */
  @Override
  public String[] getTabs() {
    return new String[]{ACCOUNT, ORDERS, STUDENTS};
  }
}
