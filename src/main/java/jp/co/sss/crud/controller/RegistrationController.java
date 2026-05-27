package jp.co.sss.crud.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

/**
 * 新規社員登録コントローラ
 */
@Controller
@SessionAttributes("employeeForm")// "employeeForm"をセッションに保存し、画面間で値を引き継げるようにする
public class RegistrationController {
	
	@Autowired  // 社員登録用のリポジトリ
	EmployeeRepository repository;
	
	@Autowired  // 部署情報用のリポジトリ
	DepartmentRepository departmentRepository;
	
	
	//新規社員登録　入力ページ
	@RequestMapping("/regist/input")
	public String registInput(Model model) {
		EmployeeForm employeeForm = new EmployeeForm(); //画面の入力値を格納するためのFormクラスのインスタンス（空の器）を作成
		employeeForm.setGender(1); //genderの初期値を１（男性）に指定
		employeeForm.setAuthority(1); //authorityの初期値を１（一般）に指定
		model.addAttribute("employeeForm", employeeForm); //作成したフォームオブジェクトをModelに登録
		return "regist/regist_input"; //新規登録の入力画面（HTML）を呼び出して表示
	}
	
	//新規社員登録　確認ページ
	@PostMapping("/regist/check")
	public String registCheck(@Validated @ModelAttribute EmployeeForm form,BindingResult error, Model model) {
		if (error.hasErrors()) {
            // エラーがある場合は、入力画面に戻る
            return "regist/regist_input";
        }
		
		model.addAttribute("employeeForm", form); //入力された値をModelに格納し確認画面で表示できるようにする
		return "regist/regist_check";
	}
	
	//新規社員登録　確認ページから戻る
	@PostMapping("/regist/back")
	public String registBack(@ModelAttribute EmployeeForm form) { //@ModelAttributeにより送信された値を自動的にセッション内に上書き保存
		return "regist/regist_input";
	}
	
	
	//新規社員登録　登録ページ
	@PostMapping("/regist/complete")
	public String registComplete(@ModelAttribute("employeeForm") EmployeeForm form, Model model) {
		Employee emp = new Employee(); //// DB保存用のempエンティティを生成
		BeanUtils.copyProperties(form, emp, "empId"); //formからempへデータを一括でコピー
		Department dept = new Department(); //社員に紐づけるためのdeptエンティティを生成
		dept.setDeptId(form.getDeptId()); //form.getDeptId()をdeptエンティティにセット
		emp.setDepartment(dept); //部署IDをセットしたdeptエンティティをempエンティティにセット＝社員と部署の外部参照の紐づけ
		emp=repository.save(emp); //saveメソッドを呼び出してDBに社員情報を保存
		return "regist/regist_complete";
	}
	
}
