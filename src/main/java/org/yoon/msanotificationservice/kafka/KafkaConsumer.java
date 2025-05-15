package org.yoon.msanotificationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.yoon.msanotificationservice.Notification;
import org.yoon.msanotificationservice.NotificationRepository;
import org.yoon.msanotificationservice.NotificationService;
import org.yoon.msanotificationservice.model.dto.NotificationSseDto;
import org.yoon.msanotificationservice.model.dto.VoteDetailDto;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "create.notification", groupId = "notification-service")
    public void listen(String message) {
        try {
            VoteDetailDto voteDetail = objectMapper.readValue(message, VoteDetailDto.class);
            Notification notification = Notification.builder()
                    .memberId(voteDetail.getTargetId()) //알림 송신 -> targetId member
                    .voteId(voteDetail.getVoteId())
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationRepository.save(notification);

            notificationService.sendNotification(notification.getMemberId(),
                    objectMapper.writeValueAsString(NotificationSseDto.from(notification)));

        } catch (JsonProcessingException e) {
            System.err.println("[❌ JSON 파싱 실패] " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[❌ 처리 중 오류 발생] " + e.getMessage());
        }
    }
}
