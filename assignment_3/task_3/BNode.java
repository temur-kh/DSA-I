import java.util.LinkedList;

/**
 * This generic class implements a node for B-Tree.
 * It contains a list of keys of class Key.
 */
public class BNode
{
    LinkedList<Key> keys;

    //maximal height of node from leaves
    int height;
    //number of keys in a subtree with root-node of this node
    int size;

    //Constructors
    public BNode()
    {
        keys = new LinkedList<>();
        height = 0;
        size = 0;
    }
    public BNode(Key key)
    {
        keys = new LinkedList<>();
        keys.add(key);
        height = 0;
        size = 1;
    }

    /**
     * @param index
     * @return a key from position index.
     */
    public Key getKey(int index)
    {
        return keys.get(index);
    }

    /**
     * @return the last key
     */
    public Key getLastKey()
    {
        return keys.getLast();
    }

    /**
     * @return the first key
     */
    public Key getFirstKey()
    {
        return keys.getFirst();
    }

    /**
     * @return a string containing all keys of the node
     */
    public String toString()
    {
        String line = "";
        for(int i = 0; i != keys.size(); i++)
        {
            for(int j = 0; j != keys.get(i).amount; j++)
            {
                if(i == keys.size() - 1 && j == keys.get(i).amount - 1)
                    line += keys.get(i).key.toString();
                else
                    line += keys.get(i).key.toString() + " ";
            }
        }
        return line;
    }
}