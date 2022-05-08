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
    /**
     * The vehicle plate number was null or whitespace
     */
    VEHICLE_PLATE_NUMBER_INVALID,

    /**
     * Vehicle max capacity was null or invalid
     */
    VEHICLE_MAX_CAPACITY_INVALID,

    /**
     * Vehicle not found in the database
     */
    VEHICLE_NOT_FOUND,

    /**
     * No vehicle was selected from the list
     */
    NO_VEHICLE_SELECTED,

    //endregion


    // region Package error codes

    /**
     * The package content was null or whitespace
     */
    PACKAGE_CONTENT_EMPTY_OR_NULL,

    /**
     * The package destination is null or whitespace
     */
    PACKAGE_DESTINATION_EMPTY_OR_NULL,

    /**
     * Zero or negative package weight
     */
    PACKAGE_INVALID_WEIGHT,

    /**
     * Package not found in the database
     */
    PACKAGE_NOT_FOUND,

    /**
     * No package was selected from the list
     */
    NO_PACKAGE_SELECTED,

    //endregion
}
