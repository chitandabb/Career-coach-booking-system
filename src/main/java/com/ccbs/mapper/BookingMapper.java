package com.ccbs.mapper;

import com.ccbs.entity.Booking;
import com.ccbs.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BookingMapper {
    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    User getUserById(Long userId);

    @Select("SELECT * FROM bookings WHERE user_id = #{userId}")
    List<Booking> getMyBookings(Long userId);

    void insert(Booking booking);

    @Select("SELECT user_id FROM users WHERE email = #{email}")
    Long getUserIdByEmail(String email);

    String selectEmailByBookingUid(@Param("calBookingUid") String calBookingUid);

    @Update("UPDATE bookings SET status = 'BOOKING_CANCELED' WHERE cal_booking_uid = #{calBookingUid}")
    void update(String calBookingUid);
}
