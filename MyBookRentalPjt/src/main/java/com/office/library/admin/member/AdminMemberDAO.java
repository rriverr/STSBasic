package com.office.library.admin.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository // or @Component
public class AdminMemberDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	// jdbc 는 db에 문자열 사용해서 접근해야함
	// 세팅은 쉽찌만 사용은 어렵따
	// java code 내에서 문자열을 사용해 접근하는 건 좋은 선택이 아님
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public boolean isAdminMember(String a_m_id) {
		System.out.println("[AdminMemberDAO] isAdminMember()");
		String sql = "SELECT COUNT(*) FROM tbl_admin_member " + "WHERE a_m_id = ?";
		int result = jdbcTemplate.queryForObject(sql, Integer.class, a_m_id);
		// jdbcTemplate.queryForObject(sql문, ?에 들어갈 형태, 들어갈 데이터);

		if (result > 0) {
			return true; // 중복
		} else {
			return false; // 중복x
		}
	}

	public int insertAdminAccount(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberDAO] insertAdminAccount()");
		List<String> args = new ArrayList<String>();

		String sql = "INSERT INTO tbl_admin_member(";

		if (adminMemberVO.getA_m_id().equals("super admin")) {
			sql += "a_m_approval, ";
			args.add("1");
		}

		sql += "a_m_id, ";
		args.add(adminMemberVO.getA_m_id());

		sql += "a_m_pw, ";
		args.add(passwordEncoder.encode(adminMemberVO.getA_m_pw()));

		sql += "a_m_name, ";
		args.add(adminMemberVO.getA_m_name());

		sql += "a_m_gender, ";
		args.add(adminMemberVO.getA_m_gender());

		sql += "a_m_part, ";
		args.add(adminMemberVO.getA_m_part());

		sql += "a_m_position, ";
		args.add(adminMemberVO.getA_m_position());

		sql += "a_m_mail, ";
		args.add(adminMemberVO.getA_m_mail());

		sql += "a_m_phone, ";
		args.add(adminMemberVO.getA_m_phone());

		sql += "a_m_reg_date, a_m_mod_date) ";

		if (adminMemberVO.getA_m_id().equals("super admin")) {
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
			// admin일 경우 a_m_approval까지 포함해서 9개
		} else {
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
			// admin이 아닌 경우 a_m_approval이 포함되지 않아 8개
		}
		// INSERT INTO tbl_admin_member(a_m_approval, a_m_id, a_m_pw, a_m_name,
		// 								a_m_gender, a_m_part, a_m_position, a_m_mail, 
		//								a_m_phone, a_m_reg_date, a_m_mod_date)
		// a_m_reg_date와 a_m_mod_date는 실행 시점의 시각 저장.

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean loginConfirm(String a_m_id) {
		return true;
	}
}
