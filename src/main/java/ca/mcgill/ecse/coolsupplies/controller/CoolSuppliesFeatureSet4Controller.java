package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.*;

import java.util.*;

public class CoolSuppliesFeatureSet4Controller {

  /**
   * Adds a GradeBundle to the application if the bundle's name is unique and not empty, the discount is an integer
   * between 0 and 100, and if the gradeLevel is an already existing Grade in the application without a bundle.
   * @author Nil Akkurt
   * @param name
   * @param discount
   * @param gradeLevel
   * @return String
   * @throws RuntimeException if name is null, already exists, if discount is smaller than 0 or larger than 100 or if gradeLevel
   * does not exist in CoolSupplies or if it already has an associated GradeBundle
   */
  public static String addBundle(String name, int discount, String gradeLevel) {

    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();
    for(GradeBundle bundle : bundlesInSystem){
      if(bundle.getName().equals(name)){
        return "The name must be unique.";
      }
    }

    if (name == null || name.trim().isEmpty()){
      return "The name must not be empty.";
    }

    if (!(0 <= discount && discount <= 100)){
      return "The discount must be greater than or equal to 0 and less than or equal to 100.";
    }

    boolean gradeExists = false;
    List<Grade> gradesInSystem = CoolSuppliesApplication.getCoolSupplies().getGrades();
    Grade myGrade = null;
    for(Grade grade: gradesInSystem) {
      if (grade.getLevel().equals(gradeLevel)) {
        gradeExists = true;
        myGrade = grade;
      }
    }
    if(!gradeExists){
      return "The grade does not exist.";
    }

    if(gradeExists){
      if (myGrade.hasBundle()){
        return "A bundle already exists for the grade.";
      }
    }

    GradeBundle myBundle= new GradeBundle(name, discount, CoolSuppliesApplication.getCoolSupplies(), myGrade);
    myGrade.setBundle(myBundle);
    return "";

  }

  /**
   * updates a GradeBundle object that is already existing in the system.
   * @author Nil Akkurt
   * @param name
   * @param newName
   * @param newDiscount
   * @param newGradeLevel
   * @return String
   *
   */
  public static String updateBundle(String name, String newName, int newDiscount,
      String newGradeLevel) {
    GradeBundle bundleToUpdate = null;
    List<GradeBundle> bundles = CoolSuppliesApplication.getCoolSupplies().getBundles();
    for(GradeBundle bundle: bundles){
      if(bundle.getName().equals(name)){
        bundleToUpdate = bundle;
      }
      if(newName.equals(bundle.getName())){
        return "The name must be unique.";
      }
    }
    if(bundleToUpdate == null){
      return "The bundle does not exist.";
    }

    if(newName==null || newName.trim().isEmpty()){
      return "The name must not be empty";
    }

    if(newDiscount < 0 || newDiscount > 100){
     return "The discount must be greater than or equal to 0 and less than or equal to 100.";
    }

    List<Grade> gradesInSystem = CoolSuppliesApplication.getCoolSupplies().getGrades();
    Grade gradeToUpdate = null;
    for(Grade grade : gradesInSystem){
      if(grade.getLevel().equals(newGradeLevel)){
        gradeToUpdate= grade;
      }
    }
    if(gradeToUpdate == null){
      return "The grade does not exist.";
    }
    if(gradeToUpdate.hasBundle()){
      return "A bundle already exists for the grade.";
    }

    bundleToUpdate.setName(newName);
    bundleToUpdate.setDiscount(newDiscount);
    bundleToUpdate.setGrade(gradeToUpdate);
    return "";
  }

  /**
   * deletes a GradeBundle object if it exists in the system
   * @author Nil Akkurt
   * @param name
   * @return String
   * @throws RuntimeException if GradeBundle with name does not exist
   */
  public static String deleteBundle(String name) {
    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();
    GradeBundle bundleToDelete = null;
    for(GradeBundle bundle: bundlesInSystem){
      if(bundle.getName().equals(name)){
        bundleToDelete = bundle;
      }
    }
    if(bundleToDelete == null){
      return ("The grade bundle does not exist.");
    }
    bundleToDelete.delete();   
    return "";
  }

  /**
   * gets the GradeBundle object with name from the system (returns it as a transfer object).
   * @author Nil Akkurt
   * @param name
   * @return TOGradeBundle
   */
  public static TOGradeBundle getBundle(String name) {
    GradeBundle bundleToGet = null;
    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();
    for(GradeBundle bundle : bundlesInSystem){
      if(bundle.getName().equals(name)){
        bundleToGet= bundle;
      }
    }
    return new TOGradeBundle(bundleToGet.getName(), bundleToGet.getDiscount(), bundleToGet.getGrade().getLevel());
    //would this give an error if the bundle stays null?
  }

  // returns all bundles

  /**
   * gets all the GradeBundle bundles from the system (returns them as transfer objects, in a list).
   * @author Nil Akkurt
   * @return List<TOGradeBundle>
   */
  public static List<TOGradeBundle> getBundles() {
    List<GradeBundle> bundlesInSystem = CoolSuppliesApplication.getCoolSupplies().getBundles();
    List<TOGradeBundle> bundlesToGet = new ArrayList<>();
    for(GradeBundle bundle : bundlesInSystem){
      TOGradeBundle TObundle = new TOGradeBundle(bundle.getName(), bundle.getDiscount(), bundle.getGrade().getLevel());
      bundlesToGet.add(TObundle);
    }
    return bundlesToGet;
    }

  }
