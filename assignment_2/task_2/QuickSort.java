/**
 * Class QuickSort is used to sort an array of data (integers+chars)
 * using Quick Sort algorithm.
 * Contains one public function sort() returning sorted data and two private methods
 */
public class QuickSort extends SortingHelper
{
    //IDs and the data itself
    private Boolean[] ids;
    private String[] array;

    public QuickSort(){}

    /**
     * Sorts given array of data (integers+chars) using Quick Sort.
     * IDs are booleans representing whether the element of array is char or integer
     * @param line String containing data to be sorted
     * @return String containing sorted data
     */
    public String sort(String line)
    {
        //Split data and create IDs of elements: integer or char
        array = line.split(" ");
        ids = createIds(array);

        //call sorting method for chars and integers
        sorting(0,array.length-1,isChar);
        sorting(0,array.length-1,isInteger);

        //create output and return it
        line = "";
        for(int i = 0; i != array.length; i++)
        {
            line += array[i] + " ";
        }
        return line;
    }

    /**
     Recursive method implementing sorting by Divide-and-Conquer strategy
     * In Quick Sort algorithm the pivot is usually taken randomly or from the middle.
     * For this implementation the pivot element is taken from the beginning of sub-array.
     * @param left left border of current sub-array
     * @param right right border of current sub-array
     * @param type type of data we watch
     */
    private void sorting(int left, int right, boolean type)
    {
        if(left >= right)
            return;

        //find the index of first element f specific type (ID)
        int i = indexOfFirstElement(left, right, type), j = right;
        int emptyPosition = i;

        //if there are some elements of this type
        if(i < j)
        {
            //take first found element as pivot
            String pivot = array[emptyPosition];
            while(i < j)
            {
                //find an element smaller than pivot from right
                while(emptyPosition < j && (ids[j] != type || convert(pivot).compareTo(convert(array[j])) <= 0))
                {
                    j--;
                }
                //put it in empty position
                array[emptyPosition] = array[j];
                emptyPosition = j;

                //find an element bigger than pivot from left
                while(i < emptyPosition && (ids[i] != type || convert(pivot).compareTo(convert(array[i])) >= 0))
                {
                    i++;
                }
                //put it in empty position
                array[emptyPosition] = array[i];
                emptyPosition = i;
            }

            //put pivot into new position
            array[emptyPosition] = pivot;

            //recursively call method for sub-arrays
            sorting(left, emptyPosition - 1, type);
            sorting(emptyPosition + 1, right, type);
        }
    }

    /**
     * Finds first element of specific type in given sub-array
     * @param start left border of sub-array
     * @param end right border of sub-array
     * @param type type of element to be found
     * @return index of found element, if there's no such element return array.length
     */
    private int indexOfFirstElement(int start, int end, boolean type)
    {
        int i;
        for(i = start; i <= end; i++)
        {
            if(ids[i] == type)
                return i;
        }
        return array.length;
    }
}