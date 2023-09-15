package com.office.library.admin.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
		// a_m_gender, a_m_part, a_m_position, a_m_mail,
		// a_m_phone, a_m_reg_date, a_m_mod_date)
		// a_m_reg_date와 a_m_mod_date는 실행 시점의 시각 저장.

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public AdminMemberVO selectAdmin(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberDAO] selectAdmin()");

		String sql = "SELECT * FROM tbl_admin_member " + "WHERE a_m_id = ? AND a_m_approval > 0";
		// 입력한 아이디가 db의 아이디와 일치하는 행을 찾고, 권한이 있는지 확인한다(approval = 1)
		// 권한이 없을 경우(approval = 0)의 행은 불러오지 않음 => list size=0 =>
		// IndexOutOfBoundException

		List<AdminMemberVO> adminMemberVOs = new ArrayList<AdminMemberVO>();

		try {
			adminMemberVOs = jdbcTemplate.query(sql, new RowMapper<AdminMemberVO>() {
				// RowMapper는 spring에서 제공하는 걸 사용!!!
				@Override
				public AdminMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					// rowmapper를 구현한 익명 클래스
					// rs, rowNum 자동으로 채워짐
					// => 행 하나하나를 가져와 매핑하는 기능
					AdminMemberVO adminMemberVO = new AdminMemberVO();
					adminMemberVO.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVO.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVO.setA_m_id(rs.getString("a_m_id"));
					adminMemberVO.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVO.setA_m_name(rs.getString("a_m_name"));
					adminMemberVO.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVO.setA_m_part(rs.getString("a_m_part"));
					adminMemberVO.setA_m_position(rs.getString("a_m_position"));
					adminMemberVO.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVO.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVO.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVO.setA_m_mod_date(rs.getString("a_m_mod_date"));
					return adminMemberVO;
				}
			}, adminMemberVO.getA_m_id());
			if (!passwordEncoder.matches(adminMemberVO.getA_m_pw(), adminMemberVOs.get(0).getA_m_pw())) {
				// matches : 암호화된 비밀번호를 복호화해서 입력된 비밀번호와 비교 연산하고 결과를 반환한다
				// 근데 실무에선 보통 이미 암호화+저장된 데이터를 다시 복호화하지 않음.
				adminMemberVOs.clear();
				// 복호화&비교 결과 일치하지 않을 경우 list를 clear
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 어차피 아이디로 검색해서 1명만 불러와질텐데 왜 굳이 list로 뽑는 거지...
		return adminMemberVOs.size() > 0 ? adminMemberVOs.get(0) : null;
	}

	public List<AdminMemberVO> selectAdmins() {
		System.out.println("[AdminMemberDAO] selectAdmins()");
		String sql = "SELECT * FROM tbl_admin_member";

		List<AdminMemberVO> adminMemberVOs = new ArrayList<AdminMemberVO>();

		try {
			adminMemberVOs = jdbcTemplate.query(sql, new RowMapper<AdminMemberVO>() {
				@Override
				public AdminMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVO adminMemberVO = new AdminMemberVO();
					adminMemberVO.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVO.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVO.setA_m_id(rs.getString("a_m_id"));
					adminMemberVO.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVO.setA_m_name(rs.getString("a_m_name"));
					adminMemberVO.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVO.setA_m_part(rs.getString("a_m_part"));
					adminMemberVO.setA_m_position(rs.getString("a_m_position"));
					adminMemberVO.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVO.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVO.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVO.setA_m_mod_date(rs.getString("a_m_mod_date"));
					return adminMemberVO;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		return adminMemberVOs;
	}

	public int updateAdminAccount(int a_m_no) {
		System.out.println("[AdminMemberDAO] updateAdminAccount()");
		String sql = "UPDATE tbl_admin_member SET " + "a_m_approval = 1 " + "WHERE a_m_no = ? ";
		int result = -1;
		try {
			result = jdbcTemplate.update(sql, a_m_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
