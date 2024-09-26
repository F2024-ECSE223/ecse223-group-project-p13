/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;

// line 9 "../../../../../coolsupplies.ump"
public abstract class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String email;
  private String password;

  //User Associations
  private Application application;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aEmail, String aPassword, Application aApplication)
  {
    email = aEmail;
    password = aPassword;
    boolean didAddApplication = setApplication(aApplication);
    if (!didAddApplication)
    {
      throw new RuntimeException("Unable to create user due to application. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
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

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public Application getApplication()
  {
    return application;
  }
  /* Code from template association_SetOneToMany */
  public boolean setApplication(Application aApplication)
  {
    boolean wasSet = false;
    if (aApplication == null)
    {
      return wasSet;
    }

    Application existingApplication = application;
    application = aApplication;
    if (existingApplication != null && !existingApplication.equals(aApplication))
    {
      existingApplication.removeUser(this);
    }
    application.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Application placeholderApplication = application;
    this.application = null;
    if(placeholderApplication != null)
    {
      placeholderApplication.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "application = "+(getApplication()!=null?Integer.toHexString(System.identityHashCode(getApplication())):"null");
  }
}