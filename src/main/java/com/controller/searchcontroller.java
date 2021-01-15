package com.controller;

import com.po.Dto;
import com.service.searchhotelservice;
import com.util.DtoUtil;
import com.util.EmptyUtils;
import com.util.Page;
import com.util.vo.ItripHotelVO;
import com.util.vo.SearchHotCityVO;
import com.util.vo.SearchHotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/hotellist")
public class searchcontroller {
    @Autowired
    private searchhotelservice searchhotelservice;

     @RequestMapping(value = "/searchItripHotelPage")
    public Dto searchItripHotelPage(@RequestBody SearchHotelVO searchHotelVO){
    System.out.println("开始查询酒店地址");

    if(EmptyUtils.isEmpty(searchHotelVO)||EmptyUtils.isEmpty(searchHotelVO.getDestination())){
      return DtoUtil.returnFail("输入的目的地1不能为空","32211");
    }

    try {
        System.out.println("开始调用serivice方法");
        Page page=searchhotelservice.searchhotelsave(searchHotelVO,searchHotelVO.getPageNo(),searchHotelVO.getPageSize());
        System.out.println("page在"+page.toString());
    if(page.getRows().size()==0){
        System.out.println("本城市酒店为空");
        return DtoUtil.returnFail("本城市酒店为空","213123");
    }
    return DtoUtil.returnDataSuccess(page);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
  //根据热门城市查酒店
  @RequestMapping(value = "/searchItripHotelListByHotCity")
  public Dto searchItripHotelListByHotCity(@RequestBody SearchHotCityVO searchHotCityVO){
      System.out.println("根据热门城市查酒店方法开始");
      if(EmptyUtils.isEmpty(searchHotCityVO)||EmptyUtils.isEmpty(searchHotCityVO.getCityId())){
          return DtoUtil.returnFail("热门城市id不能为空","333211");
      }
      try {
          List<ItripHotelVO> itripHotelVOS=searchhotelservice.searchcityid(searchHotCityVO.getCityId(),searchHotCityVO.getCount());
          return DtoUtil.returnDataSuccess(itripHotelVOS);
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }
}
