package Logic.Validation;

public class UrlValidation {
    
    public static boolean validateUrl(String url) {

        String[] parts = url.split("\\.");

        // System.out.println(url);
        // if (url.startsWith("https://") || url.startsWith("http://")){
        //     System.out.println("yes");
        // }        
        // for (String part : parts){
        //     System.out.println(part);
        // }
        // System.out.println("");

        if (!url.startsWith("https://") || !url.startsWith("http://")) {
            return false;
        }

        if (parts[0].split("//")[1].length() < 1) {
            return false;
        }

        if (parts[1].length() < 1) {
            return false;
        }

        if (parts[2].length() < 1) {
            return false;
        }

        return true;
    }
}
