package com.ccbs.enums;

import lombok.Getter;

/**
 * 预约状态枚举
 */
public enum BookingStatus {
    PENDING,
    BOOKING_CREATED,
    BOOKING_CANCELLED,
    MEETING_ENDED,
    NO_SHOW
}
