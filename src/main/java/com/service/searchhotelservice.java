package com.service;

import com.util.Page;
import com.util.vo.ItripHotelVO;
import com.util.vo.SearchHotelVO;

import java.util.List;

public interface searchhotelservice {
    /*查询酒店所以信息*/
    public Page<ItripHotelVO> searchhotelsave(SearchHotelVO searchHotelVO, Integer pageNo, Integer pageSize) throws Exception;
    /*查询酒店热门信息id*/
    public List<ItripHotelVO> searchcityid(Integer cityId,Integer pagesize) throws Exception;

}
