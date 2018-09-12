import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class ShuntingYard {
    public static void main(String[] args) throws IOException
    {
        //Setting DecimalFormat for floating numbers in output
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);
        df.setRoundingMode(RoundingMode.HALF_UP);

        //Input of data
        Scanner scan = new Scanner(new File("input.txt"));
        String scannedArray = scan.nextLine();
        String[] array = splitLine(scannedArray);
        Double answer = null;

        //Computation
        LinkedStack stack = PolishNotation.reversePN(array);
        if(stack != null) stack = stack.reverse();
        if(stack != null) answer = PolishNotation.calculate(stack);

        //Output
        FileWriter file = new FileWriter(new File("output.txt"));
        if(answer != null) file.write(df.format(answer));
        else file.write("ERROR");
        file.flush();
        file.close();
    }

    /**
     * Split the expression into separate numbers and symbols
     * @param line string to be split
     * @return array of Strings
     */
    public static String[] splitLine(String line)
    {
        line = line.replace(" ","");
        LinkedList<String> list = new LinkedList<>(); //list to store values
        String number = "";

        for(int i = 0; i != line.length(); i++)
        {
            char c = line.charAt(i);
            if(c == '(' || c == ')' || PolishNotation.isOperation(c + ""))
            {
                if(!number.equals(""))
                {
                    list.add(number);
                    number = "";
                }
                list.add(c + "");
            }
            else
            {
                number += c;
            }
        }
        if(!number.isEmpty())list.add(number);  //if we did not consider last number

        //convert list into array
        String[] strings = new String[list.size()];
        int i = 0;
        for(String s: list)
        {
            strings[i] = s;
            i++;
        }

        return strings;
    }
}

/**
 * PolishNotation class implements methods to calculate math expressions.
 * Here we use Reverse Polish Notation Algorithm and Polish Notion Algorithm itself.
 * Also, we have two additional help methods
 * All methods are static so that there is no need to use objects.
 */
class PolishNotation
{
    /**
     * Polish Notation Algorithm
     * used to calculate result of math expression using preprocessed stack f elements
     * @param stack input data of numbers and operations in postfix form.
     * @return result of calculation of math expression
     */
    public static Double calculate(LinkedStack stack)
    {
        if(stack == null)   //exclude errors
            return null;

        LinkedStack answer = new LinkedStack(); //stack to contain numbers and final answer
        while(!stack.isEmpty())
        {
            String item = stack.pop();
            if(isOperation(item))
            {
                String first, second;
                if(!answer.isEmpty()) first = answer.pop();
                else return null;
                if(!answer.isEmpty()) second = answer.pop();
                else return null;

                //execute operation and push result back into stack
                if(item.equals("+"))
                    answer.push(Double.toString(Double.parseDouble(second) + Double.parseDouble(first)));
                else if(item.equals("-"))
                    answer.push(Double.toString(Double.parseDouble(second) - Double.parseDouble(first)));
                else if(item.equals("*"))
                    answer.push(Double.toString(Double.parseDouble(second) * Double.parseDouble(first)));
                else
                    answer.push(Double.toString(Double.parseDouble(second) / Double.parseDouble(first)));
            }
            else
                answer.push(item);
        }
        if(answer.size() == 1) return Double.parseDouble(answer.pop()); //return result of computation
        else return null;       //exclude errors
    }

    /**
     * Reverse Polish Notation Algorithm
     * Converts math expression into postfix form.
     * @param array expression in a form of array of strings.
     * @return computed postfix of math epression as a stack
     */
    public static LinkedStack reversePN(String[] array)
    {
        LinkedStack operations = new LinkedStack(); //stack for operations
        LinkedStack stack = new LinkedStack();      //stack for numbers and result
        for(int i = 0; i != array.length; i++)
        {
            if(array[i].equals("(")) //put '(' into stack of operations
            {
                operations.push(array[i]);
            }
            else if(array[i].equals(")"))   //put operations from stack into result stack until '('
            {
                while(true)
                {
                    if(operations.isEmpty())
                        return null;
                    if(operations.top().equals("("))
                        break;
                    stack.push(operations.pop());
                }
                operations.pop();           //remove '(' from stack
            }
            else if(isOperation(array[i]))  //according to priority of operations put them into result stack
            {
                while(!operations.isEmpty() && priority(operations.top()) >= priority(array[i]))
                {
                    stack.push(operations.pop());
                }
                operations.push(array[i]);
            }
            else        //put numbers into result stack
            {
                stack.push(array[i]);
            }
        }
        while(!operations.isEmpty())    //put remaining operations into result stack
        {
            if(operations.top().equals("(")) return null;   //exclude errors
            stack.push(operations.pop());
        }
        return stack;
    }

    /**
     * Private method used to identify priority of operations.
     * @param symbol symbol to be considered.
     * @return priority level of operation.
     */
    private static int priority(String symbol)
    {
        return symbol.equals("*") || symbol.equals("/") ?
                1 : symbol.equals("+") || symbol.equals("-") ? 0 : -1;
    }

    /**
     * Checks if the symbol is operation.
     * @param symbol symbol to be considered
     * @return true if symbol is operation, false otherwise.
     */
    public static boolean isOperation(String symbol)
    {
        return symbol.equals("+") || symbol.equals("-") ||
                symbol.equals("/") || symbol.equals("*");
    }
}
/**
 * LinkedStack is a stack with principle LIFO.
 * Its construction uses links:
 * Nodes that contain value and link to the next element of the stack.
 */
class LinkedStack {

    private class Node //Class-helper
    {
        String value;
        Node next;
    }

    private Node top;      //top Node of the stack
    private int size;      //the number of Nodes in the stack

    /**
     * Initializes an empty stack
     */
    public LinkedStack()
    {
        top = null;
        size = 0;
    }

    /**
     * Initializes a stack with initial Nodes in it
     * @param values Nodes to be included in a stack initially
     */
    public LinkedStack(String[] values)
    {
        top = null;
        size = 0;
        for(int i = 0; i != values.length; i++)
            push(values[i]);
    }

    /**
     * @param value the Node to add to the end of the stack
     */
    public void push(String value)
    {
        Node oldTop = top;
        top = new Node();
        top.value = value;
        top.next = oldTop;
        size++;
    }

    /**
     * @return the value of Node from the top of stack.
     * If the stack is empty it throws Exception.
     */
    public String top()
    {
        if(!isEmpty()) return top.value;
        else throw new NoSuchElementException("ERROR");
    }

    /**
     * @return the value of Node from the top of stack
     * and removes this Node
     * If the stack is empty it throws Exception.
     */
    public String pop()
    {
        if(!isEmpty())
        {
            Node oldTop = top;
            top = oldTop.next;
            size--;
            return oldTop.value;
        }
        else throw new NoSuchElementException("ERROR");
    }

    /**
     * Reverses order of elements in the stack
     * @return reversed stack.
     */
    public LinkedStack reverse()
    {
        LinkedStack stack = new LinkedStack();
        while(!this.isEmpty())
        {
            String item = this.pop();
            stack.push(item);
        }
        return stack;
    }

    /**
     * @return True if the stack is empty;
     * False otherwise
     */
    public boolean isEmpty()
    {
        return size() == 0;
    }

    /**
     * @return the number of Nodes in the stack
     */
    public int size()
    {
        return size;
    }
}