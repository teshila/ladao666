package com.ht.controller.stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.dao.StockDao;
import com.ht.pojo.Stock;

@Controller
public class StockController {
	
	
	@Autowired
	private StockDao stockDao;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/stockGrid00")
	@ResponseBody
	public Map getList(String page,String rows ){
		Map returnMap = new HashMap();
		Map paramMap = new HashMap();
		
		
		int start = Integer.valueOf(page);
		int pageSize = Integer.valueOf(rows);
		
		start = (start-1)* pageSize;
		//int end =  start*pageSize;
		
		
		paramMap.put("start",start);
		paramMap.put("size", pageSize);
		
		List<Stock> list = stockDao.selectStockByParam(paramMap);
		Integer total = stockDao.getTotalCount(paramMap);
		returnMap.put("total", total);
		returnMap.put("rows", list);
		return returnMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/stockGrid01")
	@ResponseBody
	public Map getList01(String page,String rows ){
		Map returnMap = new HashMap();
		Map paramMap = new HashMap();
		
		
		int start = Integer.valueOf(page);
		int pageSize = Integer.valueOf(rows);
		
		start = (start-1)* pageSize;
		//int end =  start*pageSize;
		
		
		paramMap.put("start",start);
		paramMap.put("size", pageSize);
		paramMap.put("orderType","1");
		
		List<Stock> list = stockDao.selectStockByParam(paramMap);
		Integer total = stockDao.getTotalCount(paramMap);
		returnMap.put("total", total);
		returnMap.put("rows", list);
		return returnMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/stockGrid02")
	@ResponseBody
	public Map getList02(String page,String rows ){
		Map returnMap = new HashMap();
		Map paramMap = new HashMap();
		
		
		int start = Integer.valueOf(page);
		int pageSize = Integer.valueOf(rows);
		
		start = (start-1)* pageSize;
		//int end =  start*pageSize;
		
		
		paramMap.put("start",start);
		paramMap.put("size", pageSize);
		paramMap.put("orderType",0);
		
		List<Stock> list = stockDao.selectStockByParam(paramMap);
		Integer total = stockDao.getTotalCount(paramMap);
		returnMap.put("total", total);
		returnMap.put("rows", list);
		return returnMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/stockGrid03")
	@ResponseBody
	public Map getList03(String page,String rows ){
		Map returnMap = new HashMap();
		Map paramMap = new HashMap();
		
		
		int start = Integer.valueOf(page);
		int pageSize = Integer.valueOf(rows);
		
		start = (start-1)* pageSize;
		//int end =  start*pageSize;
		
		
		paramMap.put("start",start);
		paramMap.put("size", pageSize);
		paramMap.put("orderType","-1");
		
		List<Stock> list = stockDao.selectStockByParam(paramMap);
		Integer total = stockDao.getTotalCount(paramMap);
		returnMap.put("total", total);
		returnMap.put("rows", list);
		return returnMap;
	}
}
