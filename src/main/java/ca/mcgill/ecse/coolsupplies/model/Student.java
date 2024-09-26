/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.util.*;

// line 42 "../../../../../coolsupplies.ump"
public class Student
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Student Attributes
  private String name;

  //Student Associations
  private List<Guardian> guardian;
  private Order order;
  private GradeLevel gradeLevel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Student(String aName, Order aOrder, GradeLevel aGradeLevel)
  {
    name = aName;
    guardian = new ArrayList<Guardian>();
    boolean didAddOrder = setOrder(aOrder);
    if (!didAddOrder)
    {
      throw new RuntimeException("Unable to create student due to order. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGradeLevel = setGradeLevel(aGradeLevel);
    if (!didAddGradeLevel)
    {
      throw new RuntimeException("Unable to create student due to gradeLevel. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public String getName()
  {
    return name;
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
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
  }
  /* Code from template association_GetOne */
  public GradeLevel getGradeLevel()
  {
    return gradeLevel;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGuardian()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addGuardian(Guardian aGuardian)
  {
    boolean wasAdded = false;
    if (guardian.contains(aGuardian)) { return false; }
    guardian.add(aGuardian);
    if (aGuardian.indexOfChild(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aGuardian.addChild(this);
      if (!wasAdded)
      {
        guardian.remove(aGuardian);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeGuardian(Guardian aGuardian)
  {
    boolean wasRemoved = false;
    if (!guardian.contains(aGuardian))
    {
      return wasRemoved;
    }

    int oldIndex = guardian.indexOf(aGuardian);
    guardian.remove(oldIndex);
    if (aGuardian.indexOfChild(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aGuardian.removeChild(this);
      if (!wasRemoved)
      {
        guardian.add(oldIndex,aGuardian);
      }
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
  /* Code from template association_SetOneToMany */
  public boolean setOrder(Order aOrder)
  {
    boolean wasSet = false;
    if (aOrder == null)
    {
      return wasSet;
    }

    Order existingOrder = order;
    order = aOrder;
    if (existingOrder != null && !existingOrder.equals(aOrder))
    {
      existingOrder.removeStudent(this);
    }
    order.addStudent(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGradeLevel(GradeLevel aGradeLevel)
  {
    boolean wasSet = false;
    if (aGradeLevel == null)
    {
      return wasSet;
    }

    GradeLevel existingGradeLevel = gradeLevel;
    gradeLevel = aGradeLevel;
    if (existingGradeLevel != null && !existingGradeLevel.equals(aGradeLevel))
    {
      existingGradeLevel.removeStudent(this);
    }
    gradeLevel.addStudent(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Guardian> copyOfGuardian = new ArrayList<Guardian>(guardian);
    guardian.clear();
    for(Guardian aGuardian : copyOfGuardian)
    {
      aGuardian.removeChild(this);
    }
    Order placeholderOrder = order;
    this.order = null;
    if(placeholderOrder != null)
    {
      placeholderOrder.removeStudent(this);
    }
    GradeLevel placeholderGradeLevel = gradeLevel;
    this.gradeLevel = null;
    if(placeholderGradeLevel != null)
    {
      placeholderGradeLevel.removeStudent(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "gradeLevel = "+(getGradeLevel()!=null?Integer.toHexString(System.identityHashCode(getGradeLevel())):"null");
  }
}