package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import ca.mcgill.ecse.coolsupplies.model.*;
public class CoolSuppliesFeatureSet1Controller {
  private static CoolSupplies coolSupplies;
  // can i create a static instance ?
  public static String updateAdmin(String password) {
      // do I need to check if the old password is right
      // need to call the setPassword() method on the admin instance not a new admin
      coolSupplies.getAdmin().setPassword(password);
      return "Admin password updated!";
  }

  public static String addParent(String email, String password, String name, int phoneNumber) {


    return "New parent is registered!";
  }

  public static String updateParent(String email, String newPassword, String newName,
      int newPhoneNumber) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  public static String deleteParent(String email) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  public static TOParent getParent(String email) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  // returns all parents
  public static List<TOParent> getParents() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

}
