package jp.co.sss.crud.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
@SessionAttributes("employeeForm")
public class RegistrationController {
	
	@Autowired
	EmployeeRepository repository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@RequestMapping("/regist/input")
	public String registInput(Model model) {
		model.addAttribute("employeeForm", new EmployeeForm());
		return "regist/regist_input";
	}
	
	@PostMapping("/regist/check")
	public String registCheck(@ModelAttribute EmployeeForm form, Model model) {
		model.addAttribute("employeeForm", form);
		return "regist/regist_check";
	}
	
	@PostMapping("/regist/back")
	public String registBack(@ModelAttribute EmployeeForm form) {
		return "regist/regist_input";
	}
	
	
	
	@PostMapping("/regist/complete")
	public String registComplete(@ModelAttribute EmployeeForm form) {
		Employee emp = new Employee();
		BeanUtils.copyProperties(form, emp, "empId");
		Department dept = departmentRepository.findById(form.getDeptId()).orElse(null);
		emp.setDepartment(dept);
		repository.save(emp);
		return "regist/regist_complete";
	}
	
	
	

}
