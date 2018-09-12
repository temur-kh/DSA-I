/**
 * Class BubbleSort is used to sort an array of data (integers+chars)
 * using Bubble Sort algorithm.
 * Contains one function sort() returning sorted data
 */
public class BubbleSort extends SortingHelper
{
    public BubbleSort(){}

    /**
     * Sorts given array of data (integers+chars) using Bubble Sort.
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
            //leftId and previous will be used to refer left swapping element
            Boolean leftId = null;
            int previous = -1;

            //check the ID of current last position
            Boolean lastElementId = ids[array.length - 1 - i];

            for(int j = 0; j != array.length - i; j++)
            {
                //if didn't take left swapping element, take it
                if(previous == -1 && ids[j] == lastElementId)
                {
                    leftId = ids[j];
                    previous = j;
                }
                //if found an element of the same ID like left swapping element
                else if(previous != -1 && leftId == ids[j])
                {
                    //if right swapping element is smaller than left swapping one, swap them
                    if(convert(array[previous]).compareTo(convert(array[j])) > 0)
                    {
                        String tmp = array[previous];
                        array[previous] = array[j];
                        array[j] = tmp;
                    }

                    //new left swapping element
                    previous = j;
                }
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