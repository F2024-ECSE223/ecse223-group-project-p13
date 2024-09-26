/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.sql.Date;
import java.util.*;

// line 28 "../../../../../coolsupplies.ump"
public class Order
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Importance { Mandatory, Recommended, Optional }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private boolean studentPickup;
  private Date date;
  private double fee;
  private int authorizationCode;
  private int lateAuthorizationCode;
  private Importance importance;
  private int orderNumber;

  //Order Associations
  private List<Item> item;
  private List<Bundle> bundle;
  private List<Student> student;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(boolean aStudentPickup, Date aDate, double aFee, int aAuthorizationCode, int aLateAuthorizationCode, int aOrderNumber)
  {
    studentPickup = aStudentPickup;
    date = aDate;
    fee = aFee;
    authorizationCode = aAuthorizationCode;
    lateAuthorizationCode = aLateAuthorizationCode;
    orderNumber = aOrderNumber;
    item = new ArrayList<Item>();
    bundle = new ArrayList<Bundle>();
    student = new ArrayList<Student>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStudentPickup(boolean aStudentPickup)
  {
    boolean wasSet = false;
    studentPickup = aStudentPickup;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setFee(double aFee)
  {
    boolean wasSet = false;
    fee = aFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setAuthorizationCode(int aAuthorizationCode)
  {
    boolean wasSet = false;
    authorizationCode = aAuthorizationCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setLateAuthorizationCode(int aLateAuthorizationCode)
  {
    boolean wasSet = false;
    lateAuthorizationCode = aLateAuthorizationCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setImportance(Importance aImportance)
  {
    boolean wasSet = false;
    importance = aImportance;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderNumber(int aOrderNumber)
  {
    boolean wasSet = false;
    orderNumber = aOrderNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean getStudentPickup()
  {
    return studentPickup;
  }

  public Date getDate()
  {
    return date;
  }

  public double getFee()
  {
    return fee;
  }

  public int getAuthorizationCode()
  {
    return authorizationCode;
  }

  public int getLateAuthorizationCode()
  {
    return lateAuthorizationCode;
  }

  public Importance getImportance()
  {
    return importance;
  }

  public int getOrderNumber()
  {
    return orderNumber;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isStudentPickup()
  {
    return studentPickup;
  }
  /* Code from template association_GetMany */
  public Item getItem(int index)
  {
    Item aItem = item.get(index);
    return aItem;
  }

  public List<Item> getItem()
  {
    List<Item> newItem = Collections.unmodifiableList(item);
    return newItem;
  }

  public int numberOfItem()
  {
    int number = item.size();
    return number;
  }

  public boolean hasItem()
  {
    boolean has = item.size() > 0;
    return has;
  }

  public int indexOfItem(Item aItem)
  {
    int index = item.indexOf(aItem);
    return index;
  }
  /* Code from template association_GetMany */
  public Bundle getBundle(int index)
  {
    Bundle aBundle = bundle.get(index);
    return aBundle;
  }

  public List<Bundle> getBundle()
  {
    List<Bundle> newBundle = Collections.unmodifiableList(bundle);
    return newBundle;
  }

  public int numberOfBundle()
  {
    int number = bundle.size();
    return number;
  }

  public boolean hasBundle()
  {
    boolean has = bundle.size() > 0;
    return has;
  }

  public int indexOfBundle(Bundle aBundle)
  {
    int index = bundle.indexOf(aBundle);
    return index;
  }
  /* Code from template association_GetMany */
  public Student getStudent(int index)
  {
    Student aStudent = student.get(index);
    return aStudent;
  }

  public List<Student> getStudent()
  {
    List<Student> newStudent = Collections.unmodifiableList(student);
    return newStudent;
  }

  public int numberOfStudent()
  {
    int number = student.size();
    return number;
  }

  public boolean hasStudent()
  {
    boolean has = student.size() > 0;
    return has;
  }

  public int indexOfStudent(Student aStudent)
  {
    int index = student.indexOf(aStudent);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItem()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (item.contains(aItem)) { return false; }
    item.add(aItem);
    if (aItem.indexOfOrder(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aItem.addOrder(this);
      if (!wasAdded)
      {
        item.remove(aItem);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    if (!item.contains(aItem))
    {
      return wasRemoved;
    }

    int oldIndex = item.indexOf(aItem);
    item.remove(oldIndex);
    if (aItem.indexOfOrder(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aItem.removeOrder(this);
      if (!wasRemoved)
      {
        item.add(oldIndex,aItem);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Item aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItem()) { index = numberOfItem() - 1; }
      item.remove(aItem);
      item.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Item aItem, int index)
  {
    boolean wasAdded = false;
    if(item.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItem()) { index = numberOfItem() - 1; }
      item.remove(aItem);
      item.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundle()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addBundle(Bundle aBundle)
  {
    boolean wasAdded = false;
    if (bundle.contains(aBundle)) { return false; }
    bundle.add(aBundle);
    if (aBundle.indexOfOrder(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBundle.addOrder(this);
      if (!wasAdded)
      {
        bundle.remove(aBundle);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeBundle(Bundle aBundle)
  {
    boolean wasRemoved = false;
    if (!bundle.contains(aBundle))
    {
      return wasRemoved;
    }

    int oldIndex = bundle.indexOf(aBundle);
    bundle.remove(oldIndex);
    if (aBundle.indexOfOrder(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBundle.removeOrder(this);
      if (!wasRemoved)
      {
        bundle.add(oldIndex,aBundle);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBundleAt(Bundle aBundle, int index)
  {  
    boolean wasAdded = false;
    if(addBundle(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundle()) { index = numberOfBundle() - 1; }
      bundle.remove(aBundle);
      bundle.add(index, aBundle);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBundleAt(Bundle aBundle, int index)
  {
    boolean wasAdded = false;
    if(bundle.contains(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundle()) { index = numberOfBundle() - 1; }
      bundle.remove(aBundle);
      bundle.add(index, aBundle);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBundleAt(aBundle, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfStudent()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Student addStudent(String aName, GradeLevel aGradeLevel)
  {
    return new Student(aName, this, aGradeLevel);
  }

  public boolean addStudent(Student aStudent)
  {
    boolean wasAdded = false;
    if (student.contains(aStudent)) { return false; }
    Order existingOrder = aStudent.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aStudent.setOrder(this);
    }
    else
    {
      student.add(aStudent);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStudent(Student aStudent)
  {
    boolean wasRemoved = false;
    //Unable to remove aStudent, as it must always have a order
    if (!this.equals(aStudent.getOrder()))
    {
      student.remove(aStudent);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addStudentAt(Student aStudent, int index)
  {  
    boolean wasAdded = false;
    if(addStudent(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudent()) { index = numberOfStudent() - 1; }
      student.remove(aStudent);
      student.add(index, aStudent);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStudentAt(Student aStudent, int index)
  {
    boolean wasAdded = false;
    if(student.contains(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudent()) { index = numberOfStudent() - 1; }
      student.remove(aStudent);
      student.add(index, aStudent);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStudentAt(aStudent, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Item> copyOfItem = new ArrayList<Item>(item);
    item.clear();
    for(Item aItem : copyOfItem)
    {
      aItem.removeOrder(this);
    }
    ArrayList<Bundle> copyOfBundle = new ArrayList<Bundle>(bundle);
    bundle.clear();
    for(Bundle aBundle : copyOfBundle)
    {
      aBundle.removeOrder(this);
    }
    for(int i=student.size(); i > 0; i--)
    {
      Student aStudent = student.get(i - 1);
      aStudent.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "studentPickup" + ":" + getStudentPickup()+ "," +
            "fee" + ":" + getFee()+ "," +
            "authorizationCode" + ":" + getAuthorizationCode()+ "," +
            "lateAuthorizationCode" + ":" + getLateAuthorizationCode()+ "," +
            "orderNumber" + ":" + getOrderNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "importance" + "=" + (getImportance() != null ? !getImportance().equals(this)  ? getImportance().toString().replaceAll("  ","    ") : "this" : "null");
  }
}