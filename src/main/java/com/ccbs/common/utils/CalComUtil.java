package com.ccbs.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
@Slf4j
@Component
public class CalComUtil {

    @Value("${cal.username}")
    private String calUsername;

    @Value("${cal.event-type-slug}")
    private String eventTypeSlug;

    /**
     * 生成预约链接 (使用公共预约页面)
     */
    public String generateBookingUrl(Long userId, String userName, String userEmail) {
        try {
            String encodedEmail = URLEncoder.encode(userEmail, StandardCharsets.UTF_8);
            String encodedName = URLEncoder.encode(userName, StandardCharsets.UTF_8);

            return String.format(
                    "https://cal.com/%s/%s?user=%s&email=%s&name=%s",
                    calUsername,
                    eventTypeSlug,
                    userId,
                    encodedEmail,
                    encodedName
            );
        } catch (Exception e) {
            log.error("生成预约链接失败", e);
            throw new RuntimeException("生成预约链接失败");
        }
    }

    /**
     * 生成取消预约链接
     */
    public String generateCancelUrl(String calBookingUid, String userEmail) {
        try {
            String encodedEmail = URLEncoder.encode(userEmail, StandardCharsets.UTF_8);

            return String.format(
                    "https://cal.com/booking/%s?cancel=true&uid=%s&email=%s&allRemainingBookings=false",
                    calBookingUid,
                    calBookingUid,
                    encodedEmail
            );
        } catch (Exception e) {
            log.error("生成取消预约链接失败", e);
            throw new RuntimeException("生成取消预约链接失败");
        }
    }


}
