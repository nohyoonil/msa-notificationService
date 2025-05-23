package org.yoon.msanotificationservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDetailDto {

    private long voteId;
    private long voterId;
    private long targetId;
    private long questionId;
    private int rewards;
    private boolean opened;
    private LocalDateTime createdAt;
}
