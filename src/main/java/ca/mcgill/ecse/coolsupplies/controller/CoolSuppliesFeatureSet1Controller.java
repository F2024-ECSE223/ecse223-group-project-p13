package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
public class CoolSuppliesFeatureSet1Controller {

  // can i create a static instance ?
  public static String updateAdmin(String password) {
      // do I need to check if the old password is right
      // need to call the setPassword() method on the admin instance not a new admin
    CoolSuppliesApplication.getCoolSupplies().getAdmin().setPassword(password);
      return "Admin password updated!";
  }

  public static String addParent(String email, String password, String name, int phoneNumber) {
    // need to check for the constraints
    // DO I NEED TO CHECK IF THE PARENT ALREADY EXIST
    // if two conditions are not valid whch one do i return
      if (email.contains(" ") || email.equals("admin@cool.ca") || email.indexOf("@") <= 0 || email.indexOf("@") != email.lastIndexOf("@") || email.indexOf("@") > (email.lastIndexOf(".") -1) || email.lastIndexOf(".") > (email.length() -1)){
        return "Email must not contain any spaces and not be admin@cool.ca, and should follow the general email format 'xxxx@xx.xxx'";
      }
      else if (name == null || name.equals("")){
        return "Name must not be empty or null. Please input a valid name.";
      } else if (password == null || password.equals("")){
        return "Password must not be empty or null. Please input a valid password.";
      } else if (phoneNumber <= 999999 || phoneNumber >= 10000000){
        return "Phone number must be 7 digits without any leading 0s. PLease input a valid phone number.";
      }
      Parent new_parent = new Parent(email, password, name, phoneNumber, CoolSuppliesApplication.getCoolSupplies());
      return "New parent is registered!";
  }

  public static String updateParent(String email, String newPassword, String newName,
      int newPhoneNumber) {

    // checking all constraints of the Parent's attributes
    if (email.contains(" ") || email.equals("admin@cool.ca") || email.indexOf("@") <= 0 || email.indexOf("@") != email.lastIndexOf("@") || email.indexOf("@") > (email.lastIndexOf(".") -1) || email.lastIndexOf(".") > (email.length() -1)){
      return "Email must not contain any spaces and not be admin@cool.ca, and should follow the general email format 'xxxx@xx.xxx'";
    }
    else if (newName == null || newName.equals("")){
      return "Name must not be empty or null. Please input a valid name.";
    } else if (newPassword == null || newPassword.equals("")){
      return "Password must not be empty or null. Please input a valid password.";
    } else if (newPhoneNumber <= 999999 || newPhoneNumber >= 10000000){
      return "Phone number must be 7 digits without any leading 0s. PLease input a valid phone number."
    }

    // finding the Parent withing the Parents list and setting the attributes
    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      if (parent.getEmail().equals(email)) {
        parent.setName(newName);
        parent.setPassword(newPassword);
        parent.setPhoneNumber(newPhoneNumber);
      }
    }
    return "Parent account information was updated.";
  }

  public static String deleteParent(String email) {
    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      if (parent.getEmail().equals(email)) {
        parent.delete(); // NOT SURE RIGHT METHOD
        return "Parent account was deleted.";
      }
    }
    return "Parent account wasn't deleted as it does not exist. ";
  }

  public static TOParent getParent(String email) {
    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      if (parent.getEmail().equals(email)) {
        Parent parent_to_return = parent;
      }
    }
    return new TOParent(parent_to_return.getEmail(), parent_to_return.getPassword(), parent_to_return.getName(), parent_to_return.getPhoneNumber());
  }

  // returns all parents
  public static List<TOParent> getParents() {
    // creating an array list of parents and adding the TOs to it
    List<TOParent> parents = new ArrayList<TOParent>();
    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      parents.add(getParent(parent.getEmail()));
    }
    return parents;
  }
}

