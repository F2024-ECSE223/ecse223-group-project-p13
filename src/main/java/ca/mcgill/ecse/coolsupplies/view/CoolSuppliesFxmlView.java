package ca.mcgill.ecse.coolsupplies.view;

import java.io.IOException;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CoolSuppliesFxmlView extends Application {
  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  private static CoolSuppliesFxmlView instance;
  private List<Node> refreshableNodes = new ArrayList<>();

  private StackPane mainContent;
  private BorderPane root;

  private ParentManagerView parentManager = new ParentManagerView();
  private AdminManagerView adminManager = new AdminManagerView();

  private static Font title = new Font(30);
  
  private static Image icon =
      new Image(CoolSuppliesFxmlView.class.getResourceAsStream("resources/icon.png"));

  @Override
  public void start(Stage primaryStage) {
    Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    root = new BorderPane();
    mainContent = new StackPane();
   
    splashScreen();
    root.setCenter(mainContent);

    Scene scene = new Scene(root, 800, 600);

    primaryStage.setMinHeight(600);
    primaryStage.setMinWidth(800);
    primaryStage.setTitle("CoolSupplies");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void splashScreen() {
    mainContent.getChildren().clear();
    root.getChildren().clear();

    VBox box = new VBox();
    box.setPrefSize(400, 600);
    box.setAlignment(Pos.CENTER);

    HBox buttons = new HBox();
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(60);

    Button admin = new Button("Admin");
    admin.setPrefSize(100, 80);
    admin.setOnAction(e -> updateTab("admin"));

    Button parent = new Button("Parent");
    parent.setPrefSize(100, 80);
    parent.setOnAction(e -> updateTab("parent"));

    buttons.getChildren().addAll(admin, parent);

    VBox header = new VBox(16);
    header.setPadding(new Insets(0, 0, 16, 0));
    header.setAlignment(Pos.CENTER);

    ImageView imageView = new ImageView(CoolSuppliesFxmlView.icon);
    imageView.setFitHeight(100);
    imageView.setFitWidth(100);
    imageView.setScaleX(3);
    imageView.setScaleY(3);

    imageView.setPreserveRatio(true);

    Text title = new Text("Welcome to CoolSupplies, TechnoDupes!");
    title.setFont(CoolSuppliesFxmlView.title);

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMMM, YYYY");
    String formattedDate = "Today is " + currentDate.format(formatter);

    Text date = new Text(formattedDate);

    header.getChildren().addAll(imageView, title, date);

    box.getChildren().addAll(header, buttons);

    mainContent.getChildren().add(box);
  }

  private void updateTab(String source) {
    if (source.equalsIgnoreCase("parent")) {
      parentManager.setContent(mainContent);
      VBox sidebar = parentManager.createSidebar();

      root.setLeft(sidebar);
    }
    else if (source.equalsIgnoreCase("admin")) {
      adminManager.setContent(mainContent);
      VBox sidebar = adminManager.createSidebar();

      root.setLeft(sidebar);
    }
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
