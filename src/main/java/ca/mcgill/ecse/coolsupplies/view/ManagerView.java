package ca.mcgill.ecse.coolsupplies.view;

import java.io.IOException;
import java.util.function.Function;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Text;

public abstract class ManagerView {
  public StackPane mainContent;
  public BorderPane root;
  public Function<Void, Void> signOut;

  public VBox createSidebar() {
    VBox sidebar = new VBox(10);
    sidebar.setPadding(new Insets(10));
    sidebar.setAlignment(Pos.TOP_CENTER);
    this.createMenubar();

    HBox header = createHeader();

    VBox navigation = new VBox(10);

    for (String page : getTabs()) {
      Button button = createNavButton(page);
      navigation.getChildren().add(button);
    }

    sidebar.getChildren().addAll(header, navigation);
    this.updateContent(getTabs()[0]);

    return sidebar;
  }

  public void setSignOut(Function<Void, Void> signOut) {
    this.signOut = signOut;
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

  abstract void updateContent(String page);

  abstract String[] getTabs();

  private void createMenubar() {
    HBox box = new HBox(16);
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    Button signOut = new Button("Sign Out");
    signOut.setOnAction(e -> this.signOut.apply(null));

    Text version = new Text("v1.0.0");
    box.getChildren().addAll(version, spacer, signOut);
    this.root.setBottom(box);
  }

  public void setMain(String fxml) {
    try {
      var root = (Pane) FXMLLoader.load(getClass().getResource("fxml/" + fxml));

      mainContent.getChildren().add(root);
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}