package com.kh.quiz;

public interface QuizService {
	// interface에 작성한 메서드들은 추상메서드 => abstract
	public abstract QuizDO submitAnswer(QuizDO quizDO); 
}
