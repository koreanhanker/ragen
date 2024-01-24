package ragen.common.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachFileDTO {
    private String attach_file_nm;
    private String attach_file_path;
    private String attach_file_org_nm;
    private long atch_file_size;
}
