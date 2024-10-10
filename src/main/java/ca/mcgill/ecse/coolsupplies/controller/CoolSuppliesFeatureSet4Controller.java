package ca.mcgill.ecse.coolsupplies.controller;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import java.util.List;

public class CoolSuppliesFeatureSet4Controller {

  public static String addBundle(String name, int discount, String gradeLevel) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  public static String updateBundle(String name, String newName, int newDiscount,
      String newGradeLevel) {
    Grade myGrade = null;
    for(int i = 0; i< CoolSuppliesApplication.getCoolSupplies().numberOfBundles(); i++) {
      if(CoolSuppliesApplication.getCoolSupplies().getBundle(i).getName() == name){
        GradeBundle myBundle= CoolSuppliesApplication.getCoolSupplies().getBundle(i);
        boolean nameSet = myBundle.setName(newName);
        boolean discountSet = myBundle.setDiscount(newDiscount);
        for(int j=0; j<CoolSuppliesApplication.getCoolSupplies().numberOfGrades(); j++){
          if (CoolSuppliesApplication.getCoolSupplies().getGrade(i).getLevel() == newGradeLevel){ //MAYBE SHOULD USE ISEQUALS
            myGrade = CoolSuppliesApplication.getCoolSupplies().getGrade(i);
          }
        }
        boolean gradeSet = myBundle.setGrade(myGrade);
        if(nameSet && discountSet && gradeSet){
          return "Bundle updated successfully.";
        }
        else {
          return "Bundle was not updated successfully.";
        }
      }
    }
    return "Bundle was not found.";
  }

  public static String deleteBundle(String name) {
    for(int i= 0; i< CoolSuppliesApplication.getCoolSupplies().)
  }

  public static TOGradeBundle getBundle(String name) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  // returns all bundles
  public static List<TOGradeBundle> getBundles() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

}
