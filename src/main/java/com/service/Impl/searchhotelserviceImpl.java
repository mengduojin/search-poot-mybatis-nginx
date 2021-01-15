package com.service.Impl;

import com.mapper.BaseQuery;
import com.service.searchhotelservice;
import com.util.EmptyUtils;
import com.util.Page;
import com.util.vo.ItripHotelVO;
import com.util.vo.SearchHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class searchhotelserviceImpl implements searchhotelservice {
    private BaseQuery<ItripHotelVO> baseQuery=new BaseQuery<ItripHotelVO>("http://127.0.0.1:8089/solr/hotel");
    @Override
    public Page<ItripHotelVO> searchhotelsave(SearchHotelVO searchHotelVO, Integer pageNo, Integer pageSize) throws Exception {
       //查询所以
        SolrQuery solrQuery=new SolrQuery("*:*");
        //new一个字符串拼接
        StringBuffer stringBuffer=new StringBuffer();
        int temp=0;
        //如果输入的目的地不为空
        if(EmptyUtils.isNotEmpty(searchHotelVO)){
            if(EmptyUtils.isNotEmpty(searchHotelVO.getDestination())){
                System.out.println("如果目的地不为空");
            stringBuffer.append("destination:"+searchHotelVO.getDestination());
            temp=1;
                System.out.println("sss123123123");
            }
            if(EmptyUtils.isNotEmpty(searchHotelVO.getHotelLevel())){
                solrQuery.addFilterQuery("hotelLevel:"+searchHotelVO.getHotelLevel()
                );
            }
            if(EmptyUtils.isNotEmpty(searchHotelVO.getKeywords())&&!(searchHotelVO.getKeywords().equals("null"))){
              if(temp==1){
                  stringBuffer.append(" AND keyword:"+searchHotelVO.getKeywords());
              } else {
                  stringBuffer.append("keyword:"+searchHotelVO.getKeywords());
              }
            }
            if(EmptyUtils.isNotEmpty(searchHotelVO.getMaxPrice())){
              solrQuery.addFilterQuery("minPrice:"+"[* TO "+searchHotelVO.getMaxPrice()+"]");
                System.out.println("最大值"+searchHotelVO.getMaxPrice());
            }
            if(EmptyUtils.isNotEmpty(searchHotelVO.getMinPrice())){
                solrQuery.addFilterQuery("minPrice:"+"["+searchHotelVO.getMinPrice()+" T O *]");
                System.out.println("最小值"+searchHotelVO.getMinPrice());
            }
            if(EmptyUtils.isNotEmpty(searchHotelVO.getAscSort())){
                solrQuery.addSort(searchHotelVO.getAscSort(),SolrQuery.ORDER.asc);
            }
            if(EmptyUtils.isNotEmpty(searchHotelVO.getDescSort())){
                solrQuery.addSort(searchHotelVO.getDescSort(),SolrQuery.ORDER.desc);
            }

        }
        //商圈id不为空
        if(EmptyUtils.isNotEmpty(searchHotelVO.getFeatureIds())){
           StringBuffer buffer=new StringBuffer("(");
           int temps=1;
           String Areadarray[]=searchHotelVO.getFeatureIds().split(",");
           for (String featureIds:Areadarray){
              if (temps==1){
                 buffer.append("featureIds:"+"*,"+featureIds+",*");
              }else {
                  buffer.append(" OR featureIds:"+"*,"+featureIds+",*");
              }
              temps++;
           }
            buffer.append(")");
           solrQuery.addFilterQuery(buffer.toString());
        }
        //商圈不为空
        if(EmptyUtils.isNotEmpty(searchHotelVO.getTradeAreaIds())){
          StringBuffer buffer=new StringBuffer("(");
          int tempp=1;
          String Array[]=searchHotelVO.getTradeAreaIds().split(",");
          for(String str:Array){
              if(tempp==1){
                  buffer.append("tradingAreaIds:"+"*,"+str+",*");
              }else{
                  buffer.append(" OR tradingAreaIds:"+"*,"+str+",*");
              }
              tempp++;
          }
          buffer.append(")");
          solrQuery.addFilterQuery(buffer.toString());
        }
        //执行拼接的字符串
        if(EmptyUtils.isNotEmpty(stringBuffer.toString())){
            solrQuery.setQuery(stringBuffer.toString());
        }
        System.out.println("1231231231111");
        Page<ItripHotelVO> page=baseQuery.queryPage(solrQuery,pageNo,pageSize,ItripHotelVO.class);
        System.out.println("page在service中国"+page.toString());
        System.out.println("123123123");
        return page;
    }

    @Override
    public List<ItripHotelVO> searchcityid(Integer cityId, Integer pagesize) throws Exception {
        //查询所以
        SolrQuery solrQuery=new SolrQuery("*:*");
        if(EmptyUtils.isNotEmpty(cityId)){
            solrQuery.addFilterQuery("cityId:"+cityId);
        }else{
            return null;
        }
        List<ItripHotelVO> itripHotelVOS=baseQuery.queryList(solrQuery,pagesize,ItripHotelVO.class);
        return itripHotelVOS;
    }
}
