package com.dalgona.zerozone.exam.dto;

import com.dalgona.zerozone.practice.dto.ReadingProbResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ExamCreateResponseDto {
    Long id;
    String examName;
    List<ReadingProbResponseDto> readingProbResponseDtoList;

    public ExamCreateResponseDto(Long id, String examName, List<ReadingProbResponseDto> readingProbResponseDtoList){
        this.id = id;
        this.examName = examName;
        this.readingProbResponseDtoList = readingProbResponseDtoList;
    }
}
