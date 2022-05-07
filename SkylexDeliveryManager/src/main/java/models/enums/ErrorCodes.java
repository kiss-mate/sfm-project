package models.enums;

public enum ErrorCodes {
    // region Generic error codes

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

    //endregion

    // region Vehicle error codes

    VEHICLE_PLATE_NUMBER_INVALID,

    VEHICLE_MAX_CAPACITY_INVALID,

    VEHICLE_NOT_FOUND,

    NO_VEHICLE_SELECTED,

    //endregion
}
