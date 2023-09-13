package com.office.library.admin.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Component 써도 상관 없고 동일한 역할을 한다 
@Service
public class AdminMemberService {

	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;

	final static public int LOGIN_ALREADY_EXIST = 0;
	final static public int LOGIN_SUCCESS = 1;
	final static public int LOGIN_FAIL = -1;

	@Autowired
	AdminMemberDAO adminMemberDAO;

	public int createAccountConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] createAccountConfirm()");
		boolean isMember = adminMemberDAO.isAdminMember(adminMemberVO.getA_m_id());

		if (!isMember) {
			int result = adminMemberDAO.insertAdminAccount(adminMemberVO);
			if (result > 0) {
				return ADMIN_ACCOUNT_CREATE_SUCCESS; // 1
				// 그냥 return 1; 해도 상관은 없지만 이게 성공값이라는 걸 확실하게 명시하기 위해 상수값 사용
			} else {
				return ADMIN_ACCOUNT_CREATE_FAIL; // -1
			}
		} else {
			return ADMIN_ACCOUNT_ALREADY_EXIST; // 0
		}
	}

	public int loginConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] loginConfirm()");
		boolean isMember = adminMemberDAO.loginConfirm(adminMemberVO.getA_m_id());
		if (!isMember) {
			int result = adminMemberDAO.insertAdminAccount(adminMemberVO);
			if (result > 0) {
				return LOGIN_SUCCESS;
			} else {
				return LOGIN_FAIL;
			}
		} else {
			return LOGIN_ALREADY_EXIST;
		}
	}

}
