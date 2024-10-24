package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
public class CoolSuppliesFeatureSet1Controller {


  /**
   * This method updates the Admin's password with the one provided.
   * @author Lune Letailleur
   * @param password is a String containing the new password.
   * @return String: the exit status message
   **/
  public static String updateAdmin(String password) {
    // checking for lower and upper case
    boolean hasLower = false;
    boolean hasUpper = false;

    for(char c : password.toCharArray()){
      if(Character.isLowerCase(c)){
        hasLower = true;
      }
      if(Character.isUpperCase(c)){
        hasUpper = true;
      }
    }

    if (password.length() < 4){
      return "Password must be at least four characters long.";
    } else if (!(password.contains("!") || password.contains("#") || password.contains("$")) || !hasLower || !hasUpper){
      return "Password must contain a special character out of !#$, an upper case character, and a lower case character.";
    } else if (!CoolSuppliesApplication.getCoolSupplies().hasAdmin()){
      return "The admin does not exist.";
    }
    CoolSuppliesApplication.getCoolSupplies().getAdmin().setPassword(password);
    return "";
  }

  /**
   * This method adds a Parent entity with the given values for its attributes.
   * @author Lune Letailleur
   * @param email: a string with the parent's email
   * @param password: a string with the parent's password
   * @param name: a string with the parent's name
   * @param phoneNumber: a integer corresponding to the parent's phone number
   * @return String: the exit status message corresponding to the right situation.
   **/
  public static String addParent(String email, String password, String name, int phoneNumber) {

    if (User.hasWithEmail(email)){
      return "The email must be unique.";
    }
    else if (email.contains(" ")){
      return "The email must not contain spaces.";
    }
    else if (email == null || email.equals("")){
      return "The email must not be empty.";
    }
    else if (email.equals("admin@cool.ca")){
      return "The email must not be admin@cool.ca.";
    }
    else if (email.indexOf("@") <= 0 || email.indexOf("@") != email.lastIndexOf("@") || email.indexOf("@") >= (email.lastIndexOf(".") -1) || email.lastIndexOf(".") >= (email.length() -1)){
      return "The email must be well-formed.";
    }
    else if (name == null || name.equals("")){
      return "The name must not be empty.";
    } else if (password == null || password.equals("")){
      return "The password must not be empty.";
    } else if (phoneNumber <= 999999 || phoneNumber >= 10000000){
      return "The phone number must be seven digits.";
    }
    new Parent(email, password, name, phoneNumber, CoolSuppliesApplication.getCoolSupplies());
    return "";
  }

  /**
   * This method updates a Parent entity with the given values for its attributes (password, name and phone number).
   * @author Lune Letailleur
   * @param email: a string with the parent's email
   * @param newPassword: a string with the parent's new password
   * @param newName: a string with the parent's new name
   * @param newPhoneNumber: a integer corresponding to the parent's new phone number
   * @return String: the exit status message corresponding to the right situation.
   **/
  public static String updateParent(String email, String newPassword, String newName,
                                    int newPhoneNumber) {

    // checking all constraints of the Parent's attributes
    if (newName == null || newName.equals("")){
      return "The name must not be empty.";
    } else if (newPassword == null || newPassword.equals("")){
      return "The password must not be empty.";
    } else if (newPhoneNumber <= 999999 || newPhoneNumber >= 10000000){
      return "The phone number must be seven digits.";
    } else if (!User.hasWithEmail(email)){
      return "The parent does not exist.";
    }

    // finding the Parent withing the Parents list and setting the attributes
    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      if (parent.getEmail().equals(email)) {
        parent.setName(newName);
        parent.setPassword(newPassword);
        parent.setPhoneNumber(newPhoneNumber);
      }
    }
    return "";
  }

  /**
   * This method find the corresponding parent to the given email and deletes the parent entity
   * @author Lune Letailleur
   * @param email: a string with the parent's email
   * @return String: the exit status message corresponding to the right situation.
   **/
  public static String deleteParent(String email) {

    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      if (parent.getEmail().equals(email)) {
        parent.delete();
        return "";
      }
    }
    return "The parent does not exist.";
  }

  /**
   * This method creates the transfer object, TO, for a certain parent of a given email
   * @author Lune Letailleur
   * @param email: a string with the parent's email
   * @return TOParent: trasnfer object for the parent entity
   **/
  public static TOParent getParent(String email) {

    if (User.hasWithEmail(email)) {
      Parent parent_to_return = (Parent) User.getWithEmail(email);
      return new TOParent(parent_to_return.getEmail(), parent_to_return.getPassword(), parent_to_return.getName(), parent_to_return.getPhoneNumber());
    }
    return null;
  }

  /**
   * This method iterates through the list of parent entities, adds them to a list of transfer objects and returns that list.
   * @author Lune Letailleur
   * @return List<TOParent>, the list of the parents' transfer objects
   **/
  public static List<TOParent> getParents() {

    // creating an array list of parents and adding the TOs to it
    List<TOParent> parents = new ArrayList<TOParent>();
    for (Parent parent : CoolSuppliesApplication.getCoolSupplies().getParents()) {
      parents.add(getParent(parent.getEmail()));
    }
    return parents;
  }
}