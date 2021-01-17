package Logic.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import Logic.InputValidation;


public class GradeValidationTest {
    
    @Test
    public void testValidateGradeisLessThen1EnsuresFalse(){
        // Arrange
        double grade = 0;

        // Act
        Boolean result = InputValidation.validateGrade(grade);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testValidateGradeIsGreaterThen10EnsuresFalse(){
        // Arrange
        double grade = 11;

        // Act
        Boolean result = InputValidation.validateGrade(grade);

        // Assert
        assertEquals(false, result);
    }
}
