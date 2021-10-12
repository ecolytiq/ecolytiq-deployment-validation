import java.util.Base64;

public class HtmlParser {

    /**
     * Converts the information of an external service or down service into a string that can be interpreted as a ciid
     * @param name the name of the service
     * @param url the url of the service
     * @return A string with x as the service number, the name as the service name and url as additional information encoded in base64Url
     */
    public static String externalServiceToStringParser(String name, String url)
    {
        if (name.equals("") || url.equals(""))
            return null;
        String base64Url = Base64.getUrlEncoder().encodeToString(url.getBytes());
        return name + "/x/"+base64Url+"%-1s";
    }

    /**
     * Converts the information of an external service or down service into a ciid
     * @param name the name of the service
     * @param url the url of the service
     * @return A ciid containing the information of the service
     */
    public static Ciid externalServiceToCiidParser(String name, String url)
    {
        if (name.equals("") || url.equals(""))
            return null;
        String ciidString = externalServiceToStringParser(name, url);
        Ciid ciid = new Ciid(ciidString);
        return ciid;
    }
}
