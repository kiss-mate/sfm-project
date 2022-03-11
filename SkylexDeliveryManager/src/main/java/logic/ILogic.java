package logic;

/**
 * Provides business and transaction logic for the project
 */
public interface ILogic {
    /**
     * Creates and adds one Sample object to the "sample" table
     * @param value1 value1
     * @param value2 value2
     */
    void addOneSample(String value1, int value2);
}
