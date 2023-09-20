package com.office.library.admin.member;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

// @Component 써도 상관 없고 동일한 역할을 한다 
@Service
public class AdminMemberService {
	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;

	@Autowired
	AdminMemberDAO adminMemberDAO;

	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;

	// 회원가입
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
			return ADMIN_ACCOUNT_ALREADY_EXIST; 
		}
	}

	// 로그인
	public AdminMemberVO loginConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] loginConfirm()");
		AdminMemberVO loginedAdminMemberVO = adminMemberDAO.selectAdmin(adminMemberVO);
		if (loginedAdminMemberVO != null) {
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!");
		} else {
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!!!");
		}
		return loginedAdminMemberVO;
	}

	// 관리자 멤버 리스트
	public List<AdminMemberVO> listupAdmin() {
		System.out.println("[AdminMemberService] listupAdmin()");
		return adminMemberDAO.selectAdmins();
	}

	// 유저 권한 부여하기
	public void setAdminApproval(int a_m_no) {
		System.out.println("[AdminMemberService] setAdminApproval()");
		int result = adminMemberDAO.updateAdminAccount(a_m_no);
	}

	// 유저 정보 수정하기
	public int modifyAccountConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] modifyAccountConfirm");
		return adminMemberDAO.updateAdminAccount(adminMemberVO);
	}

	// 로그인한 멤버 가져오기
	public AdminMemberVO getLoginedAdminMemberVO(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] getLoginedAdminMemberVO");
		return adminMemberDAO.selectAdmin(adminMemberVO.getA_m_no());
	}

	// 비밀번호 찾기
	public int findPasswordConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] findPasswordConfirm()");
		AdminMemberVO selectedAdminMemberVO = adminMemberDAO.selectAdmin(adminMemberVO.getA_m_id(),
				adminMemberVO.getA_m_name(), adminMemberVO.getA_m_mail());
		int result = 0;

		if (selectedAdminMemberVO != null) {
			String newPassword = createNewPassword();
			result = adminMemberDAO.updatePassword(adminMemberVO.getA_m_id(), newPassword);
			if (result > 0) {
				sendNewPasswordByMail(adminMemberVO.getA_m_mail(), newPassword);
			}
		}
		return result;
	}

	// 새 패스워드 등록
	private String createNewPassword() {
		System.out.println("[AdminMemberService] createNewPassword()");
		char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());

		int index = 0;
		int length = chars.length;

		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);
			if (index % 2 == 0) {
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			} else {
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
			}
		}
		System.out.println("[AdminMemberService NEW PASSWORD] : " + stringBuffer.toString());
		return stringBuffer.toString();
	}

	// 패스워드 메일 전송
	private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		System.out.println("[AdminMemberService] sendNewPassword()");
		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//				mimeMessageHelper.setTo("playamongthestars000@gmail.com");
				mimeMessageHelper.setTo(toMailAddr);
				mimeMessageHelper.setSubject("[한국도서관] 비밀번호 안내입니다");
				mimeMessageHelper.setText("새 비밀번호: " + newPassword, true);
			}
		};
		javaMailSenderImpl.send(mimeMessagePreparator);
	}

}
