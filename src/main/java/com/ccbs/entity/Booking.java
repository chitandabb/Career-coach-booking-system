package com.ccbs.entity;

import com.ccbs.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 预约记录表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    private Long id;
    private Long userId;
    private String coachEmail;
    private String coachName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;
    private String calBookingUid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
