package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
public class CoolSuppliesFeatureSet1Controller {

  // can i create a static instance ?
  public static String updateAdmin(String password) {
    /**
     * This method updates the Admin's password with the one provided.
     * @author Lune Letailleur
     * @param password is a String containing the new password.
     * @return String: the exit status message
     **/
      // do I need to check if the old password is right
      // need to call the setPassword() method on the admin instance not a new admin
    CoolSuppliesApplication.getCoolSupplies().getAdmin().setPassword(password);
    return "Admin password updated!";
  }

  public static String addParent(String email, String password, String name, int phoneNumber) {
    /**
     * This method adds a Parent entity with the given values for its attributes.
     * @author Lune Letailleur
     * @param email: a string with the parent's email
     * @param password: a string with the parent's password
     * @param name: a string with the parent's name
     * @param phoneNumber: a integer corresponding to the parent's phone number
     * @return String: the exit status message correspondong to the right situation.
     **/

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
    /**
     * This method updates a Parent entity with the given values for its attributes (password, name and phone number).
     * @author Lune Letailleur
     * @param email: a string with the parent's email
     * @param password: a string with the parent's new password
     * @param name: a string with the parent's new name
     * @param phoneNumber: a integer corresponding to the parent's new phone number
     * @return String: the exit status message correspondong to the right situation.
     **/

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

    /**
     * This method find the corresponding parent to the given email and deletes the parent entity
     * @author Lune Letailleur
     * @param email: a string with the parent's email
     * @return String: the exit status message correspondong to the right situation.
     **/

    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      if (parent.getEmail().equals(email)) {
        parent.delete(); // NOT SURE RIGHT METHOD
        return "Parent account was deleted.";
      }
    }
    return "Parent account wasn't deleted as it does not exist. ";
  }

  public static TOParent getParent(String email) {
    /**
     * This method creates the transfer object, TO, for a certain parent of a given email
     * @author Lune Letailleur
     * @param email: a string with the parent's email
     * @return TOParent: trasnfer object for the parent entity
     **/

    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      if (parent.getEmail().equals(email)) {
        Parent parent_to_return = parent;
      }
    }
    return new TOParent(parent_to_return.getEmail(), parent_to_return.getPassword(), parent_to_return.getName(), parent_to_return.getPhoneNumber());
  }

  // returns all parents
  public static List<TOParent> getParents() {
    /**
     * This method iterates through the list of parent entities, adds them to a list of transfer objects and returns that list.
     * @author Lune Letailleur
     * @param none
     * @return List<TOParent>, the list of the parents's transfer objects
     **/
    // creating an array list of parents and adding the TOs to it
    List<TOParent> parents = new ArrayList<TOParent>();
    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      parents.add(getParent(parent.getEmail()));
    }
    return parents;
  }
}

