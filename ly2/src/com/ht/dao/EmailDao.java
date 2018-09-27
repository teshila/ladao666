package com.ht.dao;

import java.util.List;
import java.util.Map;

import com.ht.pojo.Email;

public interface EmailDao {

	public List<Email> selectAll(Map map);
}
