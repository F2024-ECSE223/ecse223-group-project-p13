/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.util.*;

// line 46 "../../../../../coolsupplies.ump"
public class GradeLevel
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GradeLevel Attributes
  private int index;
  private String levelName;

  //GradeLevel Associations
  private School school;
  private List<Student> student;
  private Bundle bundle;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GradeLevel(int aIndex, String aLevelName, School aSchool, Bundle aBundle)
  {
    index = aIndex;
    levelName = aLevelName;
    boolean didAddSchool = setSchool(aSchool);
    if (!didAddSchool)
    {
      throw new RuntimeException("Unable to create gradeLevel due to school. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    student = new ArrayList<Student>();
    boolean didAddBundle = setBundle(aBundle);
    if (!didAddBundle)
    {
      throw new RuntimeException("Unable to create gradeLevel due to bundle. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIndex(int aIndex)
  {
    boolean wasSet = false;
    index = aIndex;
    wasSet = true;
    return wasSet;
  }

  public boolean setLevelName(String aLevelName)
  {
    boolean wasSet = false;
    levelName = aLevelName;
    wasSet = true;
    return wasSet;
  }

  public int getIndex()
  {
    return index;
  }

  public String getLevelName()
  {
    return levelName;
  }
  /* Code from template association_GetOne */
  public School getSchool()
  {
    return school;
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
  /* Code from template association_GetOne */
  public Bundle getBundle()
  {
    return bundle;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setSchool(School aSchool)
  {
    boolean wasSet = false;
    //Must provide school to gradeLevel
    if (aSchool == null)
    {
      return wasSet;
    }

    //school already at maximum (12)
    if (aSchool.numberOfGradeLevel() >= School.maximumNumberOfGradeLevel())
    {
      return wasSet;
    }
    
    School existingSchool = school;
    school = aSchool;
    if (existingSchool != null && !existingSchool.equals(aSchool))
    {
      boolean didRemove = existingSchool.removeGradeLevel(this);
      if (!didRemove)
      {
        school = existingSchool;
        return wasSet;
      }
    }
    school.addGradeLevel(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfStudent()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Student addStudent(String aName, Order aOrder)
  {
    return new Student(aName, aOrder, this);
  }

  public boolean addStudent(Student aStudent)
  {
    boolean wasAdded = false;
    if (student.contains(aStudent)) { return false; }
    GradeLevel existingGradeLevel = aStudent.getGradeLevel();
    boolean isNewGradeLevel = existingGradeLevel != null && !this.equals(existingGradeLevel);
    if (isNewGradeLevel)
    {
      aStudent.setGradeLevel(this);
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
    //Unable to remove aStudent, as it must always have a gradeLevel
    if (!this.equals(aStudent.getGradeLevel()))
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
  /* Code from template association_SetOneToAtMostN */
  public boolean setBundle(Bundle aBundle)
  {
    boolean wasSet = false;
    //Must provide bundle to gradeLevel
    if (aBundle == null)
    {
      return wasSet;
    }

    //bundle already at maximum (12)
    if (aBundle.numberOfGradeLevel() >= Bundle.maximumNumberOfGradeLevel())
    {
      return wasSet;
    }
    
    Bundle existingBundle = bundle;
    bundle = aBundle;
    if (existingBundle != null && !existingBundle.equals(aBundle))
    {
      boolean didRemove = existingBundle.removeGradeLevel(this);
      if (!didRemove)
      {
        bundle = existingBundle;
        return wasSet;
      }
    }
    bundle.addGradeLevel(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    School placeholderSchool = school;
    this.school = null;
    if(placeholderSchool != null)
    {
      placeholderSchool.removeGradeLevel(this);
    }
    for(int i=student.size(); i > 0; i--)
    {
      Student aStudent = student.get(i - 1);
      aStudent.delete();
    }
    Bundle placeholderBundle = bundle;
    this.bundle = null;
    if(placeholderBundle != null)
    {
      placeholderBundle.removeGradeLevel(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "index" + ":" + getIndex()+ "," +
            "levelName" + ":" + getLevelName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "school = "+(getSchool()!=null?Integer.toHexString(System.identityHashCode(getSchool())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bundle = "+(getBundle()!=null?Integer.toHexString(System.identityHashCode(getBundle())):"null");
  }
}