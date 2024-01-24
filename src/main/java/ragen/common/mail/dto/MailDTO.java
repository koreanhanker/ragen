package ragen.common.mail.dto;

import ragen.common.util.CommonConstant;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class MailDTO extends AttachFileDTO {
    private List<String> toAddressList = new ArrayList<>();
    private String toAddr;
    private String fromEmail; ///발송자 이메일주소
    private String formUserName; //발송자명
    private String subject; // 제목
    private String content; // 메일 내용
    private String html_yn; // 메일 형식이 HTML인지 여부(true, false)
    private List<AttachFileDTO> attachFileList = new ArrayList<>();
    private String uploadFileDir = CommonConstant.DEFAULT_UPLOAD_MAIL_DIR;
}
