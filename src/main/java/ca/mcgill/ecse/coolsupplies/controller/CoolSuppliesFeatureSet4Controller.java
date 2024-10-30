package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

import java.util.*;

public class CoolSuppliesFeatureSet4Controller {
  /**
   * Adds a GradeBundle to the application if the bundle's name is unique and not empty, the
   * discount is an integer between 0 and 100, and if the gradeLevel is an already existing Grade in
   * the application without a bundle.
   * 
   * @author Nil Akkurt
   * @param name
   * @param discount
   * @param gradeLevel
   * @return String returns the appropriate error message if there is any, returns an empty string
   *         if the action is successful.
   */
  public static String addBundle(String name, int discount, String gradeLevel) {
    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();
    for (GradeBundle bundle : bundlesInSystem) {
      if (bundle.getName().equals(name)) {
        return "The name must be unique.";
      }
    }

    if (name == null || name.trim().isEmpty()) {
      return "The name must not be empty.";
    }

    if (!(0 <= discount && discount <= 100)) {
      return "The discount must be greater than or equal to 0 and less than or equal to 100.";
    }

    boolean gradeExists = false;
    List<Grade> gradesInSystem = CoolSuppliesApplication.getCoolSupplies().getGrades();
    Grade myGrade = null;
    for (Grade grade : gradesInSystem) {
      if (grade.getLevel().equals(gradeLevel)) {
        gradeExists = true;
        myGrade = grade;
      }
    }

    if (!gradeExists) {
      return "The grade does not exist.";
    }

    if (gradeExists) {
      if (myGrade.hasBundle()) {
        return "A bundle already exists for the grade.";
      }
    }

    GradeBundle myBundle = new GradeBundle(name, discount, CoolSuppliesApplication.getCoolSupplies(), myGrade);
    myGrade.setBundle(myBundle);

    return "";

  }

  /**
   * updates a GradeBundle object if it already is in the system, the new name is unique and
   * non-empty, if the new discount value is in the interval [0,100], if the new grade exists in the
   * system and if the new grade does not already have a bundle.
   * 
   * @author Nil Akkurt
   * @param name
   * @param newName
   * @param newDiscount
   * @param newGradeLevel
   * @return String returns the appropriate error message if there is any, returns an empty string
   *         if the action is successful.
   *
   */
  public static String updateBundle(String name, String newName, int newDiscount, String newGradeLevel) {
    GradeBundle bundleToUpdate = null;
    List<GradeBundle> bundles = CoolSuppliesApplication.getCoolSupplies().getBundles();

    for (GradeBundle bundle : bundles) {
      if (bundle.getName().equals(name)) {
        bundleToUpdate = bundle;
      }
    }

    if (bundleToUpdate == null) {
      return "The bundle does not exist.";
    }

    if (newName == null || newName.trim().isEmpty()) {
      return "The name must not be empty.";
    }

    if (newDiscount < 0 || newDiscount > 100) {
      return "The discount must be greater than or equal to 0 and less than or equal to 100.";
    }

    List<Grade> gradesInSystem = CoolSuppliesApplication.getCoolSupplies().getGrades();
    Grade gradeToUpdate = null;

    for (Grade grade : gradesInSystem) {
      if (grade.getLevel().equals(newGradeLevel)) {
        gradeToUpdate = grade;
      }
    }

    if (gradeToUpdate == null) {
      return "The grade does not exist.";
    }

    for (GradeBundle bundle : bundles) {
      if (newName.equals(bundle.getName())) {
        return "The name must be unique.";
      }
    }

    if (gradeToUpdate.hasBundle()) {
      return "A bundle already exists for the grade.";
    }

    bundleToUpdate.setName(newName);
    bundleToUpdate.setDiscount(newDiscount);
    bundleToUpdate.setGrade(gradeToUpdate);

    return "";
  }

  /**
   * deletes a GradeBundle object if it exists in the system
   * 
   * @author Nil Akkurt
   * @param name
   * @return String returns an error message if the action is unsuccessful, returns an empty string
   *         otherwise.
   */
  public static String deleteBundle(String name) {
    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();
    GradeBundle bundleToDelete = null;

    for (GradeBundle bundle : bundlesInSystem) {
      if (bundle.getName().equals(name)) {
        bundleToDelete = bundle;
      }
    }

    if (bundleToDelete == null) {
      return ("The grade bundle does not exist.");
    }

    bundleToDelete.delete();

    return "";
  }

  /**
   * gets the GradeBundle object with name from the system (returns it as a transfer object).
   * 
   * @author Nil Akkurt
   * @param name
   * @return TOGradeBundle
   */
  public static TOGradeBundle getBundle(String name) {
    GradeBundle bundleToGet = null;
    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();

    for (GradeBundle bundle : bundlesInSystem) {
      if (bundle.getName().equals(name)) {
        bundleToGet = bundle;
      }
    }

    if (bundleToGet == null) {
      return null;
    }

    return new TOGradeBundle(bundleToGet.getName(), bundleToGet.getDiscount(), bundleToGet.getGrade().getLevel());
  }

  /**
   * gets all the GradeBundle bundles from the system (returns them as transfer objects, in a list).
   * 
   * @author Nil Akkurt
   * @return List<TOGradeBundle>
   */
  public static List<TOGradeBundle> getBundles() {
    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();
    List<TOGradeBundle> bundlesToGet = new ArrayList<>();

    for (GradeBundle bundle : bundlesInSystem) {
      TOGradeBundle TObundle =
          new TOGradeBundle(bundle.getName(), bundle.getDiscount(), bundle.getGrade().getLevel());
      bundlesToGet.add(TObundle);
    }

    return bundlesToGet;
  }
  
}
