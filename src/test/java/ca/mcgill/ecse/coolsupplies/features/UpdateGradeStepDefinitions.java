package ca.mcgill.ecse.coolsupplies.features;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.*;
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;

/* Cucumber Imports */
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

/* JUnit Imports */
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class UpdateGradeStepDefinitions {
  private CoolSupplies coolSupplies;

  /**
   * @author Kenny-Alexander Joseph
   * @param string the level that is going to be updated in the system
   * @param string2 the new level that the old level is going to be updated to in the system
   * @return void
   * This gherkin step verifies that the first given level is updated with the second given level.
   */
  @When("the school admin attempts to update grade {string} in the system with level {string} \\(p13)")
  public void the_school_admin_attempts_to_update_grade_in_the_system_with_level_p13(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    callController(CoolSuppliesFeatureSet7Controller.updateGrade(string,string2));
  }

  /**
   * @author Trevor Piltch
   * @param string the level which should not exist in the system
   * @return void
   * This gherkin step verifies that the given level does not exist in the system.
   */
  @Then("the grade {string} shall not exist in the system \\(p13)")
  public void the_grade_shall_not_exist_in_the_system_p13(String string) {
    List<Grade> grades = coolSupplies.getGrades();
    List<String> levels = new ArrayList<>();

    for (int i = 0; i < grades.size(); i++) {
      levels.add(grades.get(i).getLevel());
    }

    assertFalse(levels.contains(string));
  }

  /**
   * @author Trevor Piltch
   * @param dataTable the list of grades that should exist in the system
   * @return void
   * This gherkin step verifies that the list of grades exists in the system
   */
  @Then("the following grade entities shall exist in the system \\(p13)")
  public void the_following_grade_entities_shall_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> entities = dataTable.asMaps();
    List<String> levels = new ArrayList<>();

    List<String> system_levels = new ArrayList<>();

    for (var entity : entities) {
      String level = entity.get("level");
      levels.add(level);
    }

    for (var grade : coolSupplies.getGrades()) {
      system_levels.add(grade.getLevel());
    }

    assertTrue(system_levels.containsAll(levels));
  }

  /* Helper Methods */

  /**
   * @author Trevor Piltch
   * @param result the error string coming from a controller call
   * @return void
   * Checks if the controller failed (i.e. returned a non-empty error string) and adds the string to the shared error object.
   */
  private void callController(String result) {
    if (!result.isEmpty()) {
      AddGradeStepDefinitions.error += result;
    }
  }
}

