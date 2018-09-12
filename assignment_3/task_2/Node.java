/**
 * This generic class implements a node.
 * It contains a key and references to the left and right nodes.
 * Also, it has a variable amount for the cases of key repetitions.
 * @param <K> this is the type of the data stored in a key.
 */
public class Node<K extends Comparable>
{
    K key;
    Node left;
    Node right;
    int amount;
    Node(K key)
    {
        this.key = key;
        amount = 1;
        left = null;
        right = null;
    }
}