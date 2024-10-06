package ca.mcgill.ecse.coolsupplies.features;

//Project Imports
import ca.mcgill.ecse.coolsupplies.model.*;

//JUnit Imports
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Java Imports
import java.util.List;
import java.util.Map;

//Cucumber Imports
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

<<<<<<< Updated upstream
=======
// CAN WE ADD IMPORTS ???
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

>>>>>>> Stashed changes
public class AddGradeStepDefinitions {
  private CoolSupplies coolSupplies;

  @Given("the following grade entities exists in the system \\(p13)")
  public void the_following_grade_entities_exists_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V muss.javat be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.

    List<Map<String, String>> entities = dataTable.asMaps();

    for (var entity : entities) {
      String level = entity.get("level");
      coolSupplies.addGrade(level);
    }
  }

  @When("the school admin attempts to add a new grade in the system with level {string} \\(p13)")
  public void the_school_admin_attempts_to_add_a_new_grade_in_the_system_with_level_p13(
      String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the number of grade entities in the system shall be {string} \\(p13)")
  public void the_number_of_grade_entities_in_the_system_shall_be_p13(String string) {
    // Write code here that turns the phrase above into concrete actions

    // Get number of grades
    List<Grade> grades = coolSupplies.getGrades();
    int num_grades = grades.size();

    // Test case
    assertEquals(num_grades, Integer.parseInt(string));
  }

  @Then("the error {string} shall be raised \\(p13)")
  public void the_error_shall_be_raised_p13(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the grade {string} shall exist in the system \\(p13)")
  public void the_grade_shall_exist_in_the_system_p13(String string) {
    List<Grade> grades = coolSupplies.getGrades();

    for (int i = 0; i < grades.size(); i++){
      assertTrue(grades.get(i).getLevel() == string);

    }
  }
}
