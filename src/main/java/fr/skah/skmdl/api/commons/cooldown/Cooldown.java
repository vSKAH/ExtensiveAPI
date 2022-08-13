package fr.skah.skmdl.api.commons.cooldown;

/*
 *  * @Created on 2022 - 16:58
 *  * @Project items-module
 *  * @Author jimmy  / vSKAH#0075
 */

import org.apache.commons.lang.time.DurationFormatUtils;

public interface Cooldown<T> {

    /**
     * Adds a timer to the cooldown manager
     *
     * @param object The object that will be used to identify the cooldown.
     * @param cooldownIdentifier This is the identifier for the cooldown.
     * @param time The time in milliseconds that the cooldown will last.
     */
    void addTimer(T object, String cooldownIdentifier, long time);

    /**
     * Returns true if the object is in the cooldown timer, false otherwise.
     *
     * @param object The object that you want to check if it's in a timer.
     * @param cooldownIdentifier The identifier for the cooldown.
     * @return A boolean value.
     */
    boolean isInTimer(T object, String cooldownIdentifier);

    /**
     * "Returns the time in milliseconds when the cooldown will expire."
     * The first parameter is the object that is being checked. The second parameter is the cooldown identifier
     *
     * @param object The object that is being checked.
     * @param cooldownIdentifier The identifier for the cooldown.
     * @return The time in milliseconds that the cooldown will expire.
     */
    long getTime(T object, String cooldownIdentifier);

    /**
     * It returns a formatted string of the time left on the cooldown
     *
     * @param object The object that the cooldown is being applied to.
     * @param cooldownIdentifier The identifier of the cooldown.
     * @return A string of the time left in the format of H'h 'm'm 's's'
     */
    default String getFormattedTime(T object, String cooldownIdentifier) {
        return DurationFormatUtils.formatDuration(getTime(object, cooldownIdentifier) * 1000, "H'h 'm'm 's's'");
    }

}
