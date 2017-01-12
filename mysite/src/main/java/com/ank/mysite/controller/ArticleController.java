package com.ank.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/article/")
public class ArticleController {

	@RequestMapping(name = "edit")
	public String edit() {
		return "article/edit";
	}

}
