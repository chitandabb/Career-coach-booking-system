package com.ccbs.service.impl;

import com.ccbs.common.result.Result;
import com.ccbs.common.utils.CalComUtil;
import com.ccbs.dto.CalWebhookDTO;
import com.ccbs.entity.Booking;
import com.ccbs.entity.User;
import com.ccbs.enums.BookingStatus;
import com.ccbs.mapper.BookingMapper;
import com.ccbs.service.BookingService;
import com.ccbs.vo.BookingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final CalComUtil calComUtil;
    private final BookingMapper bookingMapper;

    /**
     * 获取预约链接
     * @param userId 用户ID
     * @return 预约链接
     */
    @Override
    public Result<String> getBookingUrl(Long userId) {
        User user = bookingMapper.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("不存在该用户");
        String bookingUrl = calComUtil.generateBookingUrl(userId, user.getName(), user.getEmail());
        return Result.success(bookingUrl);
    }

    /**
     * 查询我的预约
     * @param userId 用户ID
     * @return 我的预约列表
     */
    @Override
    public Result<List<BookingVO>> getMyBookings(Long userId) {
        List<Booking> myBookings = bookingMapper.getMyBookings(userId);
        List<BookingVO> bookingVOS = myBookings.stream().map(b -> {
            BookingVO bookingvo = new BookingVO();
            BeanUtils.copyProperties(b, bookingvo);
            return bookingvo;
        }).collect(Collectors.toList());
        return Result.success(bookingVOS);
    }

    /**
     * 处理 Cal.com Webhook 事件
     * @param event Webhook 事件
     */
    @Override
    public void handleWebhookEvent(CalWebhookDTO event) {
        switch (event.getTriggerEvent()){
            case "BOOKING_CREATED" -> onBookingCreated(event);
            case "BOOKING_CANCELLED" -> onBookingCanceled(event);
        }
    }

    /**
     * 处理取消预约事件
     * @param dto Webhook 事件
     */
    private void onBookingCanceled(CalWebhookDTO dto) {
        CalWebhookDTO.Payload payload = dto.getPayload();
        String calBookingUid = payload.getUid();
        bookingMapper.update(calBookingUid);
    }

    /**
     * 处理预约创建事件
     * @param dto Webhook 事件
     */
    public void onBookingCreated(CalWebhookDTO dto) {
        CalWebhookDTO.Payload payload = dto.getPayload();
        String calBookingUid = payload.getUid();
        CalWebhookDTO.Attendee attendee = payload.getAttendees().get(0);
        CalWebhookDTO.Organizer organizer = payload.getOrganizer();
        Booking booking = new Booking();
        booking.setUserId(bookingMapper.getUserIdByEmail(attendee.getEmail()));
        booking.setCoachEmail(organizer.getEmail());
        booking.setCoachName(organizer.getName());
        booking.setStartTime(parseUtc(payload.getStartTime()));
        booking.setEndTime(parseUtc(payload.getEndTime()));
        booking.setStatus(BookingStatus.BOOKING_CREATED);
        booking.setCalBookingUid(calBookingUid);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        bookingMapper.insert(booking);
    }

    /**
     * 解析 UTC 时间
     * @param time 时间字符串
     * @return LocalDateTime
     */
    private LocalDateTime parseUtc(String time) {
        return OffsetDateTime.parse(time)
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }


    /**
     * 生成取消预约链接
     * @return 取消预约链接
     */
    @Override
    public Result<String> cancelBooking(String calBookingUid) {
        String email = bookingMapper.selectEmailByBookingUid(calBookingUid);
        return Result.success(calComUtil.generateCancelUrl(calBookingUid,email));
    }
}
