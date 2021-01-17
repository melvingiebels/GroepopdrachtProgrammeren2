package Logic;

import java.sql.Date;

public class InputValidation {

    // Mail validation
    public static boolean validateMailAddress(String mailAddress) {
        if (!mailAddress.contains("@") || mailAddress.split("@")[0].length() < 1) {
            return false;
        }

        if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.").length > 2) {
            return false;
        }

        if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.")[0].length() < 1) {
            return false;
        }

        if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.", -1)[1].length() < 1) {
            return false;
        }

        return true;
    }

    // URL validation
    public static boolean validateUrl(String url) {
        if (!url.startsWith("https://") || url.startsWith("http://")) {
            return false;
        }

        if (url.split("/", 5)[1].split("\\.")[0].length() < 1) {
            return false;
        }

        if (url.split("\\.")[1].split("\\.")[0].length() < 1) {
            return false;
        }

        if (url.split("\\.")[1].split("\\.", -1)[1].length() < 1) {
            return false;
        }

        return true;
    }

    // Percentages validation
    public static boolean validatePercentage(int percentage) {
        if (percentage < 0 || percentage > 100) {
            return false;
        }

        return true;
    }

    // Date validation
    public static boolean validateDate(Date date) {

        return true;
    }

    // Certificate grade validation
    public static boolean validateGrade(double grade) {
        if (grade < 1 || grade > 10) {
            return false;
        }

        return true;
    }

    // Zip code/ Postal code validation
    public static boolean validatePostalCode(String zipCode) {
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

        // if (zipCode.length() != 6){
        // return false;
        // }
        // if (zipCode.charAt(0) > 0 && zipCode.charAt(0) < 11){
        // for (int i = 0; i < 4; i++) {
        // if (!Character.isDigit(zipCode.charAt(i))){
        // return false;
        // }
        // }
        // for (int i = 4; i < 6; i++) {
        // if (!Character.isLetter(zipCode.charAt(i))){
        // return false;
        // }
        // }
        // }

        return false;
    }
}