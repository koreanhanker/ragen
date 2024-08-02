package ragen.example.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * class       :
 * Package      :ragen.example.dto
 *
 * @Description:
 * @author: SR22
 * @since: 2024-08-02 오후 3:34
 * 변경이력:
 * 이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * : 2024-08-02 :            : 신규 개발.
 */

@Data
public class UpbitDTO {
    private String market;
    private int count;
}
