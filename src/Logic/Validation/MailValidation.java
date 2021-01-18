package Logic.Validation;

public class MailValidation {
    
    public static boolean validateMailAddress(String mailAddress) {

        String[] parts = mailAddress.split("@");

        // contains no @ or dot
        if (!mailAddress.contains("@") || !mailAddress.contains(".")) {
            return false;
        }
        // no mailbox part
        if (parts[0].length() < 1) {
            return false;
        }
        // subdomain-tld delimiter                         
        if (parts[1].split("\\.").length > 2) {
            return false;
        }
        // no subdomain part 
        if (parts[1].split("\\.")[0].length() < 1) {
            return false;
        }
        // no tld part 
        if (parts[1].split("\\.", -1)[1].length() < 1) {
            return false;
        }
        
        return true;
    }
}
