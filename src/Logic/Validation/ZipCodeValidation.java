package Logic.Validation;

public class ZipCodeValidation {

    public static boolean validateZipCode(String zipCode) {
        // null zipCode
        if (zipCode.equals(null)) {
            return false;
        }

        // valid zipCode
        try {
            int zipCodeNumbers = Integer.valueOf(zipCode.trim().substring(0, 4));
            String zipCodeLetters = zipCode.trim().substring(4).trim();
            char zipCodeFirstLetter = zipCode.trim().substring(4).trim().toUpperCase().charAt(0);
            char zipCodeSecondLetter = zipCode.trim().substring(4).trim().toUpperCase().charAt(1);

            if (zipCodeNumbers > 999 && zipCodeNumbers <= 9999 && zipCodeLetters.length() == 2
                    && ('A' <= zipCodeFirstLetter && zipCodeFirstLetter <= 'Z')
                    && ('A' <= zipCodeSecondLetter && zipCodeSecondLetter <= 'Z')) {
                // If zipCode is valid, return true
                return true;
            }

        } catch (Exception e) {
            // invalid zipCode
            return false;
        }
        return false;
    }

    public static String formatZipCode(String zipCode) {
        // Check if zipCode is already formatted
        String zipCodeConcat = zipCode.replaceAll("\\W", "");

        String zipCodeLetters = zipCodeConcat.trim().substring(4).trim();
        String validation = zipCodeConcat.trim().substring(4).trim().toUpperCase();

        if (!zipCode.contains(" ") || (!zipCodeLetters.equals(validation))) {
            return zipCodeConcat.substring(0, 4) + " " + validation;
        }
        return zipCode;
    }
}
