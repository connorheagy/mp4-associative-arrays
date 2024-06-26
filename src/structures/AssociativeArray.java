package structures;

import static java.lang.reflect.Array.newInstance;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Connor Heagy
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K,V> copyArr = new AssociativeArray<K, V>();
    if (this.size == 0) {
      return null;
    } // if
    for (int i = 0; i < this.size; i++) {
      try {
        copyArr.set(pairs[i].key, pairs[i].value);
      } catch (Exception e) {
        fail("clone method is not working correctly");} 
    } // for
    return copyArr;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    String[] arr = new String [this.size];
    if (this.size == 0)
      {return "{}";} // if
    else if (this.size == 1) {
      return "{ " + pairs[0].key.toString() + ": " + pairs[0].value.toString() + " }";
    } // else if 
      for (int i = 0; i < this.size; i++) {
        if (i == 0) {
          arr[i] = "{ " + pairs[i].key.toString() + ": " + pairs[i].value.toString();
        } // if 
        else if (i == this.size - 1) {
          arr[i] = pairs[i].key.toString() + ": " + pairs[i].value.toString() + " }";
        } // else if
        else
        {arr[i] = pairs[i].key.toString() + ": " + pairs[i].value.toString();} // else
      } // for
      return String.join(", ", arr);
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws NullKeyException {
    // expand first if full
    if (this.size == 16) {
      expand();} // if 
    if (key == null) {
      throw new NullKeyException();} // if 
    else if (hasKey(key) == false) {
      if (this.size == 16) {
        expand();
      } // if 
      pairs[this.size++] = new KVPair<K,V>(key, value);
    } // else if
    else {
      for (int i = 0; i < this.size; i++) {
        if (this.size == 16) {
          // expand if full
          expand();
        } // if
        if (pairs[i].key.equals(key)) {
          pairs[i].value = value;} // if
      } // for
    } // else
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    // if the array is empty then throw exception
    if (size == 0) {
      throw new KeyNotFoundException();
    } // if 
    for (int i = 0; i < this.size; i++) {
      if (pairs[i].key.equals(key)) {
        return pairs[i].value;} // if
      } // for
    return null;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    for (int i = 0; i < this.size; i++) {
      // checks to see if key matches
      if (pairs[i].key.equals(key)) {
        return true;} // if 
    } // for 
    // if key is nevver found, return false 
    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    for (int i = 0; i < this.size; i++) {
      if (pairs[i].key.equals(key)) {
        for (int j = i; j < this.size; j++) {
          if (j == this.size - 1) {
            // setting the pair to null
            pairs[j].key = null;
            pairs[j].value = null;
          } // if
          else {
          pairs[j].value = pairs[j + 1].value;
          pairs[j].key = pairs[j + 1].key;} // else
        } // for 
        this.size--;
      } // if
    } // for 
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  int find(K key) throws KeyNotFoundException {
    int index = 0;
    // checks to see if the key is present or not, if not then throws exception
    if (hasKey(key) == false) {
      throw new KeyNotFoundException(); 
    } // if
    for (int i = 0; i < this.size; i++) {
      if (pairs[i].key.equals(key)) {
        index = i;
        break;
      } // if
    } // for
    return index;
  } // find(K)

} // class AssociativeArray
