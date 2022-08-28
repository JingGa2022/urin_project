package com.dongpop.urin.domain.inquiry.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Getter
public class InquiryUpdateDto {
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min=1)
    private String contents;
}
