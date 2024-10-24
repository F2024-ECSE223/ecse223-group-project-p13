package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;

import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

public class CoolSuppliesFeatureSet7Controller {

  /**
   * @author Kenny-Alexander Joseph
   * @param String level: the level that is going to be added in the system
   * @return String: exit status message
   * This method checks if level is empty and checks if level isnt a duplicate.
   * If no errors arise, initializes a new grade and displays status message accordingly.
   */
  public static String addGrade(String level) {
   if (!level.equals("")) {
    try {
      new Grade(level,CoolSuppliesApplication.getCoolSupplies());
    } catch (RuntimeException e) {
      if (e.getMessage().equals("Cannot create due to duplicate level. See https://manual.umple.org?RE003ViolationofUniqueness.html")) return "The level must be unique.";
    }
    return "";
   } else return "The level must not be empty.";
  }

  /**
  * @author Kenny-Alexander Joseph
  * @param String level: the level that is going to be updated in the system
  * @param String newLevel: the name of the new level that will replace the old level
  * @return String: exit status message
  * This method checks if newLevel is empty, checks if level exists, and checks if level was set to newlevel successfully.
  * If it executes successfully, setLevel returns true and method returns empty string.
  * Otherwise, error message is displayed accordingly. 
  */
  public static String updateGrade(String level, String newLevel) {

    if (!newLevel.equals("")){
      if (Grade.getWithLevel(level) != null) {
        if (!Grade.getWithLevel(level).setLevel(newLevel)) return "The level must be unique.";
        return "";
      } else return "The grade does not exist.";
    }
    else return "The level must not be empty.";
  }

  /**
  * @author Kenny-Alexander Joseph
  * @param String level: the level that is going to be deleted from the system
  * @return String: exit status message
  * This method checks if level is empty and checks if grade exists.
  * If no issues arise, it deletes the grade and displays status message accordingly.
  */  
  public static String deleteGrade(String level) {
    if (!level.equals("")) {
      if (Grade.getWithLevel(level) != null) {
        Grade.getWithLevel(level).delete();
        return "";
      } else return "The grade does not exist.";
      
    } else return "The level must not be empty.";
  }

  /**
  * @author Kenny-Alexander Joseph
  * @param String level: the level that is going to be turned into a transfer object
  * @return TOGrade: Transfer object of the given level
  * This method creates the tranfer object from string level to be sent to UI.
  */
  public static TOGrade getGrade(String level) {
    return new TOGrade(Grade.getWithLevel(level).getLevel());
  }

  // returns all grades
  /**
  * @author Kenny-Alexander Joseph
  * @param none
  * @return List<TOGrade>: List of the grades' transfer objects
  * This method iterates through the list of all grades, creates list of Grade TO's, 
  * then creates TO of each grade and adds it to the list.
  */
  public static List<TOGrade> getGrades() {
    List<TOGrade> grades = new ArrayList<TOGrade>();
    for (Grade i : CoolSuppliesApplication.getCoolSupplies().getGrades()) grades.add(getGrade(i.getLevel()));
    return grades;
  }

}