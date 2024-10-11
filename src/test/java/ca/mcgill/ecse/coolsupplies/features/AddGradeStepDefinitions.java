package ca.mcgill.ecse.coolsupplies.features;

// Project Imports

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddGradeStepDefinitions {
  private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  public static String error = "";
  

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

  /**
   * Attempts to add a new grade in the system and intercepts any error that might occur
   * @author Clara Dupuis
   * @param string represents the name of the grade added to the system
   * @return void
   */

  @When("the school admin attempts to add a new grade in the system with level {string} \\(p13)")
  public void the_school_admin_attempts_to_add_a_new_grade_in_the_system_with_level_p13(
      String string) {
    // Write code here that turns the phrase above into concrete actions
    callController(CoolSuppliesFeatureSet7Controller.addGrade(string));
    
  }

  /**
   * Tests if the number of grades in the system is correct
   * @author Dimitri Christopoulos
   * @param string What the number of grades in the system is supposed to be
   * @return void
   * @throws AssertionError If the number of grades in the system does not match the expected value
   */
  @Then("the number of grade entities in the system shall be {string} \\(p13)")
  public void the_number_of_grade_entities_in_the_system_shall_be_p13(String string) {
    // Write code here that turns the phrase above into concrete actions

    // Get number of grades
    List<Grade> grades = coolSupplies.getGrades();
    int num_grades = grades.size();

    // Test case
    assertEquals(num_grades, Integer.parseInt(string));
  }

  /**
   *verifies if the expected error message matches the error message in the log
   * @author Nil Akkurt
   * @param string
   * @return void 
   * @throws AssertionError if the error message is not found in the error log
   */
  @Then("the error {string} shall be raised \\(p13)")
  public void the_error_shall_be_raised_p13(String string) {
    assertTrue(error.contains(string));
  }

  /**
   * @author Edouard Dupont
   * @param string is a specific grade we want to check
   * @return void
   * This test verifies if if a specific grade exists in the system
   */
  @Then("the grade {string} shall exist in the system \\(p13)")
  public void the_grade_shall_exist_in_the_system_p13(String string) {
    List<Grade> grades = coolSupplies.getGrades();
    List<String>  levels = new ArrayList<>();

    for (int i = 0; i < grades.size(); i++){
      levels.add(grades.get(i).getLevel());
    }

    assertTrue(levels.contains(string));
  }

  /**
   * verifies if there is an error and appends the error to the error string
   * @author Clara Dupuis
   * @param result The result string returned by the controller. It will be empty if there is no error
   * return void
   */
  private void callController(String result){
    if (!result.isEmpty()){
      error += result;
    }
  }
}
