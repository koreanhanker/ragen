package ragen.common.paging.service;

import ragen.common.paging.dto.PagingDTO;

public interface PageingService {
    public PagingDTO getPagingInfo(PagingDTO reqDTO);
}
