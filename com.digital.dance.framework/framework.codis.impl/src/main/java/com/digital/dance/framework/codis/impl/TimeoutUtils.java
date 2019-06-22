package com.digital.dance.framework.codis.impl;

import java.util.concurrent.TimeUnit;

public abstract class TimeoutUtils {
    public TimeoutUtils() {
    }

    public static long toSeconds(long timeout, TimeUnit unit) {
        return roundUpIfNecessary(timeout, unit.toSeconds(timeout));
    }

    public static long toMillis(long timeout, TimeUnit unit) {
        return roundUpIfNecessary(timeout, unit.toMillis(timeout));
    }

    private static long roundUpIfNecessary(long timeout, long convertedTimeout) {
        return timeout > 0L && convertedTimeout == 0L ? 1L : convertedTimeout;
    }
}
