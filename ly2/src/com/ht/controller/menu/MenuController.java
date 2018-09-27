package com.ht.controller.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.dao.StockDao;

@Controller
public class MenuController {

	@RequestMapping(value = { "/", "/index" })
	public String doIndex(HttpServletRequest request) {
		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/data00")
	public String stockManage() {
		return "00";
	}

	@RequestMapping("/data01")
	public String data01() {
		return "day/01";
	}

	@RequestMapping("/data02")
	public String data02() {
		return "day/02";
	}

	@RequestMapping("/data03")
	public String data03() {
		return "day/03";
	}

	@RequestMapping("/data04")
	public String data04() {
		return "week/04";
	}

	@RequestMapping("/data05")
	public String data05() {
		return "week/05";
	}

	@RequestMapping("/data06")
	public String data06() {
		return "week/06";
	}

	@RequestMapping("/data07")
	public String data07() {
		return "month/07";
	}

	@RequestMapping("/data08")
	public String data08() {
		return "month/08";
	}

	@RequestMapping("/data09")
	public String data09() {
		return "month/09";
	}

	@RequestMapping("/email")
	public String email() {
		return "email";
	}

}
