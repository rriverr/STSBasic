package com.kh.quiz;

// myBatis 사용할땐 인터페이스 mapper같은 뭐시기가 필요
public interface QuizService {
	public abstract QuizDO submitAnswer(QuizDO quizDO);
}
