package ca.mcgill.ecse.coolsupplies.view;

import java.awt.Taskbar;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import atlantafx.base.theme.PrimerLight;
import atlantafx.base.theme.Styles;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CoolSuppliesFxmlView extends Application {
  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  public static final EventType<Event> START_SCHOOL_YEAR_EVENT =
      new EventType<>("START_SCHOOL_YEAR");
  private static CoolSuppliesFxmlView instance;
  private List<Node> refreshableNodes = new ArrayList<>();

  private StackPane mainContent;
  private BorderPane root;
  private ParentManagerView parentManager;
  private AdminManagerView adminManager;

  /* Shared variables */
  public static final Font title = new Font(26);
  public static final Font title2 = new Font(22);

  public static final Image icon =
      new Image(CoolSuppliesFxmlView.class.getResourceAsStream("resources/icon.png"));
  public static final Image darkIcon =
      new Image(CoolSuppliesFxmlView.class.getResourceAsStream("resources/icon_dark.png"));

  /**
   * @author Trevor Piltch
   * @param primaryStage - The main page of the app
   * @breif Creates the application with the given styles
   */
  @Override
  public void start(Stage primaryStage) {
    Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    root = new BorderPane();
    mainContent = new StackPane();

    setAppIcon();

    splashScreen();

    Scene scene = new Scene(root);

    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getBounds();

    primaryStage.setMinHeight(400);
    primaryStage.setMinWidth(600);
    primaryStage.setWidth(bounds.getWidth());
    primaryStage.setHeight(bounds.getHeight());
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
    admin.setGraphic(getIcon("resources/admin.svg"));

    Button parent = new Button("Parent");
    parent.setPrefSize(100, 80);
    parent.setOnAction(e -> updateTab("parent"));
    parent.setGraphic(getIcon("resources/profile.svg"));

    buttons.getChildren().addAll(admin, parent);

    VBox header = new VBox();
    header.setPadding(new Insets(0, 0, 16, 0));
    header.setAlignment(Pos.CENTER);

    VBox imageBox = new VBox();
    imageBox.setPadding(new Insets(0, 0, 32, 0));
    imageBox.setAlignment(Pos.CENTER);

    ImageView imageView = CoolSuppliesApplication.isLight ? new ImageView(CoolSuppliesFxmlView.icon) : new ImageView(CoolSuppliesFxmlView.darkIcon);
    imageView.setFitHeight(100);
    imageView.setFitWidth(100);
    imageView.setScaleX(3);
    imageView.setScaleY(3);

    imageView.setPreserveRatio(true);
    imageBox.getChildren().add(imageView);

    Text title = new Text("Welcome to CoolSupplies!");
    title.getStyleClass().add(Styles.TITLE_1);

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMMM dd, YYYY");
    String formattedDate = "Today is " + currentDate.format(formatter) + ".";

    Text date = new Text(formattedDate);
    date.getStyleClass().add(Styles.TITLE_4);

    header.getChildren().addAll(imageBox, title, date);

    Button register = new Button("Register");
    register.getStyleClass().add(Styles.ACCENT);
    register.setOnAction((e) -> {
      updateTab("register");
    });

    box.getChildren().addAll(header, buttons, register);

    mainContent.getChildren().add(box);
    root.setCenter(mainContent);
  }

  /**
   * @author Trevor Piltch
   * @param source - The source of the button push
   * @biref Updates the view depending on which button the user pushed
   */
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
        back.getStyleClass().add(Styles.DANGER);

        header.getChildren().addAll(back, spacer);

        this.root.setTop(header);
        this.root.setCenter(register);
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }

  /* MARK: Helper Methods */
  // Following methods were created in the tutorial...
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

  /**
   * @author Trevor Piltch
   * @param path - The path of the svg icon resource
   * @return An SVGPath representing the given icon
   * @brief Loads the file from the given path and converts the contents into an SVGPath (basically an image for the buttons in JavaFX)
   */
  public static SVGPath getIcon(String path) {
    try {
      InputStream inputStream = CoolSuppliesFxmlView.class.getResourceAsStream(path);
      
      if (inputStream == null) {
        throw new FileNotFoundException("Resource not found: " + inputStream);
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      List<String> content = reader.lines()
          .collect(Collectors.toList());

      SVGPath svg = new SVGPath();
      svg.setContent(String.join(" ",content));

      if (!CoolSuppliesApplication.isLight) {
        svg.setStroke(Color.WHITE);
        svg.setFill(Color.WHITE);
      }

      return svg;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * @author Trevor Piltch
   * @param err - The error message to display
   * @brief Opens a new alert in the application displaying the error message
   */
  public static void handleErr(String err) {
    if (!err.isEmpty()) {
      System.out.println("ERROR: " + err);

      Alert errAlert = new Alert(AlertType.ERROR);
      errAlert.setContentText(err);
      errAlert.show();
    }
  }

  /**
   * @author Trevor Piltch
   * @param fxml - The FXML file to load
   * @param title - The title of the window
   * @brief Creates a new window from the given fxml file and title.
   */
  public static void newWindow(String fxml, String title) {
    try {
        FXMLLoader loader = new FXMLLoader(CoolSuppliesFxmlView.class.getResource("fxml/" + fxml));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
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
