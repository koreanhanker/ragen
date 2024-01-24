package ragen.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ragen.common.response.dto.ResponseDTO;
import ragen.common.response.enums.ResponseCode;
import ragen.common.response.enums.ResponseStatus;

import java.util.Locale;

@Component
public class ResponseUtil {

	public static <T> ResponseDTO<T> SUCCESS(String messageCode, T data) {
		return new ResponseDTO<>(ResponseStatus.SUCCESS, MessageUtils.getMessage(messageCode),ResponseCode.SUCCESS_CODE.getCode(), data);
	}
	public static <T> ResponseDTO<T> FAIL(String messageCode, T data) {
		return new ResponseDTO<>(ResponseStatus.FAIL, MessageUtils.getMessage(messageCode),ResponseCode.FAIL_CODE.getCode(), data);
	}

	public static <T> ResponseDTO<T> ERROR(String messageCode, T data) {
		return new ResponseDTO<>(ResponseStatus.ERROR, MessageUtils.getMessage(messageCode),ResponseCode.ERROR_CODE.getCode(), data);
	}

}
