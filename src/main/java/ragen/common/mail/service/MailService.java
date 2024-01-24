package ragen.common.mail.service;

import ragen.common.mail.dto.AttachFileDTO;
import ragen.common.mail.dto.MailDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MailService {
    public boolean sendMail(MailDTO mailDto);

    public MailDTO sendMail(MailDTO mailDto, List<AttachFileDTO>  attacheFilelist);

    public List<AttachFileDTO> getAttacheFileList(MailDTO mailDto, List<MultipartFile> files);
}
