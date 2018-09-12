/**
 * Class Entry is used to store pairs of elements (key,value)
 * @param <K> parameter key
 * @param <V> parameter value
 */
public class Entry<K, V> {

    K key;
    V value;

    //Constructor#1
    public Entry()
    {
        key = null;
        value = null;
    }
    //Constructor#2
    public Entry(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}