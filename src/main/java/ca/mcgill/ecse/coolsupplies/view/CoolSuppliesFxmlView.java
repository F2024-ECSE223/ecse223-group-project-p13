package ca.mcgill.ecse.coolsupplies.view;

import java.awt.Taskbar;
import java.awt.Toolkit;
import java.io.IOException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
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

  private ParentManagerView parentManager;
  private AdminManagerView adminManager;

  public static Font title = new Font(26);
  public static Font title2 = new Font(22);

  public static Image icon =
      new Image(CoolSuppliesFxmlView.class.getResourceAsStream("resources/icon.png"));

  @Override
  public void start(Stage primaryStage) {
    Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    root = new BorderPane();
    mainContent = new StackPane();

    setAppIcon();

    splashScreen();

    Scene scene = new Scene(root, 800, 600);

    primaryStage.setMinHeight(600);
    primaryStage.setMinWidth(800);
    primaryStage.setTitle("CoolSupplies");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /* MARK: Views */

  /**
   * @author Trevor Piltch
   * @brief Creates the initial launch screen with options to switch to parent or admin views.
   */
  private void splashScreen() {
    mainContent.getChildren().clear();
    root.getChildren().clear();

    VBox box = new VBox(16);
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

    Text title = new Text("Welcome to CoolSupplies!");
    title.setFont(CoolSuppliesFxmlView.title);

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMMM dd, YYYY");
    String formattedDate = "Today is " + currentDate.format(formatter);

    Text date = new Text(formattedDate);

    header.getChildren().addAll(imageView, title, date);

    Button register = new Button("Register");
    register.setOnAction((e) -> {
      updateTab("register");
    });

    box.getChildren().addAll(header, buttons, register);

    mainContent.getChildren().add(box);
    root.setCenter(mainContent);
  }

  private void updateTab(String source) {
    if (source.equalsIgnoreCase("parent")) {
      parentManager = new ParentManagerView(root, mainContent);
      parentManager.setSignOut((a) -> {
        this.splashScreen();
        return null;
      });
      VBox sidebar = parentManager.createSidebar();

      root.setLeft(sidebar);
    } else if (source.equalsIgnoreCase("admin")) {
      adminManager = new AdminManagerView(root, mainContent);
      adminManager.setSignOut((a) -> {
        this.splashScreen();
        return null;
      });
      VBox sidebar = adminManager.createSidebar();

      root.setLeft(sidebar);
    }
    else if (source.equalsIgnoreCase("register")) {
      try {
        var register = (Pane) FXMLLoader.load(getClass().getResource("fxml/register_parent.fxml"));
        HBox header = new HBox();
        header.setPadding(new Insets(16, 16, 16, 16));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button back = new Button("Back");
        back.setOnAction((e) -> splashScreen());

        header.getChildren().addAll(back, spacer);

        this.root.setTop(header);
        this.root.setCenter(register);
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }

  /* MARK: Helper Methods */
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

  /**
   * @author Trevor Piltch
   * @brief Sets the application icon for the dock or taskbar depending on the operating system
   */
  private void setAppIcon() {
    if (Taskbar.isTaskbarSupported()) {
      Taskbar taskbar = Taskbar.getTaskbar();

      if (taskbar.isSupported(java.awt.Taskbar.Feature.ICON_IMAGE)) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        java.awt.Image image =
            tk.getImage(CoolSuppliesFxmlView.class.getResource("resources/app_icon.png"));
        taskbar.setIconImage(image);
      }
    }
  }

  public static void handleErr(String err) {
    if (!err.isEmpty()) {
      System.out.println("ERROR: " + err);

      Alert errAlert = new Alert(AlertType.ERROR);
      errAlert.setContentText(err);
      errAlert.show();
    }
  }

  public static void newWindow(String fxml, String title) {
    try {
        FXMLLoader loader = new FXMLLoader(CoolSuppliesFxmlView.class.getResource("fxml/" + fxml));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 300));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error loading FXML file: " + e.getLocalizedMessage());
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error showing new window: " + e.getLocalizedMessage());
    }
}
}
