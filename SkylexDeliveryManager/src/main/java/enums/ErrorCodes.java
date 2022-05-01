package enums;

public enum ErrorCodes {
    // region Generic error codes (Reserved from 1 to 99)
    /**
     * Error code when a requested object is not found in the database
     */
    NOT_FOUND_IN_DB (),
    // endregion

    // region Driver error codes
    /**
     * The driver name was null or whitespace
     */
    DRIVER_NAME_EMPTY_OR_NULL,

    /**
     * Driver not found in the database
     */
    DRIVER_NOT_FOUND,

    /**
     * No driver was selected from the list
     */
    NO_DIVER_SELECTED,
}
