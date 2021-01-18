package Logic.Validation;

public class PercentageValidation {
    
    public static boolean validatePercentage(int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            return true;
        }

        return false;
    }
}
