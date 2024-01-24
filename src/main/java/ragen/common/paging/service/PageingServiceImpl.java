package ragen.common.paging.service;

import ragen.common.paging.dao.PageingDAO;
import ragen.common.paging.dto.PagingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageingServiceImpl implements PageingService{

    @Autowired
    private PageingDAO pageingDAO;

    public PagingDTO getPagingInfo(PagingDTO reqDTO){
        return pageingDAO.selectPaging(reqDTO);
    }
}
