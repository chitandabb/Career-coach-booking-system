package com.ccbs.controller;

import com.ccbs.service.BookingService;
import com.ccbs.vo.BookingVO;
import com.ccbs.common.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * 获取预约链接
     */
    @PostMapping("/booking-url")
    public Result<String> getBookingUrl(@RequestParam Long userId) {
        return bookingService.getBookingUrl(userId);
    }

    /**
     * 查询我的预约
     */
    @GetMapping("/bookings")
    public Result<List<BookingVO>> getMyBookings(@RequestParam Long userId) {
        return bookingService.getMyBookings(userId);
    }

    /**
     * 获取取消预约链接*/
    @PostMapping("/bookings/cancel")
    public Result<String> cancelBooking(@RequestParam String calBookingUid) {
        return bookingService.cancelBooking(calBookingUid);
    }

}