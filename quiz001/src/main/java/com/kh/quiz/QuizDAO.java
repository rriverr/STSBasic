package com.kh.quiz;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuizDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public QuizDO submitAnswer(QuizDO answer) {
		// mapper에 적어준 정보를 쓴다.
		// ("mapper namespace.method", 전해줄 객체)
		return sqlSession.selectOne("quizMapper.submitAnswer", answer);
		// sql문 수행 - 문제를 찾으면 테이블의 데이터를 가져옴. 없으면 null
	}
}
