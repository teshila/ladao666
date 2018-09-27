package com.ht.dao;

import java.util.List;
import java.util.Map;

import com.ht.pojo.Stock;

public interface StockDao {

	public List<Stock> selectStockByParam(Map map);

	public Integer getTotalCount(Map map);

	public void save(Stock stock);

	public List<Stock> getStockInfo(Map orderType);
}
