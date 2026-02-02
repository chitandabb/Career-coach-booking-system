package com.ccbs.dto;

import lombok.Data;

import java.util.List;

@Data
public class CalWebhookDTO {
    private String triggerEvent;
    private String createdAt;
    private Payload payload;

    @Data
    public static class Payload {
        private String uid;
        private String startTime;
        private String endTime;
        private Organizer organizer;
        private List<Attendee> attendees;
        private String status;
    }

    @Data
    public static class Organizer {
        private String email;
        private String name;
    }

    @Data
    public static class Attendee {
        private String email;
        private String name;
    }
}