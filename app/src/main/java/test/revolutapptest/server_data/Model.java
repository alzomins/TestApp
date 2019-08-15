
package test.revolutapptest.server_data;

import java.util.LinkedHashMap;

/**
 * Model
 *
 * @author Aleksandar Milojevic
 */
public class Model {

    /**
     * Base
     */
    private String base;

    /**
     * Date
     */
    private String date;

    /**
     * Linked has map
     */
    private LinkedHashMap<String, Object> rates;

    /**
     * Get base
     *
     * @return base
     */
    public String getBase() {
        return base;
    }

    /**
     * Get date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Get rates
     *
     * @return dates
     */
    public LinkedHashMap<String, Object> getRates() {
        return rates;
    }
}
