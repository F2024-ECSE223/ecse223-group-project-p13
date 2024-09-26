/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.util.*;

// line 21 "../../../../../coolsupplies.ump"
public class Guardian extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Guardian Attributes
  private String name;
  private String phoneNumber;

  //Guardian Associations
  private Admin admin;
  private List<Student> child;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Guardian(String aEmail, String aPassword, Application aApplication, Admin aAdmin)
  {
    super(aEmail, aPassword, aApplication);
    name = null;
    phoneNumber = null;
    boolean didAddAdmin = setAdmin(aAdmin);
    if (!didAddAdmin)
    {
      throw new RuntimeException("Unable to create guardian due to admin. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    child = new ArrayList<Student>();
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

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }
  /* Code from template association_GetOne */
  public Admin getAdmin()
  {
    return admin;
  }
  /* Code from template association_GetMany */
  public Student getChild(int index)
  {
    Student aChild = child.get(index);
    return aChild;
  }

  public List<Student> getChild()
  {
    List<Student> newChild = Collections.unmodifiableList(child);
    return newChild;
  }

  public int numberOfChild()
  {
    int number = child.size();
    return number;
  }

  public boolean hasChild()
  {
    boolean has = child.size() > 0;
    return has;
  }

  public int indexOfChild(Student aChild)
  {
    int index = child.indexOf(aChild);
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
      existingAdmin.removeGuardian(this);
    }
    admin.addGuardian(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfChild()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addChild(Student aChild)
  {
    boolean wasAdded = false;
    if (child.contains(aChild)) { return false; }
    child.add(aChild);
    if (aChild.indexOfGuardian(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aChild.addGuardian(this);
      if (!wasAdded)
      {
        child.remove(aChild);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeChild(Student aChild)
  {
    boolean wasRemoved = false;
    if (!child.contains(aChild))
    {
      return wasRemoved;
    }

    int oldIndex = child.indexOf(aChild);
    child.remove(oldIndex);
    if (aChild.indexOfGuardian(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aChild.removeGuardian(this);
      if (!wasRemoved)
      {
        child.add(oldIndex,aChild);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addChildAt(Student aChild, int index)
  {  
    boolean wasAdded = false;
    if(addChild(aChild))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChild()) { index = numberOfChild() - 1; }
      child.remove(aChild);
      child.add(index, aChild);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveChildAt(Student aChild, int index)
  {
    boolean wasAdded = false;
    if(child.contains(aChild))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChild()) { index = numberOfChild() - 1; }
      child.remove(aChild);
      child.add(index, aChild);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addChildAt(aChild, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Admin placeholderAdmin = admin;
    this.admin = null;
    if(placeholderAdmin != null)
    {
      placeholderAdmin.removeGuardian(this);
    }
    ArrayList<Student> copyOfChild = new ArrayList<Student>(child);
    child.clear();
    for(Student aChild : copyOfChild)
    {
      aChild.removeGuardian(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "admin = "+(getAdmin()!=null?Integer.toHexString(System.identityHashCode(getAdmin())):"null");
  }
}