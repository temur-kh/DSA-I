/**
 * This generic class implements a node used in AVL Tree.
 * It extends class Node and has some extra features like parent, height, size.
 * @param <K> this is the type of the data stored in a key.
 */
public class AVLNode<K extends Comparable> extends Node
{
    //parent node
    AVLNode parent;
    //max height of a node starting from leaves
    int height;
    //number of nodes in a subtree with root in this node
    int size;

    public AVLNode(K key)
    {
        super(key);
        height = 0;
        size = 1;
    }

    /**
     * @return reference to the left node
     */
    public AVLNode getLeft()
    {
        return (AVLNode) left;
    }

    /**
     * @return reference to the right node
     */
    public AVLNode getRight()
    {
        return (AVLNode) right;
    }
}