package ca.mcgill.ecse.coolsupplies.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ParentManagerView extends ManagerView {
  public static final String ACCOUNT = "Account";
  public static final String ORDERS = "Orders";
  public static final String STUDENTS = "Students";

  public ParentManagerView(BorderPane root, StackPane mainContent) {
    this.root = root;
    this.mainContent = mainContent;
  }

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


  @Override
  public String[] getTabs() {
    return new String[]{ACCOUNT, ORDERS, STUDENTS};
  }
}
