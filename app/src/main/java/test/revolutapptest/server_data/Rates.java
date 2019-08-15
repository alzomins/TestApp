
package test.revolutapptest.server_data;

/**
 * Class rates
 *
 * @author Aleksandar Milojevic
 */
public class Rates {

    /**
     * Key elements from Map
     */
    private String key;

    /**
     * Value element from map
     */
    private Object value;

    /**
     * Full currency name
     */
    private String fullCurrencyName;

    /**
     * Flag
     */
    private String flag;

    /**
     * Constructor
     *
     * @param key   key
     * @param value value
     */
    public Rates(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Constructor
     *
     * @param key              key
     * @param value            value
     * @param fullCurrencyName full currency name
     * @param flag             flag
     */
    public Rates(String key, Object value, String fullCurrencyName, String flag) {
        this.key = key;
        this.value = value;
        this.fullCurrencyName = fullCurrencyName;
        this.flag = flag;
    }

    /**
     * Set key
     *
     * @param key key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Set value
     *
     * @param value value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Get key
     *
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Get value
     *
     * @return value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Get full currency name
     *
     * @return fullCurrencyName
     */
    public String getFullCurrencyName() {
        return fullCurrencyName;
    }

    /**
     * Set full currency name
     *
     * @param fullCurrencyName fullCurrencyName
     */
    public void setFullCurrencyName(String fullCurrencyName) {
        this.fullCurrencyName = fullCurrencyName;
    }

    /**
     * Get flag
     *
     * @return flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Set flag
     *
     * @param flag flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
}
