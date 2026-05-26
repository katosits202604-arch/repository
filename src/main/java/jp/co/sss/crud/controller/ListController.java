package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class ListController {
	@Autowired
	EmployeeRepository repository;
	

	@RequestMapping("/list")
	public String showAllEmployees(Model model) {
		model.addAttribute("list", repository.findAll());
		return "list/list";
	}
	
	@RequestMapping("/list/empName") 
    public String showEmpListByNameLike(@RequestParam String empName, Model model) {
		model.addAttribute("list", repository.findByEmpNameContaining(empName)); 
		return "list/list";
		
    } 

}
