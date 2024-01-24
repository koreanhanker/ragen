package ragen.common.handler;

import ragen.common.response.dto.ResponseDTO;
import ragen.common.response.enums.ResponseCode;
import ragen.common.util.ResponseUtil;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseDTO handleSizeLimitExceededException(SizeLimitExceededException e) {
        return ResponseUtil.ERROR("FAIL.FILESIZE.MAXIMUM.LIMIT",null);
    }
}