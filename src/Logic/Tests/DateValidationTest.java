package Logic.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Logic.Validation.DateValidation;


public class DateValidationTest {
    // @subcontract == 31 days in month    
    @Test
    public void testValidateDateRequiresMonth1And31DaysEnsuresTrue() {
        // Arrange
        String date = "31-01-2019";

        // Act
        Boolean result = DateValidation.validateDate(date);

        // Assert
        assertTrue("31 days in given month", result);
    }  
    
    // @subcontract > 31 days in month 
    @Test
    public void testValidateDateRequiresMonth1And32DaysEnsuresFalse(){
        // Arrange
        String date = "32-01-2019";

        // Act
        Boolean result = DateValidation.validateDate(date);

        // Assert
        assertFalse("Month contains more than 31 day", result);
    }

    // @subcontract == 30 days in month
    @Test
    public void testValidateDateRequiresMonth4And30DaysEnsuresTrue() {
        // Arrange
        String date = "30-04-2020";

        // Act
        Boolean result = DateValidation.validateDate(date);

        // Assert
        assertTrue("Month can only contain a maximum of 30 days", result);
    }

    // @ Subcontract > 30 days in month
    @Test
    public void testValidateDateRequiresMonth4And31DaysEnsuresFalse() {
        // Arrange
        String date = "31-04-2020";

        // Act
        Boolean result = DateValidation.validateDate(date);

        // Assert
        assertFalse("Month can only contains a maximum of 30 days", result);
    }


    // * @subcontract == 29 days in month
    @Test
    public void testValidateDateRequiresMonth2And29DaysAndYear2012EnsuresTrue() {
        // Arrange
        String date = "29-02-2012";

        // Act
        Boolean result = DateValidation.validateDate(date);

        // Assert
        assertTrue("Month does not contain a maximum of 29 days", result);
    }
    
    // * @subcontract > 29 days in month
    @Test
    public void testValidateDateRequiresMonth2And30DaysAndYear2012EnsuresFalse() {
        // Arrange
        String date = "30-02-2012";

        // Act
        Boolean result = DateValidation.validateDate(date);

        // Assert
        assertFalse("Month contains a maximum of 29 days", result);
    }

    //* @subcontract == 28 days in month
    @Test
    public void testValidateDateRequiresMonth2And28DaysAndYear2010EnsuresTrue() {
        // Arrange
        String date = "28-02-2010";

        // Act
        Boolean result = DateValidation.validateDate(date);

        // Assert
        assertTrue("No Leap year can only contains a maximum of 28", result);
    }

    //* @subcontract > 28 days in month
    @Test
    public void testValidateDateRequiresMonth2And29DaysAndYear2010EnsuresFalse() {
        // Arrange
        String date = "29-02-2010";

        // Act
        Boolean result = DateValidation.validateDate(date);

        // Assert
        assertFalse("No Leap year can only contains a maximum of 28", result);
    }
}
