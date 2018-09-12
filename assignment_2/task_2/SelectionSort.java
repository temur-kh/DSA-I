/**
 * Class SelectionSort is used to sort an array of data (integers+chars)
 * using Selection Sort algorithm.
 * Contains one function sort() returning sorted data
 */
public class SelectionSort extends SortingHelper
{
    public SelectionSort(){}

    /**
     * Sorts given array of data (integers+chars) using Selection Sort.
     * IDs are booleans representing whether the element of array is char or integer
     * @param line String containing data to be sorted
     * @return String containing sorted data
     */
    public String sort(String line)
    {
        //Split data and create IDs of elements: integer or char
        String[] array = line.split(" ");
        Boolean[] ids = createIds(array);

        for(int i = 0; i < array.length; i++)
        {
            //data of current minimal element
            String currentMin = array[i];
            Boolean currentId = ids[i];
            int indexMin = i;

            for(int j = i + 1; j < array.length; j++)
            {
                if(ids[j] == currentId)
                {
                    //compare with minimal element
                    if(convert(array[j]).compareTo(convert(currentMin)) < 0)
                    {
                        currentMin = array[j];
                        indexMin = j;
                    }
                }
            }

            //swap current element with minimal found element
            if(indexMin != i)
            {
                String tmp = array[i];
                array[i] = array[indexMin];
                array[indexMin] = tmp;
            }
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