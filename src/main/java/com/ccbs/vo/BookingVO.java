package com.ccbs.vo;

import com.ccbs.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingVO {
    private BookingStatus status;
    private String coachName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}