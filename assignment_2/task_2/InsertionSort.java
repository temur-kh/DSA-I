/**
 * Class InsertionSort is used to sort an array of data (integers+chars)
 * using Insertion Sort algorithm.
 * Contains one function sort() returning sorted data
 */
public class InsertionSort extends SortingHelper
{
    public InsertionSort(){}

    /**
     * Sorts given array of data (integers+chars) using Insertion Sort.
     * IDs are booleans representing whether the element of array is char or integer
     * @param line String containing data to be sorted
     * @return String containing sorted data
     */
    public String sort(String line)
    {
        //Split data and create IDs of elements: integer or char
        String[] array = line.split(" ");
        Boolean[] ids = createIds(array);

        for(int i = 0; i != array.length; i++)
        {
            //data of current element to be moved
            String current = array[i];
            Boolean currentId = ids[i];

            //index representing next position for moving to
            int next = i;

            for(int j = i - 1; j >= 0; j--)
            {
                //consider only elements of the same ID like current
                if(currentId == ids[j])
                {
                    //if current element is smaller than taken element,
                    // move taken element right, otherwise break
                    if(convert(array[j]).compareTo(convert(current)) <= 0)
                        break;
                    else
                    {
                        array[next] = array[j];
                        next = j;
                    }
                }
            }

            //put current element on new position
            array[next] = current;
        }

        //create output and return it
        line = "";
        for(int i = 0; i != array.length; i++)
        {
            line += array[i] + " ";
        }
        return line;
    }
}