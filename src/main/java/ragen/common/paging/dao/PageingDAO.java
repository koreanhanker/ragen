package ragen.common.paging.dao;

import ragen.common.paging.dto.PagingDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PageingDAO {
    private SqlSessionTemplate sqlSessionTemplate;

    public PageingDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    private static final String NAMESPACE = "common.";

    public PagingDTO selectPaging(PagingDTO reqDTO) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectPaging", reqDTO);
    }
}
