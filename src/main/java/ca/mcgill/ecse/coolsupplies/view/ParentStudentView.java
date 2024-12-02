package ca.mcgill.ecse.coolsupplies.view;

import java.util.List;
import java.util.stream.Collectors;
import atlantafx.base.theme.Styles;
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

  /**
   * @author Trevor Piltch
   * @param mainContent - The content to add this to 
   * @brief Creates a new view for the user to manage their students
   */
  public ParentStudentView(StackPane mainContent) {
    this.mainContent = mainContent;
    this.associatedList = FXCollections.observableArrayList(
        CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail.get()));

    allList = FXCollections.observableArrayList(CoolSuppliesFeatureSet2Controller.getStudents());
    updateLists();
    ListView<TOStudent> associatedListView = new ListView<>(associatedList);
    this.createAssociatedList(associatedListView);
    ListView<TOStudent> fullList = this.createFullList();
    HBox parent = selectParent();

    Text title = new Text("Your Students");
    title.setFont(CoolSuppliesFxmlView.title2);

    Text title2 = new Text("All Students");
    title2.setFont(CoolSuppliesFxmlView.title2);

    VBox view = new VBox();
    view.setPadding(new Insets(16, 16, 16, 16));
    view.getChildren().addAll(parent, title, associatedListView, title2, fullList);

    this.mainContent.getChildren().add(view);
    this.parentEmail.addListener((observable, oldValue, newValue) -> {
      if (!newValue.isEmpty()) {
        associatedList.clear();
        associatedList.addAll(CoolSuppliesFeatureSet6Controller.getStudentsOfParent(newValue));
        updateLists();
      }
    });
  }

  /**
   * @author Trevor Piltch
   * Create the header view to select the parent from a drop down and update internal state
   */
  private HBox selectParent() {
    HBox header = new HBox();

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
        }
      });

      Region spacer = new Region();
      HBox.setHgrow(spacer, Priority.ALWAYS);

      header.getChildren().addAll(spacer, box);

    return header;
  }

  /**
   * @author Trevor Piltch
   * @param listView - The list to add the students to
   * @brief Creates a list of students that are associated with the parent
   */
  private void createAssociatedList(ListView<TOStudent> listView) {
    listView.setItems(associatedList);
    listView.setCellFactory(null);
    listView.setCellFactory(l -> new ListCell<TOStudent>() {
      @Override
      protected void updateItem(TOStudent student, boolean empty) {
        super.updateItem(student, empty);
        if (empty || student == null) {
          setGraphic(null);
        } else {
          setGraphic(createListItem(student));
        }
      }
    });
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
    delete.getStyleClass().add(Styles.DANGER);
    delete.setOnAction((e) -> {
      err =
          CoolSuppliesFeatureSet6Controller.deleteStudentFromParent(student.getName(), parentEmail.get());
          CoolSuppliesFxmlView.handleErr(err);

      if (err.isEmpty()) {
        associatedList.remove(student);
        allList.add(student);
      }
    });

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Text label = new Text(student.getName() + " - " + student.getGradeLevel());

    content.getChildren().addAll(label, spacer, delete);
    return content;
  }

  /**
   * @author Trevor Piltch
   * @param student - The student to add to the list
   * @return - A hbox represented the view to add to the list
   * @brief - Creates a lists item for a student that is not associated with the current parent
   */
  private HBox createFullListItem(TOStudent student) {
    HBox content = new HBox();
    content.setAlignment(Pos.CENTER_LEFT);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Text label = new Text(student.getName() + " - " + student.getGradeLevel());

    content.getChildren().addAll(label, spacer);

    Button add = new Button("Add");
    add.getStyleClass().add(Styles.SUCCESS);
    add.setOnAction((e) -> {
      err = CoolSuppliesFeatureSet6Controller.addStudentToParent(student.getName(),
          parentEmail.get());
      CoolSuppliesFxmlView.handleErr(err);
      if (err.isEmpty()) {
        associatedList.add(student);
        allList.remove(student);
        updateLists();
      }
    });
    add.visibleProperty().bind(this.parentEmail.isNotEmpty());

    content.getChildren().add(add);

    return content;

  }

  /**
   * @author Trevor Piltch
   * @return A ListView containing all the students that are not associated with a parent
   * @brief Creates a listview containing all the students that are not associated with a parent
   */
  private ListView<TOStudent> createFullList() {
    ListView<TOStudent> listView = new ListView<>(allList);
    listView.setCellFactory(l -> new ListCell<TOStudent>() {
      @Override
      protected void updateItem(TOStudent student, boolean empty) {
        super.updateItem(student, empty);
        if (empty || student == null) {
          setGraphic(null);
        } else {
          setGraphic(createFullListItem(student));
        }
      }
    });

    return listView;
  }

  /**
   * @author Trevor piltch
   * @brief Filters out the associated students from the list of students
   */
  private void updateLists() {
    allList = FXCollections.observableArrayList(CoolSuppliesFeatureSet2Controller.getStudents());

    List<TOStudent> newList =
        allList.stream()
            .filter(s -> !studentHasParent(s.getName())).collect(Collectors.toList());

    allList.clear();
    allList.setAll(newList);
  }

  /**
   * @author Trevor Piltch
   * @param studentName - The student to check if they have a parent
   * @return boolean indicating whether the student has a parent or not
   * @brief Checks if the student with the given name has a parent by iterating through the system parents and checking their associated students.
   */
  private boolean studentHasParent(String studentName) {
    boolean result = false;

    for (TOParent parent : CoolSuppliesFeatureSet1Controller.getParents()) {
      result = CoolSuppliesFeatureSet6Controller.getStudentOfParent(studentName, parent.getEmail()) != null;

      if (result) {
        break;
      }
    }

    return result;
  }
}
