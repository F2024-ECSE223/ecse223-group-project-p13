/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.sql.Date;
import java.util.*;

// line 38 "../../../../../coolsupplies.ump"
public class School
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //School Attributes
  private Date startDate;
  private Date endDate;

  //School Associations
  private Admin admin;
  private List<GradeLevel> gradeLevel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public School(Date aStartDate, Date aEndDate, Admin aAdmin)
  {
    startDate = aStartDate;
    endDate = aEndDate;
    if (aAdmin == null || aAdmin.getSchool() != null)
    {
      throw new RuntimeException("Unable to create School due to aAdmin. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    admin = aAdmin;
    gradeLevel = new ArrayList<GradeLevel>();
  }

  public School(Date aStartDate, Date aEndDate, String aEmailForAdmin, String aPasswordForAdmin, Application aApplicationForAdmin)
  {
    startDate = aStartDate;
    endDate = aEndDate;
    admin = new Admin(aEmailForAdmin, aPasswordForAdmin, aApplicationForAdmin, this);
    gradeLevel = new ArrayList<GradeLevel>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }
  /* Code from template association_GetOne */
  public Admin getAdmin()
  {
    return admin;
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
  public GradeLevel addGradeLevel(int aIndex, String aLevelName, Bundle aBundle)
  {
    if (numberOfGradeLevel() >= maximumNumberOfGradeLevel())
    {
      return null;
    }
    else
    {
      return new GradeLevel(aIndex, aLevelName, this, aBundle);
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

    School existingSchool = aGradeLevel.getSchool();
    boolean isNewSchool = existingSchool != null && !this.equals(existingSchool);
    if (isNewSchool)
    {
      aGradeLevel.setSchool(this);
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
    //Unable to remove aGradeLevel, as it must always have a school
    if (!this.equals(aGradeLevel.getSchool()))
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
    Admin existingAdmin = admin;
    admin = null;
    if (existingAdmin != null)
    {
      existingAdmin.delete();
    }
    for(int i=gradeLevel.size(); i > 0; i--)
    {
      GradeLevel aGradeLevel = gradeLevel.get(i - 1);
      aGradeLevel.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "admin = "+(getAdmin()!=null?Integer.toHexString(System.identityHashCode(getAdmin())):"null");
  }
}