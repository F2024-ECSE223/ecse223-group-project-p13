package ca.mcgill.ecse.coolsupplies.features;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = {
  // Feature 1
  // "src/test/resources/AddParent.feature",
  // "src/test/resources/UpdateParent.feature",
  // "src/test/resources/DeleteParent.feature",
  // "src/test/resources/GetParent.feature",
  // "src/test/resources/UpdatePasswordOfSchoolAdmin.feature",

  // Feature 2
  // "src/test/resources/AddStudent.feature",
  // "src/test/resources/UpdateStudent.feature",
  // "src/test/resources/DeleteStudent.feature",
  // "src/test/resources/GetStudent.feature"
  
  // Feature 3
  // "src/test/resources/AddItem.feature",
  // "src/test/resources/UpdateItem.feature",
  // "src/test/resources/DeleteItem.feature",
  // "src/test/resources/GetItem.feature",

  // Feature 4
  // "src/test/resources/AddGradeBundle.feature",
  // "src/test/resources/UpdateGradeBundle.feature",
  // "src/test/resources/DeleteGradeBundle.feature",
  // "src/test/resources/GetGradeBundle.feature",

  // Feature 5
  // "src/test/resources/AddBundleItemToBundleItemsOfGradeBundle.feature",
  // "src/test/resources/UpdateBundleItemInBundleItemsOfGradeBundle.feature",
  // "src/test/resources/RemoveBundleItemFromBundleItemsOfGradeBundle.feature",
  // "src/test/resources/GetBundleItemFromBundleItemsOfGradeBundle.feature",

  // Feature 6
  // "src/test/resources/AddStudentToStudentsOfParent.feature",
  // "src/test/resources/RemoveStudentFromStudentsOfParent.feature",
  // "src/test/resources/GetStudentFromStudentsOfParent.feature"
  // "src/test/resources/AddOrder.feature",

  // Feature 7
  // "src/test/resources/AddGrade.feature",
  // "src/test/resources/DeleteGrade.feature",
  // "src/test/resources/GetGrade.feature",
  // "src/test/resources/UpdateGrade.feature"
},
    glue = "ca.mcgill.ecse.coolsupplies.features")
public class CucumberFeaturesTestRunner {

}
