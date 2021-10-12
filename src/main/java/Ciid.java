import java.util.ArrayList;
import java.util.List;

public class Ciid {

    public List<Ciid> ciids;
    public Miid miid;

    public Ciid(String id)
    {
        List<Object> parsedValues =  parseCiid(id);
        if(parsedValues.size() > 0)
            this.miid = (Miid) parsedValues.get(0);
        if (parsedValues.size() > 1)
            this.ciids = (List<Ciid>) parsedValues.get(1);
    }

    /**
     * Gets a string with the textual information about this CIID an extract all properties
     * @param id The string representation of this CIID
     * @return Returns a list where the first entry is the list of all other CIIDs and the second is the MIID
     */
    public List<Object> parseCiid(String id)
    {
        List<Object> list = new ArrayList<>();

        String[] nameArgArray = separateNameFromArg(id);
        String name = nameArgArray[0];
        String arg = nameArgArray[1];

        Miid miid = new Miid(name);
        if(miid.sn != null)
            list.add(miid);
        if(!arg.equals("")) {
            List<Ciid> children = parseArguments(arg);
            if(children.get(0).miid != null)
                list.add(children);
        }
        return list;
    }

    /**
     * Separates the first miid and the following ciids
     * @param id The id string
     * @return A string array with the miid as the first entry and the ciids as second
     */
    public String[] separateNameFromArg(String id) {
        int splitIndex = id.length();
        if (id.contains("("))
            splitIndex = id.indexOf("(");
        String[] ret = new String[] {id.substring(0, splitIndex), id.substring(splitIndex)};
        return ret;
    }

    /**
     * Process the ciid call string. An creates a list out of them.
     * @param arg The ciid string after the miid was cut off
     * @return A list of ciids
     */
    public List<Ciid> parseArguments(String arg) {
        List<Ciid> ret = new ArrayList<>();

        //delete ( and ) at the beginning and end
        arg = arg.substring(1, arg.length()-1);

        String[] uids = spiltOnPlus(arg).toArray(new String[0]);

        for (String uid: uids) {
            ret.add(new Ciid(uid));
        }
        return ret;
    }

    /**
     * Splits a string on + if the plus occurs before a (.
     * @param s A string to parse
     * @return If the string contains a plus before a bracket it is split there which is the return value. Else the string itself will be returned.
     */
    public List<String> spiltOnPlus(String s) {

        int plusPosition = -1;
        int bracketPosition = Integer.MAX_VALUE;
        List<String> ret = new ArrayList<>();

        if(s.contains("+"))
            plusPosition = s.indexOf("+");
        else {
            ret.add(s);
            return ret;
        }
        if(s.contains("("))
            bracketPosition = s.indexOf("(");

        if(plusPosition < bracketPosition)
        {
            String workingString = s.substring(plusPosition+1);
            ret.add(s.substring(0, plusPosition));
            ret.addAll(spiltOnPlus(workingString));
            return ret;
        }
        ret.add(s);
        return ret;
    }

    @Override
    public String toString()
    {
        String ret = this.miid.toString();
        if (ciids != null && ciids.size() == 1)
        {
            return ret.concat("("+ciids.get(0).toString()+")");
        } else if (ciids != null && ciids.size() > 1)
        {
            ret = ret.concat("(");
            String ciidsString = ciids.get(0).toString();
            for (int i = 1; i< ciids.size(); i++)
            {
                ciidsString = ciidsString.concat("+" + ciids.get(i).toString());
            }
            ret = ret.concat(ciidsString);
            ret = ret.concat(")");
        }
        return ret;
    }

}
