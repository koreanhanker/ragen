package ragen.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ragen.common.base.controller.BaseController;
import ragen.common.datasSource.config.MessageConfig;
import ragen.common.response.dto.ResponseDTO;
import ragen.common.response.enums.ResponseCode;
import ragen.common.util.ResponseUtil;
import ragen.example.dto.ExampleDTO;
import ragen.example.service.ExampleService;

import java.util.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/example")
public class ExampleController extends BaseController {

    @Autowired
    private ExampleService exampleService;

    @RequestMapping("/getExampleList")
    public ResponseDTO setSystemAuth(@RequestBody(required = false) ExampleDTO exampleDTO) {

        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();

        List<ExampleDTO> resultList =  exampleService.getTestInfoList(exampleDTO);

        resultMap.put("exampleList",resultList);

        return ResponseUtil.SUCCESS("SUCCESS.SEARCH",resultMap);
    }

    @RequestMapping("/getQueryList")
    public ResponseDTO getQueryList(@RequestBody HashMap<String, Object> param) {

        LinkedHashMap<String, Object> resMap = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> resultMap  = exampleService.getQueryList(param);

        resMap.put("queryList",resultMap);

        return ResponseUtil.SUCCESS("SUCCESS.SEARCH",resMap);
    }


    @RequestMapping("/message")
    public ResponseDTO message() {
        return ResponseUtil.SUCCESS("SUCCESS.SEARCH","데이터입니다.");
    }
}
