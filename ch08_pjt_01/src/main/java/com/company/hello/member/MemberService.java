package com.company.hello.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	@Autowired // new 어쩌구 저쩌구 안 해도 spring이 알아서 해줌
	MemberDAO memberDAO;

	public int signUpConfirm(MemberVO memberVO) {
		System.out.println("[MemberService] signUpConfirm()");

		System.out.println("m_id : " + memberVO.getM_id());
		System.out.println("m_pw : " + memberVO.getM_pw());
		System.out.println("m_mail : " + memberVO.getM_mail());
		System.out.println("m_phone : " + memberVO.getM_phone());

		memberDAO.insertMember(memberVO);
		return 0;
	}

}
