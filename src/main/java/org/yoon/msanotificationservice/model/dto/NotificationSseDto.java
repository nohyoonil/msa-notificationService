package org.yoon.msanotificationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.yoon.msanotificationservice.Notification;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class NotificationSseDto {

    private long notificationId;
    private Long memberId;
    private Long voteId;
    private LocalDateTime createdAt;

    public static NotificationSseDto from(Notification notification) {
        return NotificationSseDto.builder()
                .notificationId(notification.getId())
                .memberId(notification.getMemberId())
                .voteId(notification.getVoteId())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
