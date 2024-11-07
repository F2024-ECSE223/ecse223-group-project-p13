/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.controller;
import java.util.*;

// line 43 "../../../../../CoolSuppliesTransferObjects.ump"
public class TOOrder
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOOrder Attributes
  private String parentEmail;
  private String studentName;
  private String status;
  private String number;
  private String date;
  private String level;
  private String authCode;
  private String penaltyAuthCode;
  private double price;

  //TOOrder Associations
  private List<TOOrderItem> items;

  //Helper Variables
  private boolean canSetItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOOrder(String aParentEmail, String aStudentName, String aStatus, String aNumber, String aDate, String aLevel, String aAuthCode, String aPenaltyAuthCode, double aPrice, TOOrderItem... allItems)
  {
    parentEmail = aParentEmail;
    studentName = aStudentName;
    status = aStatus;
    number = aNumber;
    date = aDate;
    level = aLevel;
    authCode = aAuthCode;
    penaltyAuthCode = aPenaltyAuthCode;
    price = aPrice;
    canSetItems = true;
    items = new ArrayList<TOOrderItem>();
    boolean didAddItems = setItems(allItems);
    if (!didAddItems)
    {
      throw new RuntimeException("Unable to create TOOrder, must not have duplicate items. See https://manual.umple.org?RE001ViolationofImmutability.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getParentEmail()
  {
    return parentEmail;
  }

  public String getStudentName()
  {
    return studentName;
  }

  public String getStatus()
  {
    return status;
  }

  public String getNumber()
  {
    return number;
  }

  public String getDate()
  {
    return date;
  }

  public String getLevel()
  {
    return level;
  }

  public String getAuthCode()
  {
    return authCode;
  }

  public String getPenaltyAuthCode()
  {
    return penaltyAuthCode;
  }

  public double getPrice()
  {
    return price;
  }
  /* Code from template association_GetMany */
  public TOOrderItem getItem(int index)
  {
    TOOrderItem aItem = items.get(index);
    return aItem;
  }

  public List<TOOrderItem> getItems()
  {
    List<TOOrderItem> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(TOOrderItem aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_SetUnidirectionalMany */
  private boolean setItems(TOOrderItem... newItems)
  {
    boolean wasSet = false;
    if (!canSetItems) { return false; }
    canSetItems = false;
    ArrayList<TOOrderItem> verifiedItems = new ArrayList<TOOrderItem>();
    for (TOOrderItem aItem : newItems)
    {
      if (verifiedItems.contains(aItem))
      {
        continue;
      }
      verifiedItems.add(aItem);
    }

    if (verifiedItems.size() != newItems.length)
    {
      return wasSet;
    }

    items.clear();
    items.addAll(verifiedItems);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "parentEmail" + ":" + getParentEmail()+ "," +
            "studentName" + ":" + getStudentName()+ "," +
            "status" + ":" + getStatus()+ "," +
            "number" + ":" + getNumber()+ "," +
            "date" + ":" + getDate()+ "," +
            "level" + ":" + getLevel()+ "," +
            "authCode" + ":" + getAuthCode()+ "," +
            "penaltyAuthCode" + ":" + getPenaltyAuthCode()+ "," +
            "price" + ":" + getPrice()+ "]";
  }
}