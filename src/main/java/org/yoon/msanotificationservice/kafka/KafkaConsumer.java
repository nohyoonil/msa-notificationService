package org.yoon.msanotificationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.yoon.msanotificationservice.Notification;
import org.yoon.msanotificationservice.NotificationRepository;
import org.yoon.msanotificationservice.NotificationService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "create.notification", groupId = "notification-service")
    public void listen(String message) {
        try {
            HashMap<String, Object> map = objectMapper.readValue(message, HashMap.class);
            Notification notification = Notification.builder()
                    .memberId(Long.parseLong(map.get("targetId").toString()))
                    .voteId(Long.parseLong(map.get("voteId").toString()))
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationRepository.save(notification);

            Map<String, Object> resposeMap = new HashMap<>();
            resposeMap.put("notificationId", notification.getId());
            resposeMap.put("memberId", notification.getMemberId());
            resposeMap.put("voteId", notification.getVoteId());
            resposeMap.put("createdAt", notification.getCreatedAt());

            notificationService.sendNotification(notification.getMemberId(),
                    objectMapper.writeValueAsString(resposeMap));

        } catch (JsonProcessingException e) {
            System.err.println("[❌ JSON 파싱 실패] " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[❌ 처리 중 오류 발생] " + e.getMessage());
        }
    }
}
