package com.ht.controller.email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.dao.EmailDao;

@Controller
public class EmailController {

	
	@Autowired
	private EmailDao emailDao;
	
	@RequestMapping("getEmails")
	@ResponseBody
	public Map getEmails(){
		List list = emailDao.selectAll(null);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	
}
