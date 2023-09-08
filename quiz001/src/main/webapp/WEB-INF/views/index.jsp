<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>This is a page for you to solve some quizes</title>
</head>
<body>
	<c:if test="${empty answer}">
		<form action="quizSubmitted" method="post">
			<label>1. 1 + 1 = </label>
			<input type="hidden" name="question" value="1"> <!-- value에 1을 넣어 1번 문제임을 고정적으로 명시 -->
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
		<form action="quizSubmitted" method="post">
			<label>2. 5 + 5 = </label> <input type="hidden" name="question"
				value="2">
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
		<form action="quizSubmitted" method="post">
			<label>3. 1 + 3 = </label> <input type="hidden" name="question"
				value="3">
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
		<form action="quizSubmitted" method="post">
			<label>4. 3 - 1 = </label> <input type="hidden" name="question"
				value="4">
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
		<form action="quizSubmitted" method="post">
			<label>5. 5 - 4 = </label> <input type="hidden" name="question"
				value="5">
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
		<form action="quizSubmitted" method="post">
			<label>6. 15 - 13 = </label> <input type="hidden" name="question"
				value="6">
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
		<form action="quizSubmitted" method="post">
			<label>7. 3 + 5 = </label> <input type="hidden" name="question"
				value="6">
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
		<form action="quizSubmitted" method="post">
			<label>8. 10 - 9 = </label> <input type="hidden" name="question"
				value="6">
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
		<form action="quizSubmitted" method="post">
			<label>9. 5 + 7 = </label> <input type="hidden" name="question"
				value="9">
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
		<form action="quizSubmitted" method="post">
			<label>10. 14 + 5 = </label> <input type="hidden" name="question"
				value="10">
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>
	</c:if>
	
	
	<c:if test="${!empty answer}">
		<h1>Correct answer.</h1>
	</c:if>
</body>
</html>