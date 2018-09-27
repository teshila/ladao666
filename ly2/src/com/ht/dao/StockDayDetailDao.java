package com.ht.dao;

import java.util.List;
import java.util.Map;

import com.ht.pojo.StockDayDetail;

public interface StockDayDetailDao {

	public void save(StockDayDetail stockDetail);

	public List selectStockDetailByParam(Map map);
}
