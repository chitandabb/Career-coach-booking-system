package com.ccbs.service;

import com.ccbs.common.result.Result;
import com.ccbs.dto.CalWebhookDTO;
import com.ccbs.vo.BookingVO;

import java.util.List;

public interface BookingService {
    Result<String> getBookingUrl(Long userId);

    Result<List<BookingVO>> getMyBookings(Long userId);

    void handleWebhookEvent(CalWebhookDTO event);

    Result<String> cancelBooking(String calBookingUid);
}
