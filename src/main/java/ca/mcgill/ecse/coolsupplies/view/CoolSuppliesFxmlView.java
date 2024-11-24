package ca.mcgill.ecse.coolsupplies.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout.Alignment;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CoolSuppliesFxmlView extends Application {
  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  private static CoolSuppliesFxmlView instance;
  private List<Node> refreshableNodes = new ArrayList<>();

  private StackPane mainContent;

  @Override
  public void start(Stage primaryStage) {
    Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    BorderPane root = new BorderPane();

    VBox sidebar = createSidebar();
    sidebar.setPrefWidth(200);

    mainContent = new StackPane();

    updateContent("Home");

    root.setLeft(sidebar);
    // splashScreen();
    root.setCenter(mainContent);

    Scene scene = new Scene(root, 800, 600);

    primaryStage.setMinHeight(600);
    primaryStage.setMinWidth(800);
    primaryStage.setTitle("CoolSupplies");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private VBox createSidebar() {
    VBox sidebar = new VBox(10);
    sidebar.setPadding(new Insets(10));
    sidebar.setAlignment(Pos.TOP_CENTER);

    HBox header = createHeader();

    VBox navigation = new VBox(10);
    String[] pages = {"Account", "Students", "Orders"};

    for (String page : pages) {
      Button button = createNavButton(page);
      navigation.getChildren().add(button);
    }

    sidebar.getChildren().addAll(header, navigation);

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
      if (page.toLowerCase().equals("account")) {
        var root = (Pane) FXMLLoader.load(getClass().getResource("ParentManager.fxml"));

        mainContent.getChildren().add(root);
        return;

      }
      else if (page.toLowerCase().equals("students")) {
        var root = (Pane) FXMLLoader.load(getClass().getResource("students.fxml"));

        mainContent.getChildren().add(root);
        return;
      }
      else if (page.toLowerCase().equals("orders")){
        var root = (Pane) FXMLLoader.load(getClass().getResource("addItem.fxml"));
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

  private void splashScreen() {
    mainContent.getChildren().clear();

    VBox box = new VBox();
    box.setPadding(new Insets(16));

    HBox buttons = new HBox();
    Button admin = new Button("Admin");


    Button parent = new Button("Parent");

    // parent.setOnAction(e -> 

    // );

    buttons.getChildren().addAll(admin, parent);

    Text title = new Text("Welcom to CoolSupplies");

    box.getChildren().addAll(title, buttons);

    mainContent.getChildren().add(box);
  }

  private void updateTab(String source) {

  }

  public void registerRefreshEvent(Node node) {
    refreshableNodes.add(node);
  }

  public void registerRefreshEvent(Node... nodes) {
    for (var node : nodes) {
      refreshableNodes.add(node);
    }
  }

  public void removeRefreshableNode(Node node) {
    refreshableNodes.remove(node);
  }

  public void refresh() {
    for (Node node : refreshableNodes) {
      node.fireEvent(new Event(REFRESH_EVENT));
    }
  }

  public static CoolSuppliesFxmlView getInstance() {
    return instance;
  }
}
