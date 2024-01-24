package ragen.example.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import ragen.example.dto.ExampleDTO;
import ragen.example.dto.QueryReqDTO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExampleDAO {

    private SqlSessionTemplate sqlSessionTemplate;

    public ExampleDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    private static final String NAMESPACE = "TEST.";

    public List<ExampleDTO> selectTestInfoList(ExampleDTO reqDTO) {
        return sqlSessionTemplate.selectList(NAMESPACE + "selectTestInfoList", reqDTO);
    }

    public List<LinkedHashMap<String, Object>> selectQueryList(HashMap<String, Object> param) {
        return sqlSessionTemplate.selectList(NAMESPACE + "selectQueryList", param);
    }
}
