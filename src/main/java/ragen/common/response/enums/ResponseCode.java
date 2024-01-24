package ragen.common.response.enums;

public enum ResponseCode {
	SUCCESS_CODE("0000"),
	ERROR_CODE("-1"),
	FAIL_CODE("-1");
	private final String code;


	ResponseCode(String code) {
		this.code = code;

	}

	public String getCode() {
		return code;
	}
}
