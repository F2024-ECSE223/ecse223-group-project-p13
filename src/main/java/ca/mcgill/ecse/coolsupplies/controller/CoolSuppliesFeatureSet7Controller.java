package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;

import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

public class CoolSuppliesFeatureSet7Controller {

  public static String addGrade(String level) {
    //Initializes newgrade, then checks if initialization succeeded and displays status message accordingly
    new Grade(level,CoolSuppliesApplication.getCoolSupplies());
    if (!Grade.hasWithLevel(level)) return "Grade successfully added";
    else return "Error: Grade was not added";
  }

  public static String updateGrade(String level, String newLevel) {
    //Gets the grade with the string level, then sets the level to new level.
    //If it executes successfully, setLevel returns true, false otherwise.
    //Error message is displayed accordingly. 
    if (Grade.getWithLevel(level).setLevel(newLevel)) return "Grade updated successfully";
    else return "Error: Grade was not updated";
  }

  public static String deleteGrade(String level) {
    //Calls delete level on the instance of grade
    //Checks if grade was successfully removed, then displays status message accordingly
    Grade.getWithLevel(level).delete();
    if (!Grade.hasWithLevel(level)) return "Grade successfully deleted";
    else return "Error: Grade was not deleted";

  }

  public static TOGrade getGrade(String level) {
    //Creates TO from string level to be sent to UI
    return new TOGrade(level);
  }

  // returns all grades
  public static List<TOGrade> getGrades() {
    //Creates list of Grade TO's, iterates through list of all grades, then creates TO of each grade and adds it to the list
    List<TOGrade> grades = new ArrayList<TOGrade>();
    for (Grade i : CoolSuppliesApplication.getCoolSupplies().getGrades()) grades.add(getGrade(i.getLevel()));
    return grades;
  }

}