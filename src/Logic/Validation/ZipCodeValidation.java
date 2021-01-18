package Logic.Validation;

public class ZipCodeValidation {

    public static boolean validateZipCode(String zipCode) {
        // @subcontract null postalCode
        if (zipCode.equals(null)) {
            return false;
        }

        // @subcontract valid postalCode
        try {
            int zipCodeNumbers = Integer.valueOf(zipCode.trim().substring(0, 4));
            String zipCodeLetters = zipCode.trim().substring(4).trim();
            char zipCodeFirstLetter = zipCode.trim().substring(4).trim().toUpperCase().charAt(0);
            char zipCodeSecondLetter = zipCode.trim().substring(4).trim().toUpperCase().charAt(1);

            if (zipCodeNumbers > 999 && zipCodeNumbers <= 9999 && zipCodeLetters.length() == 2
                    && ('A' <= zipCodeFirstLetter && zipCodeFirstLetter <= 'Z')
                    && ('A' <= zipCodeSecondLetter && zipCodeSecondLetter <= 'Z')) {
                // If postalcode is valid, return true
                return true;
            }

        } catch (Exception e) {
            // @subcontract invalid postalCode
            return false;
        }
        return false;
    }
}