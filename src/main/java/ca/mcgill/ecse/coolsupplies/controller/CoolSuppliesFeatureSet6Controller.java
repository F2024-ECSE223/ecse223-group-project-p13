package ca.mcgill.ecse.coolsupplies.controller;

/* Project Imports */
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

/* Utility Imports */
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class CoolSuppliesFeatureSet6Controller {
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  private final static String STUDENT_MISSING_MESSAGE = "The student does not exist.";
  private final static String PARENT_MISSING_MESSAGE = "The parent does not exist.";

  /**
   * Adds the student with the given name to the list of students associated with the parent with the given email.
   * @author Trevor Piltch
   * @param studentName - The name of the student to add
   * @param parentEmail - The email of the parent of whom we're adding the student
   * @return A string containing an error message, or an empty string on success
   */
  public static String addStudentToParent(String studentName, String parentEmail) {
    Student studentToAdd = Student.getWithName(studentName);    

    if (studentToAdd == null) {
      return STUDENT_MISSING_MESSAGE;
    }

    Parent parent = (Parent) User.getWithEmail(parentEmail);

    if (parent == null) {
      return PARENT_MISSING_MESSAGE;
    }

    parent.addStudent(studentToAdd);

    return "";
  }

  /**
   * Removes the student with the given name from the list of students associated with the parent with the given email.
   * @param studentName - The name of the student to remove
   * @param parentEmail - The email of the parent to remove the student from 
   * @return A string containing an error message, or an empty string on success
   */
  public static String deleteStudentFromParent(String studentName, String parentEmail) {
    Student studentToDelete = Student.getWithName(studentName);

    if (studentToDelete == null) {
      return STUDENT_MISSING_MESSAGE;
    }

    Parent parent = (Parent) User.getWithEmail(parentEmail);

    if (parent == null) {
      return PARENT_MISSING_MESSAGE;
    }

    parent.removeStudent(studentToDelete);

    return "";
  }

  /**
   * Returns a transfer object of the student if they are associated with the given parent, and null if not.
   * @author Trevor Piltch
   * @param studentName - The name of the student to return
   * @param parentEmail - The email of the parent where we are looking for the student
   * @return The student transfer object or null if it doesn't exist
   */
  public static TOStudent getStudentOfParent(String studentName, String parentEmail) {
    Parent parent = (Parent) User.getWithEmail(parentEmail);

    if (parent == null) {
      return null;
    }

    for (Student student : parent.getStudents()) {
      if (student.getName().equals(studentName)) {
        return new TOStudent(studentName, parentEmail);
      }
    }

    return null;
  }


  /**
   * Gets the list of students associated with the parents email as transfer objects.
   * @author Trevor Piltch
   * @param parentEmail - The email of the parent to get the list of students from
   * @return The list of students as transfer objects
   */
  public static List<TOStudent> getStudentsOfParent(String parentEmail) {
    Parent parent = (Parent) User.getWithEmail(parentEmail);
    List<TOStudent> students = new ArrayList<TOStudent>(); 

    if (parent == null) {
      return students;
    }

    for (Student student : parent.getStudents()) {
      TOStudent toStudent = new TOStudent(student.getName(), parentEmail);
      students.add(toStudent);
    }

    return students;
  }

  /**
   * Starts an order with the given parameters 
   * @author Trevor Piltch
   * @param number - The number of the order, must be greater than 0
   * @param date - The date of the order
   * @param level - The level of the order, must be one of "Mandatory", "Recommended", or "Optional"
   * @param parentEmail - The email of the parent who is creating the order
   * @param studentName - The name of the student that is being associated with this order
   * @return A string containing an error message or an empty string if successful.
   */
  public static String startOrder(int number, Date date, String level, String parentEmail, String studentName) {
    if (number < 1) {
      return "The number must be greater than 0.";
    }

    if (Order.getWithNumber(number) != null) {
      return "The number must be unique.";
    }

    Student student = Student.getWithName(studentName);

    if (student == null) {
      return STUDENT_MISSING_MESSAGE;
    }

    Parent parent = (Parent) User.getWithEmail(parentEmail);

    if (parent == null) {
      return PARENT_MISSING_MESSAGE;
    }

    BundleItem.PurchaseLevel purchaseLevel = null;
    

    switch (level) {
      case "Mandatory":
        purchaseLevel = BundleItem.PurchaseLevel.Mandatory;
        break;
      case "Recommended":
        purchaseLevel = BundleItem.PurchaseLevel.Recommended;
        break;
      case "Optional":
        purchaseLevel = BundleItem.PurchaseLevel.Optional;
        break;
      default:
        return "The level must be Mandatory, Recommended, or Optional.";
    }

    try {
      new Order(number, date, purchaseLevel, parent, student, coolSupplies);
    } catch (RuntimeException e) {
      return e.getMessage(); // If we get here, then some other error occurred.
    }

    return "";
  }
} 
