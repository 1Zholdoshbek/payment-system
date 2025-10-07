package com.example.paymentserviceapp.util;

import java.time.Instant;
import java.time.OffsetDateTime;

public final class DateTimeUtils {

    private DateTimeUtils() {}

    public static OffsetDateTime toOffsetDateTimeOrMin(Instant instant) {
        return instant != null
                ? OffsetDateTime.ofInstant(instant, TimeConstants.DEFAULT_ZONE_OFFSET)
                : OffsetDateTime.MIN;
    }

    public static OffsetDateTime toOffsetDateTimeOrMax(Instant instant) {
        return instant != null
                ? OffsetDateTime.ofInstant(instant, TimeConstants.DEFAULT_ZONE_OFFSET)
                : OffsetDateTime.MAX;
    }
}