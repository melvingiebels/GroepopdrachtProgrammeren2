package Logic.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import Logic.Validation.GradeValidation;


public class GradeValidationTest {
    
    @Test
    public void testValidateGradeis0EnsuresFalse(){
        // Arrange
        double grade = 0;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertFalse("Grade is 0", result);
    }

    @Test
    public void testValidateGradeis1EnsuresTrue(){
        // Arrange
        double grade = 1;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertTrue("Grade is 1", result);
    }

    @Test
    public void testValidateGradeis5EnsuresTrue(){
        // Arrange
        double grade = 5;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertTrue("Grade is 5", result);
    }

    @Test
    public void testValidateGradeis10EnsuresTrue(){
        // Arrange
        double grade = 10;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertTrue("Grade is 10", result);
    }


    @Test
    public void testValidateGradeIs11EnsuresFalse(){
        // Arrange
        double grade = 11;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertFalse("Grade is 11", result);
    }
}
