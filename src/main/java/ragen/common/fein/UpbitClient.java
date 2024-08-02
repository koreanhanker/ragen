package ragen.common.fein;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * class       :
 * Package      :ragen.common.fein
 *
 * @Description:
 * @author: SR22
 * @since: 2024-08-02 오후 3:31
 * 변경이력:
 * 이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * : 2024-08-02 :            : 신규 개발.
 */
@FeignClient(name = "upbitClient", url = "https://api.upbit.com")
public interface UpbitClient {

    @GetMapping("/v1/candles/days")
    String getCandleDays(
            @RequestHeader("accept") String acceptHeader,
            @RequestParam("market") String market,
            @RequestParam("count") int count
    );
}
