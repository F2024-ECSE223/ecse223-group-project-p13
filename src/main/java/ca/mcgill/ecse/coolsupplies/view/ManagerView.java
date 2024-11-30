package ca.mcgill.ecse.coolsupplies.view;

import java.io.IOException;
import java.util.function.Function;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import atlantafx.base.theme.Styles;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
  private ImageView imageView = new ImageView();
  public StringProperty schoolYear = new SimpleStringProperty("Start School Year");

  public VBox createSidebar() {
    VBox sidebar = new VBox(10);
    sidebar.setPadding(new Insets(10));
    sidebar.setAlignment(Pos.TOP_CENTER);
    this.createMenubar();

    VBox header = createHeader();

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

 private VBox createHeader() {
    VBox header = new VBox(10);
    header.setPadding(new Insets(15));
    header.setAlignment(Pos.CENTER);

    Text title = new Text("CoolSupplies");
    title.getStyleClass().add(Styles.TITLE_2);

    Text subtitle = new Text("by TechnoDupes");
    subtitle.getStyleClass().add(Styles.TEXT_SUBTLE);

    if (CoolSuppliesApplication.isLight) {
      imageView.setImage(CoolSuppliesFxmlView.icon);
    }
    else {
      imageView.setImage(CoolSuppliesFxmlView.darkIcon);
    }

    imageView.setFitHeight(150);
    imageView.setFitWidth(150);
    imageView.setPreserveRatio(true);

    header.getChildren().addAll(imageView, title, subtitle);

    return header;
  }

  private Button createNavButton(String text) {
    if (text.equals(AdminManagerView.YEAR)) {
      AdminManagerView.schoolYearButton = new Button(schoolYear.get());
      AdminManagerView.schoolYearButton.setMaxWidth(Double.MAX_VALUE);
      AdminManagerView.schoolYearButton.setAlignment(Pos.CENTER_LEFT);
      AdminManagerView.schoolYearButton.setPadding(new Insets(10));

      AdminManagerView.schoolYearButton.setOnAction(e -> updateContent(text));
      AdminManagerView.schoolYearButton.getStyleClass().add(Styles.ACCENT);
      return AdminManagerView.schoolYearButton;
    }
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
    box.setPadding(new Insets(16, 16, 16, 16));

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Button signOut = new Button("Sign Out");
    signOut.setOnAction(e -> this.signOut.apply(null));
    signOut.getStyleClass().add(Styles.DANGER);

    Button theme = new Button(CoolSuppliesApplication.isLight ? "Dark" : "Light");
    theme.setOnAction((e) -> {
      if (CoolSuppliesApplication.isLight) {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        this.imageView.setImage(CoolSuppliesFxmlView.darkIcon);
      } 
      else {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        this.imageView.setImage(CoolSuppliesFxmlView.icon);
      }
      CoolSuppliesApplication.isLight = !CoolSuppliesApplication.isLight;
      this.createMenubar();
      System.out.println("NOTICE: Updated theme");
    });

    Text version = new Text("v1.0.0");
    box.getChildren().addAll(version, spacer, theme, signOut);
    this.root.setTop(box);
  }

  public void setMain(String fxml) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(this.getClass().getResource("fxml/"+fxml));
      loader.setClassLoader(this.getClass().getClassLoader());

      var root = (Pane) loader.load();

      mainContent.getChildren().add(root);
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}