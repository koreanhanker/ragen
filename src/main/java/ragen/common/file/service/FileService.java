package ragen.common.file.service;

import ragen.common.file.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    public FileDTO saveFile(MultipartFile files);
    public FileDTO saveFile(MultipartFile files , String uploadFileDir);
    public List<FileDTO> saveFile(List<MultipartFile> files , String uploadFileDir);
}
