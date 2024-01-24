package ragen.example.service;

import ragen.example.dto.ExampleDTO;
import ragen.example.dto.QueryReqDTO;
import ragen.example.dto.QueryResDTO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface ExampleService {
    public List<ExampleDTO> getTestInfoList(ExampleDTO reqDTO);

    public List<LinkedHashMap<String, Object>> getQueryList(HashMap<String, Object> param);
}
