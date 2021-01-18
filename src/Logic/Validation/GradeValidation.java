package Logic.Validation;

public class GradeValidation {
    
    public static boolean validateGrade(double grade) {
        if (grade >= 1 && grade <= 10) {
            return true;
        }

        return false;
    }
}
