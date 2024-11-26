package ca.mcgill.ecse.coolsupplies.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ParentManagerView {
  private StackPane mainContent;

  public static final String ACCOUNT = "Account";
  public static final String ORDERS = "Orders";
  public static final String STUDENTS = "Students";

  public void setContent(StackPane mainContent) {
    this.mainContent = mainContent;
  }

  public VBox createSidebar() {
    VBox sidebar = new VBox(10);
    sidebar.setPadding(new Insets(10));
    sidebar.setAlignment(Pos.TOP_CENTER);

    HBox header = createHeader();

    VBox navigation = new VBox(10);
    String[] pages = {ACCOUNT, ORDERS, STUDENTS};

    for (String page : pages) {
      Button button = createNavButton(page);
      navigation.getChildren().add(button);
    }

    sidebar.getChildren().addAll(header, navigation);
    this.updateContent(ACCOUNT);

    return sidebar;
  }

  private HBox createHeader() {
    HBox header = new HBox(10);
    header.setPadding(new Insets(15));
    header.setAlignment(Pos.CENTER_LEFT);

    Text title = new Text("CoolSupplies");
    ImageView imageView = new ImageView();
    Image icon = new Image(CoolSuppliesFxmlView.class.getResourceAsStream("resources/icon.png"));
    imageView.setImage(icon);
    imageView.setFitHeight(60);
    imageView.setFitWidth(60);
    imageView.setPreserveRatio(true);

    header.getChildren().addAll(imageView, title);

    return header;
  }

  private Button createNavButton(String text) {
    Button button = new Button(text);
    button.setMaxWidth(Double.MAX_VALUE);
    button.setAlignment(Pos.CENTER_LEFT);
    button.setPadding(new Insets(10));

    button.setOnAction(e -> updateContent(text));

    return button;
  }

  private void updateContent(String page) {
    mainContent.getChildren().clear();

    try {
      if (page.equals(ACCOUNT)) {
        var root = (Pane) FXMLLoader.load(getClass().getResource("ParentManager.fxml"));

        mainContent.getChildren().add(root);
        return;
      }
      else {
        StackPane content = new StackPane();
        Text text = new Text("This is the " + page + " page");
        text.setStyle("-fx-font-size: 24px;");
        content.getChildren().add(text);
        mainContent.getChildren().add(content);
      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
