package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		System.out.println("[AdminMemberController] loginForm()");
		String nextPage = "admin/member/login_form";
		return nextPage;
	}

	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVO adminMemberVO, HttpSession session) {
		System.out.println("[AdminMemberController] loginConfirm");
		AdminMemberVO loginedMemberVO = adminMemberService.loginConfirm(adminMemberVO);
		String nextPage = "admin/member/login_ok";
		if(loginedMemberVO == null) {
			nextPage = "admin/member/login_ng";
		} else {
			session.setAttribute("loginedAdminMemberVO", loginedMemberVO);
			session.setMaxInactiveInterval(60*30);
			// 서버의 session에 저장되는 값들은 브라우저의 쿠키에 JSESSIONID로 저장된다.
			// session에 주입한 값들은 브라우저에선 확인할 수 없지만 난수화된 값 안에 저장은 되어있음.
		}
		return nextPage;
	}
	
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[AdminMemberController] logoutConfirm()");
		session.setAttribute("loginedAdminMemberVO", null);
		session.setMaxInactiveInterval(0);
		String nextPage = "redirect:/admin/member/loginForm";
		return nextPage;
	}
	
//	@GetMapping("/listupAdmin")
//	public ModelAndView listupAdmin() {
//		List<AdminMemberVO> adminMemberVOs = adminMemberService.listupAdmin();
//		ModelAndView modelAndView = new ModelAndView();
//		String nextPage = "admin/member/listup_admins";
//		modelAndView.setViewName(nextPage);
//		modelAndView.addObject(adminMemberVOs);
//		return modelAndView;
//	}
	
	
}
