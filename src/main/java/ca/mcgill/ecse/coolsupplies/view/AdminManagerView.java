package ca.mcgill.ecse.coolsupplies.view;

import java.io.IOException;
import java.util.concurrent.Callable;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdminManagerView {
  /* Menu Items */
  private static final String ACCOUNT = "Account";
  private static final String PARENTS = "Parents";
  private static final String STUDENTS = "Students";
  private static final String GRADES = "Grades";
  private static final String ITEMS = "Items";
  private static final String BUNDLES = "Bundles";
  private static final String ORDERS = "Orders";

  private StackPane mainContent;
  private HBox header;
  private Callable<Void> signOut;

  public void setContent(StackPane mainContent) {
    this.mainContent = mainContent;
  }

  public void setHeader(HBox header) {
    this.header = header;
  }

  public VBox createSidebar() {
    VBox sidebar = new VBox(10);
    sidebar.setPadding(new Insets(10));
    sidebar.setAlignment(Pos.TOP_CENTER);

    HBox header = createHeader();

    VBox navigation = new VBox(10);
    String[] pages = {ACCOUNT, PARENTS, GRADES, STUDENTS, ITEMS, BUNDLES, ORDERS};

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

    if (page.equals(STUDENTS)) {
      setMain("students.fxml");
    } else {
      StackPane content = new StackPane();
      Text text = new Text("This is the " + page + " page");
      text.setStyle("-fx-font-size: 24px;");
      content.getChildren().add(text);
      mainContent.getChildren().add(content);
    }
  }

 private void createMenubar() {
    HBox box = new HBox(16);
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    Button signOut = new Button("Sign Out");

    Text version = new Text("v1.0.0");
    box.getChildren().addAll(version, spacer, signOut);
  }

  private void setMain(String fxml) {
    try {
      var root = (Pane) FXMLLoader.load(getClass().getResource(fxml));

      mainContent.getChildren().add(root);
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
