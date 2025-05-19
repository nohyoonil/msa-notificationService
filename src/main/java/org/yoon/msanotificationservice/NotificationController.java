package org.yoon.msanotificationservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@RequestHeader(value = "X-User-Id") long userId) {
        return notificationService.connect(userId);
    }

    @GetMapping("/api/notifications/test")
    public void test(@RequestHeader(value = "X-User-Id") long userId) {
        notificationService.sendNotification(userId, "test123");
    }
}
