package Logic.Validation;

public class UrlValidation {
    
    public static boolean validateUrl(String url) {

        System.out.println(url);
        String[] parts = url.split("\\.");

        if (parts.length != 3){
            return false;
        }

        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            System.out.println("no protocol");
            return false;
        }
        // "https://.website.com" geeft: index  1 out of bounds for length 1
        if ((url.startsWith("https://") && parts[0].length() < 9) || (url.startsWith("http://") && parts[0].length() < 8)) {
            System.out.println("no subdomain");
            return false;
        }

        if (parts[1].length() < 1) {
            System.out.println("no domain name");
            return false;
        }

        if (parts[2].length() < 1) {
            System.out.println("no tld");
            return false;
        }

        return true;       
    }
}
    // System.out.println(url);
        // if (url.startsWith("https://") || url.startsWith("http://")){
        //     System.out.println("yes");
        // }        
        // for (String part : parts){
        //     System.out.println(part);
        // }
        // System.out.println("");
