package com.office.library.admin.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	@RequestMapping(value="/createAccountForm", method= RequestMethod.GET)
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
	public String createAccountConfirm() {
		System.out.println("[AdminMemberController] createAccountConfirm");
		return null;
	}
}
