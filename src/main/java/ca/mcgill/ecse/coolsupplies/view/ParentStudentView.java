package ca.mcgill.ecse.coolsupplies.view;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet6Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ParentStudentView {
  private StackPane mainContent;
  private String err = "";
  private StringProperty parentEmail = new SimpleStringProperty(""); 

  private ObservableList<TOStudent> associatedList;
  private ObservableList<TOStudent> allList;

  public ParentStudentView(StackPane mainContent) {
    this.mainContent = mainContent;
    this.associatedList = FXCollections.observableArrayList(
        CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail.get()));
    this.allList =
        FXCollections.observableArrayList(CoolSuppliesFeatureSet2Controller.getStudents());

    ListView<TOStudent> list = this.createAssociatedList();
    ListView<TOStudent> fullList = this.createFullList();
    HBox parent = selectParent();

    Text title = new Text("Your Students");
    title.setFont(CoolSuppliesFxmlView.title2);

    Text title2 = new Text("All Students");
    title2.setFont(CoolSuppliesFxmlView.title2);

    VBox view = new VBox();
    view.setPadding(new Insets(16, 16, 16, 16));
    view.getChildren().addAll(parent, title, list, title2, fullList);

    this.mainContent.getChildren().add(view);
  }

  // MARK: Select the Parent
  private HBox selectParent() {
    HBox header = new HBox();

    Text title = new Text("Hello, Parent!");
    title.setFont(CoolSuppliesFxmlView.title);

      ObservableList<TOParent> parents =
          FXCollections.observableList(CoolSuppliesFeatureSet1Controller.getParents());
      ComboBox<TOParent> box = new ComboBox<>(parents);
      box.setCellFactory(lv -> new ListCell<TOParent>() {
        @Override 
        protected void updateItem(TOParent parent, boolean empty) {
          super.updateItem(parent, empty);
          setText(empty ? null : parent.getEmail());
        }
      });

      box.setButtonCell(new ListCell<TOParent>() {
        @Override
        protected void updateItem(TOParent parent, boolean empty) {
          super.updateItem(parent, empty);
          setText(empty ? null : parent.getEmail());
        }
      });

      box.setOnAction((e) -> {
        TOParent selected = box.getSelectionModel().getSelectedItem();

        if (selected != null) {
          this.parentEmail.set(selected.getEmail());
          this.parentEmail.addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
              associatedList.clear();
              associatedList
                  .addAll(CoolSuppliesFeatureSet6Controller.getStudentsOfParent(newValue));
                  updateLists();
                  
            }
          });
        }
      });

      Region spacer = new Region();
      HBox.setHgrow(spacer, Priority.ALWAYS);

      header.getChildren().addAll(title, spacer, box);

    return header;
  }

  // MARK: List of students associated with parent
  private ListView<TOStudent> createAssociatedList() {
    ListView<TOStudent> listView = new ListView<>(associatedList);
    listView.setCellFactory(l -> new ListCell<TOStudent>() {
      @Override
      protected void updateItem(TOStudent student, boolean empty) {
        super.updateItem(student, empty);
        if (empty || student == null) {
          setText(null);
        } else {
          setGraphic(createListItem(student));
        }
      }
    });

    return listView;
  }

  /**
   * @author Trevor Piltch
   * @param student - The student for the list item
   * @return HBox representing the list row item that is being generated
   */
  private HBox createListItem(TOStudent student) {
    HBox content = new HBox();
    content.setAlignment(Pos.CENTER_LEFT);

    Button delete = new Button("Remove");
    delete.setOnAction((e) -> {
      err =
          CoolSuppliesFeatureSet6Controller.deleteStudentFromParent(student.getName(), parentEmail.get());
          CoolSuppliesFxmlView.handleErr(err);

      if (err.isEmpty()) {
        associatedList.remove(student);
      }
    });

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Text label = new Text(student.getName() + " - " + student.getGradeLevel());

    content.getChildren().addAll(label, spacer, delete);
    return content;
  }

  // MARK: Full list of students
  private HBox createFullListItem(TOStudent student) {
    HBox content = new HBox();
    content.setAlignment(Pos.CENTER_LEFT);

    Button delete = new Button("Add");
    delete.setOnAction((e) -> {
      err = CoolSuppliesFeatureSet6Controller.addStudentToParent(student.getName(), parentEmail.get());
      CoolSuppliesFxmlView.handleErr(err);
      if (err.isEmpty()) {
        associatedList.add(student);
      }
    });

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Text label = new Text(student.getName() + " - " + student.getGradeLevel());

    content.getChildren().addAll(label, spacer, delete);
    return content;

  }

  private ListView<TOStudent> createFullList() {
    ListView<TOStudent> listView = new ListView<>(allList);
    listView.setCellFactory(l -> new ListCell<TOStudent>() {
      @Override
      protected void updateItem(TOStudent student, boolean empty) {
        super.updateItem(student, empty);
        if (empty || student == null) {
          setText(null);
        } else {
          setGraphic(createFullListItem(student));
        }
      }
    });

    return listView;
  }

  private void updateLists() {
    allList.filtered((student) -> {
      return !associatedList.contains(student);
    });
  }
}
