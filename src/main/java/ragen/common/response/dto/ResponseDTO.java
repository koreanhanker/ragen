package ragen.common.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ragen.common.response.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private final ResponseStatus status;
    private final String message;
    private final String code;
    private final T data;
}
