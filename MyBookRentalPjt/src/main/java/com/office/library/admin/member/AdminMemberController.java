package com.office.library.admin.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

	@Autowired
	AdminMemberService adminMemberService;

	@RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
	public String createAccountForm() {
		System.out.println("[AdminMemberController] createAccountForm");
		String nextPage = "admin/member/create_account_form";
		return nextPage;
	}

	// 주소창에 /admin/member/createAccountConfirm 쳐서 직접 접근하는 경우 post가 아닌 get 메서드로 실행됨
	// => 아래의 메서드가 실행되지 않아 sysout이 찍히지 않음
	// 리퀘스트 메서드를 GET으로 변경해서 다시 직접 접근하면 출력이 된다.
	//	@RequestMapping(value = "/createAccountConfirm", method = RequestMethod.POST)
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberController] createAccountConfirm");
		int result = adminMemberService.createAccountConfirm(adminMemberVO);
		String nextPage = "admin/member/create_account_ok";
		if (result <= 0) {
			nextPage = "admin/member/create_account_ng";
		}
		return nextPage;
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		String nextPage = "admin/member/login_form";
		return nextPage;
	}

	@GetMapping("admin")
	public String home() {
		String nextPage = "admin/home";
		return nextPage;
	}

	public String loginConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberController] loginConfirm");
		int result = adminMemberService.loginConfirm(adminMemberVO);
		String nextPage = "admin/login_ok";
		if (result <= 0) {
			nextPage = "admin/member/login_ng";
		}
		return nextPage;
	}
}
