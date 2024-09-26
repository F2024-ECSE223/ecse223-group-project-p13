/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.util.*;
import java.sql.Date;

// line 15 "../../../../../coolsupplies.ump"
public class Admin extends User
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Importance { Mandatory, Recommended, Optional }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Admin Attributes
  private String name;
  private String password;

  //Admin Associations
  private School school;
  private List<Bundle> bundle;
  private List<Item> item;
  private List<Guardian> guardian;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Admin(String aName, String aPassword, Application aApplication, School aSchool)
  {
    super(aName, aPassword, aApplication);
    name = "admin@cool.ca";
    password = "admin";
    if (aSchool == null || aSchool.getAdmin() != null)
    {
      throw new RuntimeException("Unable to create Admin due to aSchool. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    school = aSchool;
    bundle = new ArrayList<Bundle>();
    item = new ArrayList<Item>();
    guardian = new ArrayList<Guardian>();
  }

  public Admin(String aName, String aPassword, Application aApplication, Date aStartDateForSchool, Date aEndDateForSchool)
  {
    super(aName, aPassword, aApplication);
    name = "admin@cool.ca";
    password = "admin";
    school = new School(aStartDateForSchool, aEndDateForSchool, this);
    bundle = new ArrayList<Bundle>();
    item = new ArrayList<Item>();
    guardian = new ArrayList<Guardian>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public School getSchool()
  {
    return school;
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
  public Guardian getGuardian(int index)
  {
    Guardian aGuardian = guardian.get(index);
    return aGuardian;
  }

  public List<Guardian> getGuardian()
  {
    List<Guardian> newGuardian = Collections.unmodifiableList(guardian);
    return newGuardian;
  }

  public int numberOfGuardian()
  {
    int number = guardian.size();
    return number;
  }

  public boolean hasGuardian()
  {
    boolean has = guardian.size() > 0;
    return has;
  }

  public int indexOfGuardian(Guardian aGuardian)
  {
    int index = guardian.indexOf(aGuardian);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundle()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Bundle addBundle(double aDiscountAmount, boolean aDiscount)
  {
    return new Bundle(aDiscountAmount, aDiscount, this);
  }

  public boolean addBundle(Bundle aBundle)
  {
    boolean wasAdded = false;
    if (bundle.contains(aBundle)) { return false; }
    Admin existingAdmin = aBundle.getAdmin();
    boolean isNewAdmin = existingAdmin != null && !this.equals(existingAdmin);
    if (isNewAdmin)
    {
      aBundle.setAdmin(this);
    }
    else
    {
      bundle.add(aBundle);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBundle(Bundle aBundle)
  {
    boolean wasRemoved = false;
    //Unable to remove aBundle, as it must always have a admin
    if (!this.equals(aBundle.getAdmin()))
    {
      bundle.remove(aBundle);
      wasRemoved = true;
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
  public static int minimumNumberOfItem()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Item addItem(String aName, double aPrice)
  {
    return new Item(aName, aPrice, this);
  }

  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (item.contains(aItem)) { return false; }
    Admin existingAdmin = aItem.getAdmin();
    boolean isNewAdmin = existingAdmin != null && !this.equals(existingAdmin);
    if (isNewAdmin)
    {
      aItem.setAdmin(this);
    }
    else
    {
      item.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a admin
    if (!this.equals(aItem.getAdmin()))
    {
      item.remove(aItem);
      wasRemoved = true;
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
  public static int minimumNumberOfGuardian()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Guardian addGuardian(String aName, String aPassword, Application aApplication)
  {
    return new Guardian(aName, aPassword, aApplication, this);
  }

  public boolean addGuardian(Guardian aGuardian)
  {
    boolean wasAdded = false;
    if (guardian.contains(aGuardian)) { return false; }
    Admin existingAdmin = aGuardian.getAdmin();
    boolean isNewAdmin = existingAdmin != null && !this.equals(existingAdmin);
    if (isNewAdmin)
    {
      aGuardian.setAdmin(this);
    }
    else
    {
      guardian.add(aGuardian);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGuardian(Guardian aGuardian)
  {
    boolean wasRemoved = false;
    //Unable to remove aGuardian, as it must always have a admin
    if (!this.equals(aGuardian.getAdmin()))
    {
      guardian.remove(aGuardian);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGuardianAt(Guardian aGuardian, int index)
  {  
    boolean wasAdded = false;
    if(addGuardian(aGuardian))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuardian()) { index = numberOfGuardian() - 1; }
      guardian.remove(aGuardian);
      guardian.add(index, aGuardian);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGuardianAt(Guardian aGuardian, int index)
  {
    boolean wasAdded = false;
    if(guardian.contains(aGuardian))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuardian()) { index = numberOfGuardian() - 1; }
      guardian.remove(aGuardian);
      guardian.add(index, aGuardian);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGuardianAt(aGuardian, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    School existingSchool = school;
    school = null;
    if (existingSchool != null)
    {
      existingSchool.delete();
    }
    for(int i=bundle.size(); i > 0; i--)
    {
      Bundle aBundle = bundle.get(i - 1);
      aBundle.delete();
    }
    for(int i=item.size(); i > 0; i--)
    {
      Item aItem = item.get(i - 1);
      aItem.delete();
    }
    for(int i=guardian.size(); i > 0; i--)
    {
      Guardian aGuardian = guardian.get(i - 1);
      aGuardian.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "school = "+(getSchool()!=null?Integer.toHexString(System.identityHashCode(getSchool())):"null");
  }
}