/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.util.*;

// line 52 "../../../../../coolsupplies.ump"
public class Bundle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Bundle Attributes
  private double discountAmount;
  private boolean discount;

  //Bundle Associations
  private Admin admin;
  private List<Order> order;
  private List<Item> item;
  private List<GradeLevel> gradeLevel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Bundle(double aDiscountAmount, boolean aDiscount, Admin aAdmin)
  {
    discountAmount = aDiscountAmount;
    discount = aDiscount;
    boolean didAddAdmin = setAdmin(aAdmin);
    if (!didAddAdmin)
    {
      throw new RuntimeException("Unable to create bundle due to admin. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    order = new ArrayList<Order>();
    item = new ArrayList<Item>();
    gradeLevel = new ArrayList<GradeLevel>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDiscountAmount(double aDiscountAmount)
  {
    boolean wasSet = false;
    discountAmount = aDiscountAmount;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiscount(boolean aDiscount)
  {
    boolean wasSet = false;
    discount = aDiscount;
    wasSet = true;
    return wasSet;
  }

  public double getDiscountAmount()
  {
    return discountAmount;
  }

  public boolean getDiscount()
  {
    return discount;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isDiscount()
  {
    return discount;
  }
  /* Code from template association_GetOne */
  public Admin getAdmin()
  {
    return admin;
  }
  /* Code from template association_GetMany */
  public Order getOrder(int index)
  {
    Order aOrder = order.get(index);
    return aOrder;
  }

  public List<Order> getOrder()
  {
    List<Order> newOrder = Collections.unmodifiableList(order);
    return newOrder;
  }

  public int numberOfOrder()
  {
    int number = order.size();
    return number;
  }

  public boolean hasOrder()
  {
    boolean has = order.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = order.indexOf(aOrder);
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
  public GradeLevel getGradeLevel(int index)
  {
    GradeLevel aGradeLevel = gradeLevel.get(index);
    return aGradeLevel;
  }

  public List<GradeLevel> getGradeLevel()
  {
    List<GradeLevel> newGradeLevel = Collections.unmodifiableList(gradeLevel);
    return newGradeLevel;
  }

  public int numberOfGradeLevel()
  {
    int number = gradeLevel.size();
    return number;
  }

  public boolean hasGradeLevel()
  {
    boolean has = gradeLevel.size() > 0;
    return has;
  }

  public int indexOfGradeLevel(GradeLevel aGradeLevel)
  {
    int index = gradeLevel.indexOf(aGradeLevel);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAdmin(Admin aAdmin)
  {
    boolean wasSet = false;
    if (aAdmin == null)
    {
      return wasSet;
    }

    Admin existingAdmin = admin;
    admin = aAdmin;
    if (existingAdmin != null && !existingAdmin.equals(aAdmin))
    {
      existingAdmin.removeBundle(this);
    }
    admin.addBundle(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrder()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (order.contains(aOrder)) { return false; }
    order.add(aOrder);
    if (aOrder.indexOfBundle(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aOrder.addBundle(this);
      if (!wasAdded)
      {
        order.remove(aOrder);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    if (!order.contains(aOrder))
    {
      return wasRemoved;
    }

    int oldIndex = order.indexOf(aOrder);
    order.remove(oldIndex);
    if (aOrder.indexOfBundle(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aOrder.removeBundle(this);
      if (!wasRemoved)
      {
        order.add(oldIndex,aOrder);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrder()) { index = numberOfOrder() - 1; }
      order.remove(aOrder);
      order.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(order.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrder()) { index = numberOfOrder() - 1; }
      order.remove(aOrder);
      order.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
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
    if (aItem.indexOfBundle(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aItem.addBundle(this);
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
    if (aItem.indexOfBundle(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aItem.removeBundle(this);
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
  public static int minimumNumberOfGradeLevel()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfGradeLevel()
  {
    return 12;
  }
  /* Code from template association_AddOptionalNToOne */
  public GradeLevel addGradeLevel(int aIndex, String aLevelName, School aSchool)
  {
    if (numberOfGradeLevel() >= maximumNumberOfGradeLevel())
    {
      return null;
    }
    else
    {
      return new GradeLevel(aIndex, aLevelName, aSchool, this);
    }
  }

  public boolean addGradeLevel(GradeLevel aGradeLevel)
  {
    boolean wasAdded = false;
    if (gradeLevel.contains(aGradeLevel)) { return false; }
    if (numberOfGradeLevel() >= maximumNumberOfGradeLevel())
    {
      return wasAdded;
    }

    Bundle existingBundle = aGradeLevel.getBundle();
    boolean isNewBundle = existingBundle != null && !this.equals(existingBundle);
    if (isNewBundle)
    {
      aGradeLevel.setBundle(this);
    }
    else
    {
      gradeLevel.add(aGradeLevel);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGradeLevel(GradeLevel aGradeLevel)
  {
    boolean wasRemoved = false;
    //Unable to remove aGradeLevel, as it must always have a bundle
    if (!this.equals(aGradeLevel.getBundle()))
    {
      gradeLevel.remove(aGradeLevel);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGradeLevelAt(GradeLevel aGradeLevel, int index)
  {  
    boolean wasAdded = false;
    if(addGradeLevel(aGradeLevel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGradeLevel()) { index = numberOfGradeLevel() - 1; }
      gradeLevel.remove(aGradeLevel);
      gradeLevel.add(index, aGradeLevel);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGradeLevelAt(GradeLevel aGradeLevel, int index)
  {
    boolean wasAdded = false;
    if(gradeLevel.contains(aGradeLevel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGradeLevel()) { index = numberOfGradeLevel() - 1; }
      gradeLevel.remove(aGradeLevel);
      gradeLevel.add(index, aGradeLevel);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGradeLevelAt(aGradeLevel, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Admin placeholderAdmin = admin;
    this.admin = null;
    if(placeholderAdmin != null)
    {
      placeholderAdmin.removeBundle(this);
    }
    ArrayList<Order> copyOfOrder = new ArrayList<Order>(order);
    order.clear();
    for(Order aOrder : copyOfOrder)
    {
      aOrder.removeBundle(this);
    }
    ArrayList<Item> copyOfItem = new ArrayList<Item>(item);
    item.clear();
    for(Item aItem : copyOfItem)
    {
      aItem.removeBundle(this);
    }
    for(int i=gradeLevel.size(); i > 0; i--)
    {
      GradeLevel aGradeLevel = gradeLevel.get(i - 1);
      aGradeLevel.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "discountAmount" + ":" + getDiscountAmount()+ "," +
            "discount" + ":" + getDiscount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "admin = "+(getAdmin()!=null?Integer.toHexString(System.identityHashCode(getAdmin())):"null");
  }
}