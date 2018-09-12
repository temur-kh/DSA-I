/**
 * This is the interface of tree structure.
 * @param <K> this is the type of the data stored in a tree.
 */
public interface ITree<K extends Comparable> {
    K find(K key);
    void insert(K key);
    void remove(K key);
    Iterable traverse();
    Iterable print() throws Exception;
    int size();
    boolean isEmpty();
}