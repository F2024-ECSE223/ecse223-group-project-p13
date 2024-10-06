package ca.mcgill.ecse.coolsupplies.features;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;

/* Cucumber Imports */
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/* JUnit Imports */
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

public class UpdateGradeStepDefinitions {
  private CoolSupplies coolSupplies;

  @When("the school admin attempts to update grade {string} in the system with level {string} \\(p13)")
  public void the_school_admin_attempts_to_update_grade_in_the_system_with_level_p13(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    String result = CoolSuppliesFeatureSet7Controller.updateGrade(string,string2);
    assertTrue(!result.equals("Error: Grade was not updated"));
  }

  @Then("the grade {string} shall not exist in the system \\(p13)")
  public void the_grade_shall_not_exist_in_the_system_p13(String string) {
    List<Grade> grades = coolSupplies.getGrades();

    for (int i = 0; i < grades.size(); i++) {
      assertTrue(grades.get(i).getLevel() != string);
    }
  }

  @Then("the following grade entities shall exist in the system \\(p13)")
  public void the_following_grade_entities_shall_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {

    List<String> grade_entities = dataTable.asList(String.class);
    List<String> levels = new ArrayList<String>();
    List<Grade> grades = coolSupplies.getGrades();

    for (int i = 0; i < grades.size(); i++) {
      levels.add(grades.get(i).getLevel());
    }

    assertTrue(levels.containsAll(grade_entities));
  }
}

