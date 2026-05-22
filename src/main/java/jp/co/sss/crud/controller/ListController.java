package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class ListController {
	@Autowired
	EmployeeRepository repository;
	
//	@RequestMapping("/login")
//	public String login() {
//		return "/";
//	}
	
	@RequestMapping("/list")
	public String showAllEmployees(Model model) {
		model.addAttribute("list", repository.findAll());
		return "list/list";
	}

}
