package com.company.hello.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

	// 서비스 객체 생성과 이용 방법
	// 1. 객체 생성 연산자(new)를 이용해 서비스 객체를 생성하고 이용한다
//	MemberService memberService = new MemberService();
	// 2. 스프링 설정 파일을 이용해 스프링 컨테이너에 bean 객체를 생성, 의존 객체 자동 주입 방법을 이용한다.
	// src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml에서 bean 설정을 해준다.
	@Autowired
	MemberService memberService;

	@RequestMapping("/signUp")
	public String signUp() {
		return "sign_up";
	}

	@RequestMapping("/signIn")
	public String signIn() {
		return "sign_in";
	}

	@RequestMapping("/signUpConfirm")
	public String signUpConfirm(MemberVO memberVO) {
		// spring이 VO에 작성된 setter를 통해 알아서 값을 주입하기 때문에 반드시 setter 선언이 되어있어야 함.
		System.out.println("[MemberController] signUpConfirm()");

		System.out.println("m_id : " + memberVO.getM_id());
		System.out.println("m_pw : " + memberVO.getM_pw());
		System.out.println("m_mail : " + memberVO.getM_mail());
		System.out.println("m_phone : " + memberVO.getM_phone());

		memberService.signUpConfirm(memberVO);
		return "sign_up_ok";
	}

	@RequestMapping("/signInConfirm")
	public String signInConfirm(MemberVO memberVO) {
		System.out.println("[MemberController] signInConfirm()");

		// service의 signInConfirm 메서드가 반환하는 값의 타입이 MemberVO이기 때문에
		// MemberVO signInedMember에 담아줌.
		MemberVO signedInMember = memberService.signInConfirm(memberVO);
		if (signedInMember != null) {
			// id와 pw가 같아서 DAO > service를 타고 해당 유저의 정보가 잘 넘어왔을 경우 
			return "sign_in_ok";
		} else {
			// 입력값이 유저정보와 달라 아무런 vo도 가져오지 못했을 경우 
			return "sign_in_ng";
		}
	}
	
	@RequestMapping("/logOut")
	public String logOut() {
		MemberVO memberVO = new MemberVO();
		MemberVO signedInMember = memberService.signInConfirm(memberVO);
		return "sign_in";
	}
	
	
}
