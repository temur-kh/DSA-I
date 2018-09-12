/**
 * Class MergeSort is used to sort an array of data (integers+chars)
 * using Merge Sort algorithm.
 * Contains one public function sort() returning sorted data and two private methods
 */
public class MergeSort extends SortingHelper
{
    //IDs and the data itself
    private Boolean[] ids;
    private String[] array;

    public MergeSort(){}

    /**
     * Sorts given array of data (integers+chars) using Merge Sort.
     * IDs are booleans representing whether the element of array is char or integer
     * @param line String containing data to be sorted
     * @return String containing sorted data
     */
    public String sort(String line)
    {
        //Split data and create IDs of elements: integer or char
        array = line.split(" ");
        ids = createIds(array);

        //call sorting method
        sorting(0,array.length - 1);

        //create output and return it
        line = "";
        for(int i = 0; i != array.length; i++)
        {
            line += array[i] + " ";
        }
        return line;
    }

    /**
     * Recursive method implementing sorting by Divide-and-Conquer strategy
     * @param left left border of current sub-array
     * @param right right border of current sub-array
     */
    private void sorting(int left, int right)
    {
        //go if the length of sub-array is more than 1
        if(left < right)
        {
            int middle = (left + right) / 2;

            //sort left and right halves of sub-array
            sorting(left, middle);
            sorting(middle + 1, right);

            //merge both halves together
            merge(left, right);
        }
    }

    /**
     * Method merges two sorted sub-arrays into one sorted array
     * In order to go through two sub-arrays containing elements of two types,
     * method uses two pointer for each sub-array for iterating.
     * @param left left border of current array
     * @param right right border of current array
     */
    private void merge(int left, int right)
    {
        //calculate middle element, sizes of both sub-arrays
        int middle = (left + right) / 2;
        int sizeLeft = middle - left + 1;
        int sizeRight = right - middle;

        //create extra memory to be used
        String[] leftArray = new String[sizeLeft];
        String[] rightArray = new String[sizeRight];

        //move data into extra arrays
        for(int i = 0; i != sizeLeft; i++)
        {
            leftArray[i] = array[left + i];
        }
        for(int i = 0; i != sizeRight; i++)
        {
            rightArray[i] = array[middle + 1 + i];
        }

        //two pointer for left sub-array (one for integer elements, one for char)
        //two pointer for right sub-array
        int charPointerLeft = 0, charPointerRight = 0;
        int intPointerLeft = 0, intPointerRight = 0;

        //current position in final array
        int currentPosition = left;

        while(currentPosition < (left + sizeLeft+sizeRight))
        {
            if(ids[currentPosition] == isChar)
            {
                //find first coming char from both sub-arrays
                while(charPointerLeft < sizeLeft && ids[left + charPointerLeft] != isChar)
                    charPointerLeft++;
                while(charPointerRight < sizeRight && ids[middle + 1 + charPointerRight] != isChar)
                    charPointerRight++;

                //didn't find char in left sub-array
                if(charPointerLeft == sizeLeft)
                {
                    array[currentPosition] = rightArray[charPointerRight];
                    charPointerRight++;
                }
                //didn't find char in right sub-array
                else if(charPointerRight == sizeRight)
                {
                    array[currentPosition] = leftArray[charPointerLeft];
                    charPointerLeft++;
                }
                //found two chars
                else
                {
                    //compare and put smaller one in current position
                    if(convert(leftArray[charPointerLeft]).compareTo(convert(rightArray[charPointerRight])) < 0)
                    {
                        array[currentPosition] = leftArray[charPointerLeft];
                        charPointerLeft++;
                    }
                    else
                    {
                        array[currentPosition] = rightArray[charPointerRight];
                        charPointerRight++;
                    }
                }
            }
            else
            {
                //find first coming integers from both sub-arrays
                while(intPointerLeft < sizeLeft && ids[left + intPointerLeft] != isInteger)
                    intPointerLeft++;
                while(intPointerRight < sizeRight && ids[middle + 1 + intPointerRight] != isInteger)
                    intPointerRight++;

                //didn't find integer in left sub-array
                if(intPointerLeft == sizeLeft)
                {
                    array[currentPosition] = rightArray[intPointerRight];
                    intPointerRight++;
                }
                //didn't find integer in right sub-array
                else if(intPointerRight == sizeRight)
                {
                    array[currentPosition] = leftArray[intPointerLeft];
                    intPointerLeft++;
                }
                //found two integers
                else
                {
                    //compare and put smaller one in current position
                    if(convert(leftArray[intPointerLeft]).compareTo(convert(rightArray[intPointerRight])) < 0)
                    {
                        array[currentPosition] = leftArray[intPointerLeft];
                        intPointerLeft++;
                    }
                    else
                    {
                        array[currentPosition] = rightArray[intPointerRight];
                        intPointerRight++;
                    }
                }
            }
            currentPosition++;
        }
    }
}