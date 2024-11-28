package ca.mcgill.ecse.coolsupplies.view;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class GradeManagerView {
  @FXML
  private TableView grade_pane;
  @FXML
  private TableColumn grade_col;
  @FXML
  private TableColumn options_col;
  @FXML
  private TextField addGradeNameTextField;
  @FXML
  private Button addGradeNameButton;
  

  // Event Listener on Button[#addDriverButton].onAction
  @FXML
  public void addGradeNameButton(ActionEvent event) {
    System.out.println("hi");
  }

  // Event Listener on Button[#addRouteButton].onAction
  @FXML
  public void addRouteClicked(ActionEvent event) {
    // omitted
  }

  // Event Listener on Button[#addBusButton].onAction
  @FXML
  public void addBusClicked(ActionEvent event) {
    // omitted
  }
}