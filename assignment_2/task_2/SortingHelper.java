/**
 * Class SotringHelper is extendable by other sorting classes
 * to use function createIds and some constants which is specific for all of them
 */
public class SortingHelper
{

    //booleans to represent whether item is char or integer
    public final boolean isChar = true;
    public final boolean isInteger = false;

    /**
     * Creates an array of booleans containing data whether an item is char or integer
     * @param array an array of Strings, each being either char or integer
     * @return an array of booleans
     */
    public Boolean[] createIds(String[] array)
    {
        Boolean[] ids = new Boolean[array.length];
        for(int i = 0; i != array.length; i++)
        {
            ids[i] = ((array[i].length() == 1) && Character.isLetter(array[i].charAt(0))) ? isChar : isInteger;
        }
        return ids;
    }

    /**
     * Converts the input data into comparable type: Integer or Character
     * @param line element to be converted properly
     * @return comparable element (Integer or Character)
     */
    public Comparable convert(String line)
    {
        if(line.length() == 1 && Character.isLetter(line.charAt(0)))
            return line.charAt(0);
        else
            return Integer.parseInt(line);
    }
}