package Logic.Validation;

public class NameValidation {

    public static boolean validateName(String name) {
        if (name.matches("(?=^.{0,50}$)^[a-zA-Z-]+\s[a-zA-Z-]+$")) {
            return true;
        }
        return false;
    }
}
