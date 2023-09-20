package com.office.library.book.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVO;

@Service
public class BookService {

	final static public int BOOK_ISBN_ALEADY_EXIST = 0;
	final static public int BOOK_REGISTER_SUCCESS = 1;
	final static public int BOOK_REGISTER_FAIL = -1;
	
	@Autowired
	BookDAO bookDAO;
	
	public int registerBookConfirm(BookVO bookVO) {
		System.out.println("[BookService] registerBookConfirm()");
		boolean isISBN = bookDAO.isISBN(bookVO.getB_isbn());
		
		if(!isISBN) {
			int result = bookDAO.insertBook(bookVO);
			
			if(result > 0) {
				return BOOK_REGISTER_SUCCESS;
			} else {
				return BOOK_REGISTER_FAIL;
			}
		} else {
			return BOOK_ISBN_ALEADY_EXIST;
		}
	}
	
	public List<BookVO> searchBookConfirm(BookVO bookVO) {
		System.out.println("[BookService] searchBookConfirm()");
		return bookDAO.selectBooksBySearch(bookVO);
	}
	
	public BookVO bookDetail(int b_no) {
		System.out.println("[BookService] bookDetail()");
		return bookDAO.selectBook(b_no);
	}
	
	public BookVO modifyBookForm(int b_no) {
		System.out.println("[BookService] modifyBookForm()");
		return bookDAO.selectBook(b_no);
	}
	
	public int modifyBookConfirm(BookVO bookVO) {
		System.out.println("[BookService] modifyBookConfirm()");
		int result = bookDAO.updateBook(bookVO);
		return result;
	}
	
	public int deleteBookConfirm(int b_no) {
		System.out.println("[BookService] deleteBookConfirm");
		int result = bookDAO.deleteBook(b_no);
		return result;
	}
	
	
}
