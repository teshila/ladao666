package com.ht.pojo;

public class Stock {

	private String code;
	private String name;
	private String prevclose;
	private String current_price;
	private String maxprice;
	private String minprice;
	private String open_price;
	private String marketType;
	private String stockPinYin;
	private String risePrice;
	//1、深圳创业板股票的代码是：300XXX 的股票    4621
	//2、而深圳中小板股票的代码是：002XXX 开头的股票  4614
	//3、上海主板的股票代码是：60XXXX 开头的股票  4353
	//4、深圳主板的股票代码是：000XXX 开头的股票  4609
	private Integer count;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrevclose() {
		return prevclose;
	}

	public void setPrevclose(String prevclose) {
		this.prevclose = prevclose;
	}

	public String getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(String current_price) {
		this.current_price = current_price;
	}

	public String getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(String maxprice) {
		this.maxprice = maxprice;
	}

	public String getMinprice() {
		return minprice;
	}

	public void setMinprice(String minprice) {
		this.minprice = minprice;
	}

	public String getOpen_price() {
		return open_price;
	}

	public void setOpen_price(String open_price) {
		this.open_price = open_price;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getStockPinYin() {
		return stockPinYin;
	}

	public void setStockPinYin(String stockPinYin) {
		this.stockPinYin = stockPinYin;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getRisePrice() {
		return risePrice;
	}

	public void setRisePrice(String risePrice) {
		this.risePrice = risePrice;
	}

	@Override
	public String toString() {
		return "Stock [code=" + code + ", name=" + name + ", prevclose=" + prevclose + ", current_price=" + current_price + ", maxprice=" + maxprice + ", minprice=" + minprice + ", open_price=" + open_price + ", marketType=" + marketType + ", stockPinYin=" + stockPinYin + ", risePrice=" + risePrice + ", count=" + count + "]";
	}


}
