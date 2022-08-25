package Util;

public class StringUtil {
    public static String hashPassword(String strInput)
    {
    	String generatedSecuredPasswordHash = Asset.BCrypt.hashpw(strInput, Asset.BCrypt.gensalt(12));
        return generatedSecuredPasswordHash;
    }
    
    public static boolean checkPassword(String strInput, String hashedPassword) {
        boolean matched = Asset.BCrypt.checkpw(strInput, hashedPassword);
        return matched;
    }
}
