package com.dalgona.zerozone.exam.service;

import com.dalgona.zerozone.exam.dto.ExamCreateRequestDto;
import com.dalgona.zerozone.exam.dto.ExamCreateResponseDto;
import com.dalgona.zerozone.exam.dto.ExamSettingInfoResponseDto;

public interface ExamCreateInterface {
    ExamSettingInfoResponseDto getExamSettingInfo();

    ExamCreateResponseDto createExam(ExamCreateRequestDto examCreateRequestDto);
}
