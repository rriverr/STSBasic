package com.kh.quiz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuizController {
	@Autowired
	private QuizService quizService;

	@RequestMapping("/quizSubmitted")
	public String answerSubmit(QuizDO answeredQuestion, Model model, HttpServletRequest request) {
		QuizDO quizDO = quizService.submitAnswer(answeredQuestion);
		HttpSession session = request.getSession();
		if (quizDO == null) {
			return "error";
		} else {
			return "index";
		}
	}
}
