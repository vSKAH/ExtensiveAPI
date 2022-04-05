package fr.skah.skmdl.api.spigot.cooldown;

/*
 *  * @Created on 2022 - 16:58
 *  * @Project items-module
 *  * @Author jimmy  / vSKAH#0075
 */

import org.apache.commons.lang.time.DurationFormatUtils;

public interface Cooldown<T> {


    void addTimer(T object, String cooldownIdentifier, long time);

    boolean isInTimer(T object, String cooldownIdentifier);

    long getTime(T object, String cooldownIdentifier);

    default String getFormattedTime(T object, String cooldownIdentifier) {
        return DurationFormatUtils.formatDuration(getTime(object, cooldownIdentifier) * 1000, "H'h 'm'm 's's'");
    }

}
