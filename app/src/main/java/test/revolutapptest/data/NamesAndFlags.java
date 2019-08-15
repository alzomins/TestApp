package test.revolutapptest.data;

/**
 * Names and flags
 *
 * @author Aleksandar Milojevic
 */
public class NamesAndFlags {

    /**
     * Full name
     */
    private String fullName;

    /**
     * Flag
     */
    private String flag;

    /**
     * Constructor
     *
     * @param fullName full name
     * @param flag     flag
     */
    public NamesAndFlags(String fullName, String flag) {
        this.fullName = fullName;
        this.flag = flag;
    }

    /**
     * Get full name
     *
     * @return full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Get flag
     *
     * @return flag
     */
    public String getFlag() {
        return flag;
    }
}