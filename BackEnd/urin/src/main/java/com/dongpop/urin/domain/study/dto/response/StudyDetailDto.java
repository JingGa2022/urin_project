package com.dongpop.urin.domain.study.dto.response;

import com.dongpop.urin.domain.participant.dto.response.ParticipantDto;
import com.dongpop.urin.domain.study.entity.StudyStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class StudyDetailDto {
    private int id;
    private String title;
    private String notice;
    private int memberCapacity;
    private int currentMember;
    private StudyStatus status;
    private LocalDate expirationDate;
    private int dDay;
    private Boolean isOnair;
    private String hashtagCodes;
    private List<String> hashtagNameList;
    private List<ParticipantDto> participants;
}

