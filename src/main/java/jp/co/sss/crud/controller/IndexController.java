package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.LoginForm;
import jp.co.sss.crud.repository.EmployeeRepository;

/*
 *  ログインコントローラ
 */
@Controller
public class IndexController {

	@Autowired //社員情報用のリポジトリ
	EmployeeRepository employeeRepository;

	@Autowired //Httpセッション
	HttpSession session;
	
	// ログイン画面の表示
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(@ModelAttribute LoginForm loginForm) {
		session.invalidate(); //既存のセッションを破棄
		return "index";
	}

	// ログイン認証処理
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute LoginForm loginForm,BindingResult result,HttpSession session, Model model) {
		if (result.hasErrors()) {
			return "index";
		}
		int empId = loginForm.getEmpId(); //formから入力された社員IDを取り出す
		String empPass = loginForm.getEmpPass(); //formから入力されたパスワードを取り出す
		Employee employee = employeeRepository.findByEmpIdAndEmpPass(empId, empPass); //IDとパスワードが一致する社員がいるかDBから検索する

		if (employee != null) { //もし該当する社員がいたら
			EmployeeBean employeeBean = new EmployeeBean(); // セッションに保持するためのスコープ用Bean（EmployeeBean）を生成
			employeeBean.setEmpId(employee.getEmpId());
			employeeBean.setEmpName(employee.getEmpName());
			employeeBean.setAuthority(employee.getAuthority());
			session.setAttribute("user", employeeBean); // 生成したBeanをuserという名前でセッションに格納
			return "redirect:/list"; // 一覧へリダイレクト
		} else { //該当する社員がいなかったら
			model.addAttribute("errMessage", "社員ID、またはパスワードが間違っています。"); // エラーメッセージをModelに登録し、表示できるようにする
			return "index";
		}
	}

	// ログアウト処理
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout() {
		session.invalidate(); //セッションを破棄
		return "redirect:/";
	}

}
