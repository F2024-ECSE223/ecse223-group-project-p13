/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.util.*;

// line 57 "../../../../../coolsupplies.ump"
public class Item
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Importance { Mandatory, Recommended, Optional }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Attributes
  private String name;
  private double price;
  private int count;
  private Importance importance;

  //Item Associations
  private Admin admin;
  private List<Order> order;
  private List<Bundle> bundle;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Item(String aName, double aPrice, Admin aAdmin)
  {
    name = aName;
    price = aPrice;
    count = 0;
    boolean didAddAdmin = setAdmin(aAdmin);
    if (!didAddAdmin)
    {
      throw new RuntimeException("Unable to create item due to admin. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    order = new ArrayList<Order>();
    bundle = new ArrayList<Bundle>();
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

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setCount(int aCount)
  {
    boolean wasSet = false;
    count = aCount;
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

  public String getName()
  {
    return name;
  }

  public double getPrice()
  {
    return price;
  }

  public int getCount()
  {
    return count;
  }

  public Importance getImportance()
  {
    return importance;
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
      existingAdmin.removeItem(this);
    }
    admin.addItem(this);
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
    if (aOrder.indexOfItem(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aOrder.addItem(this);
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
    if (aOrder.indexOfItem(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aOrder.removeItem(this);
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
    if (aBundle.indexOfItem(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBundle.addItem(this);
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
    if (aBundle.indexOfItem(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBundle.removeItem(this);
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

  public void delete()
  {
    Admin placeholderAdmin = admin;
    this.admin = null;
    if(placeholderAdmin != null)
    {
      placeholderAdmin.removeItem(this);
    }
    ArrayList<Order> copyOfOrder = new ArrayList<Order>(order);
    order.clear();
    for(Order aOrder : copyOfOrder)
    {
      aOrder.removeItem(this);
    }
    ArrayList<Bundle> copyOfBundle = new ArrayList<Bundle>(bundle);
    bundle.clear();
    for(Bundle aBundle : copyOfBundle)
    {
      aBundle.removeItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "," +
            "count" + ":" + getCount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "importance" + "=" + (getImportance() != null ? !getImportance().equals(this)  ? getImportance().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "admin = "+(getAdmin()!=null?Integer.toHexString(System.identityHashCode(getAdmin())):"null");
  }
}