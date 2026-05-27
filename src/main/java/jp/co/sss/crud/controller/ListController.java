package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.crud.repository.EmployeeRepository;

/*
 *  社員一覧表示コントローラ
 */
@Controller
public class ListController {
	@Autowired //社員テーブル操作用リポジトリ
	EmployeeRepository repository;
	
	// 社員の一覧表示
	@RequestMapping("/list")
	public String showAllEmployees(Model model) {
		model.addAttribute("list", repository.findAll()); 
		// DBからすべての社員情報を取得し、listとしてModelに格納
		return "list/list";
	}
	
	// 社員名でのあいまい検索
	@RequestMapping("/list/empName") 
    public String showEmpListByNameLike(@RequestParam String empName, Model model) {
		model.addAttribute("list", repository.findByEmpNameContaining(empName)); 
		// 画面の入力値を含む社員をDBから検索し、listとしてModelに格納
		return "list/list";
		
    } 

}
