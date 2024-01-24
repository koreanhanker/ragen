package ragen.common.mail.service;

import ragen.common.mail.dto.AttachFileDTO;
import ragen.common.mail.dto.MailDTO;
import ragen.common.util.StringUtils;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Value("${mail.encoding.charset}")
    private String charset;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    private final JavaMailSender javaMailSender;
    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public MailDTO sendMail(MailDTO mailDto, List<AttachFileDTO>  attacheFilelist)
    {
        try {
            File directory = new File(uploadPath+"/"+mailDto.getUploadFileDir());
            if(!directory.exists()) {
                directory.mkdirs();
            }

            List<AttachFileDTO> atchFileList = new ArrayList();
            for(AttachFileDTO file : attacheFilelist) {
                //uuid 생성
                String atchFileOrgNm = file.getAttach_file_org_nm();
                // 확장자 추출(ex : .pdf)
                String extension = file.getAttach_file_nm();
                // uuid와 확장자 결합
                String attachFileNm = file.getAttach_file_nm();
                String attachFilePath = directory + "/" + attachFileNm;

                //파일저장하기
                Path filePath = Paths.get(attachFilePath);
                Files.createFile(filePath);

                atchFileList.add(new AttachFileDTO(attachFileNm,attachFilePath, atchFileOrgNm,file.getAtch_file_size()));
            }

            mailDto.setAttachFileList(atchFileList);
            mailDto.setToAddressList(Arrays.asList(mailDto.getToAddr().split(",")));

            this.sendMail(mailDto);

        }catch(Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }

        return mailDto;
    }

    public List<AttachFileDTO> getAttacheFileList(MailDTO mailDto, List<MultipartFile> files){

        List<AttachFileDTO> atchFileList = new ArrayList();

        if(files == null) {
            return atchFileList;
        }

        File directory = new File(uploadPath+"/"+mailDto.getUploadFileDir());
        if(!directory.exists()) {
            directory.mkdirs();
        }

        for(MultipartFile file : files) {
            //uuid 생성
            String uuid = UUID.randomUUID().toString();
            String atchFileOrgNm = file.getOriginalFilename();
            long attachFileSize = file.getSize();
            // 확장자 추출(ex : .pdf)
            String extension = atchFileOrgNm.substring(atchFileOrgNm.lastIndexOf("."));
            // uuid와 확장자 결합
            String attachFileNm = uuid + extension;
            String attachFilePath = directory + "/" + attachFileNm;
            atchFileList.add(new AttachFileDTO(attachFileNm,attachFilePath, atchFileOrgNm,attachFileSize));
        }

        return atchFileList;
    }

    @Override
    public boolean sendMail(MailDTO mailDto)
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, charset); // use multipart (true)
            InternetAddress[] toAddress = StringUtils.listToArray(mailDto.getToAddressList(), "");
            mimeMessageHelper.setSubject(MimeUtility.encodeText(mailDto.getSubject(), charset, "B")); // Base64 encoding
            mimeMessageHelper.setText(mailDto.getContent(), Boolean.parseBoolean(mailDto.getHtml_yn().equals("Y")?"true":"false"));
            mimeMessageHelper.setFrom(new InternetAddress(mailDto.getFromEmail(),mailDto.getFormUserName(),charset));
            mimeMessageHelper.setTo(toAddress);

            if(!CollectionUtils.isEmpty(mailDto.getAttachFileList())) {
                for(AttachFileDTO attachFileDto: mailDto.getAttachFileList()) {
                    FileSystemResource fileSystemResource = new FileSystemResource(new File(attachFileDto.getAttach_file_path()));
                    mimeMessageHelper.addAttachment(MimeUtility.encodeText(attachFileDto.getAttach_file_org_nm(), charset, "B"), fileSystemResource);
                }
            }

            javaMailSender.send(mimeMessage);

            log.info("MailServiceImpl.sendMail() :: SUCCESS");
        } catch (Exception e) {
            log.info("MailServiceImpl.sendMail() :: FAILED");
            e.printStackTrace();
            return true;
        }

        return true;
    }
}
