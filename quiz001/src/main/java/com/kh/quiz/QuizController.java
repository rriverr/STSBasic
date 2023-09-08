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
	
	@RequestMapping("/")
	public String toMainPage() {
		return "index";
	}
	
	@RequestMapping("quizSubmitted")
	public String answerSubmit(QuizDO answer, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("1"));
		QuizDO quizDO = quizService.submitAnswer(answer); // 테이블에 있는 문제 번호일 경우 QuizDO데이터가 채워져있음. 반대의 경우 null
		if(quizDO == null) {
			// Model , HttpServletRequest 둘 중 뭘 써도 상관 없는데 보통 spring은 Model 쓰는 편
			model.addAttribute("msg", "Wrong answer");
			return "error";
		} else {
			session.setAttribute("answer", quizDO);
			return "index";
		}
	}
}
