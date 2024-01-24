package ragen.common.file.dto;

import lombok.Data;
@Data
public class FileDTO {
    private String atch_file_nm;
    private String atch_file_org_nm;
    private String atch_file_path;
    private long atch_file_size;

    public FileDTO(String atch_file_nm, String atch_file_org_nm, String atch_file_path,long atch_file_size) {
        this.atch_file_nm = atch_file_nm;
        this.atch_file_org_nm = atch_file_org_nm;
        this.atch_file_path = atch_file_path;
        this.atch_file_size = atch_file_size;
    }

    public FileDTO() {

    }
}
