package com.ccbs.controller;

import com.ccbs.dto.CalWebhookDTO;
import com.ccbs.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WebhookController {
    
    private final BookingService bookingService;
    /**
     * 接收 Cal.com Webhook 事件
     */
    @PostMapping("/cal")
    public void handleCalWebhook(@RequestBody CalWebhookDTO dto) {
        log.info(dto.getTriggerEvent());
        log.info(dto.toString());
        bookingService.handleWebhookEvent(dto);
    }
}