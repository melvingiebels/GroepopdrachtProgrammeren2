package Logic.Validation;

public class DateValidation {

    public static boolean validateDate(String date) {

        // Check length
        if (date.length() < 10 || date.length() > 10) {
            return false;
        }

        String dateParts[] = date.split("-");

        if (dateParts.length < 3 || dateParts.length > 3) {
            return false;
        }

        int day = Integer.valueOf(dateParts[0]);
        int month = Integer.valueOf(dateParts[1]);
        int year = Integer.valueOf(dateParts[2]);
        // System.out.println("days: " + day + " month: " + month + " year: " + year);

        if (1 < month && month > 12) {
            return false;
        }

        // * @subcontract 31 days in month
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                && (day < 1 || day > 31)) {
            System.out.println("first");
            return false;
        }
        // * @subcontract 30 days in month
        if ((month == 4 || month == 6 || month == 9 || month == 11) && (day < 1 || day > 30)) {
            System.out.println("second");
            return false;
        }
        // * @subcontract 29 days in month
        if (month == 2 && (day < 1 || day > 29) && year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            System.out.println("third");
            return false;
        }
        // * @subcontract 28 days in month
        if (month == 2 && (day < 1 || day > 28) && (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0))) {
            System.out.println("fourth");
            return false;
        }

        return true;
    }
}
