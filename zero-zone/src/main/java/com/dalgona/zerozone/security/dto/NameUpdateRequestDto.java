package com.dalgona.zerozone.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NameUpdateRequestDto {
    @NotNull(message = "새로운 이름은 null일 수 없습니다.")
    @NotEmpty(message = "새로운 이름은 빈값일 수 없습니다.")
    private String name;
}
