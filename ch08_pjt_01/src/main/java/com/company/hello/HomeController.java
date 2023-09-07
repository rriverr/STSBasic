package com.company.hello;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 어노테이션 명시를 통해 해당 클래스가 Conroller 빈 객체로 사용될 수 있도록 한다.
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// RequestMappin 어노테이션을 통해 home 메서드를 /로 매핑. 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		// Model 객체에 key : serverTime / value : formattedDate
		// 이 Model 객체는 이 메서드 실행 후 view에 전달된다. 
		
		return "home";
		// 이 메서드는 View에서 필요한 정보를 return해야하므로 "home"을 반환.
		// 이후 /WEB-INF/views/home.jsp 를 찾는 데 이용된다.
	}
	
}
