package com.dongpop.urin.domain.feedback.dto.request;

import com.dongpop.urin.domain.feedbackcontent.entity.FeedbackContentType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class FeedbackSetDataDto {
    @NotNull
    private FeedbackContentType type;
    @NotNull
    @Size(min = 1)
    private List<FeedbackDataDto> feedbackList;
}
