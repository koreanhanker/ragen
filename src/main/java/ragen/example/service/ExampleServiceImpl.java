package ragen.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ragen.common.fein.UpbitClient;
import ragen.example.dao.ExampleDAO;
import ragen.example.dto.ExampleDTO;
import ragen.example.dto.QueryReqDTO;
import ragen.example.dto.QueryResDTO;
import ragen.example.dto.UpbitDTO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExampleServiceImpl implements ExampleService{

    @Autowired
    private ExampleDAO exampleDAO;

    @Autowired
    private UpbitClient upbitClient;

    public List<ExampleDTO> getTestInfoList(ExampleDTO reqDTO){
        return exampleDAO.selectTestInfoList(reqDTO);
    }

    public List<LinkedHashMap<String, Object>>  getQueryList(HashMap<String, Object> param){
        return exampleDAO.selectQueryList(param);
    }

    public String getCandleDays(UpbitDTO upbitDTO) {
        return upbitClient.getCandleDays("application/json", upbitDTO.getMarket(), upbitDTO.getCount());
    }
}
