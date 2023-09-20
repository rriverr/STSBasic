package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.office.library.book.BookVO;
import com.office.library.book.admin.util.UploadFileService;

@Controller
@RequestMapping("/book/admin")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	UploadFileService uploadFileService;

	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		System.out.println("[BookController] registerBookForm()");
		String nextPage = "admin/book/register_book_form";
		return nextPage;
	}

	@PostMapping("/registerBookConfirm")
	public String registerBookConfirm(BookVO bookVO, @RequestParam("file") MultipartFile file) {
		System.out.println("[BookController] registerBookConfirm()");
		String nextPage = "admin/book/register_book_ok";
		String savedFileName = uploadFileService.upload(file);
		if (savedFileName != null) {
			bookVO.setB_thumbnail(savedFileName);
			int result = bookService.registerBookConfirm(bookVO);
			if (result <= 0) {
				nextPage = "admin/book/register_book_ng";
			}
		} else {
			nextPage = "admin/book/register_book_ng";
		}
		return nextPage;
	}

	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVO bookVO, Model model) {
		System.out.println("[BookController] searchBookConfirm()");
		String nextPage = "admin/book/search_book";
		List<BookVO> bookVOs = bookService.searchBookConfirm(bookVO);
		model.addAttribute("bookVOs", bookVOs);
		return nextPage;
	}

	@GetMapping("/bookDetail")
	public String bookDetail(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[BookController] bookDetail()");
		String nextPage = "admin/book/book_detail";
		BookVO bookVO = bookService.bookDetail(b_no);
		model.addAttribute("bookVO", bookVO);
		return nextPage;
	}

	@GetMapping("/modifyBookForm")
	public String modifyBookForm(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[BookController] modifyBookForm()");
		String nextPage = "admin/book/modify_book_form";
		BookVO bookVO = bookService.modifyBookForm(b_no);
		model.addAttribute("bookVO", bookVO);
		return nextPage;
	}

	@PostMapping("/modifyBookConfirm")
	public String modifyBookConfirm(BookVO bookVO, @RequestParam("file") MultipartFile file) {
		System.out.println("[BookController] modifyBookConfirm()");
		String nextPage = "admin/book/modify_book_ok";
		if (!file.getOriginalFilename().equals("")) {
			String savedFileName = uploadFileService.upload(file);
			if (savedFileName != null) {
				bookVO.setB_thumbnail(savedFileName);
			}
		}
		int result = bookService.modifyBookConfirm(bookVO);
		if (result <= 0) {
			nextPage = "admin/book/modify_book_ng";
		}
		return nextPage;
	}

	@GetMapping("/deleteBookConfirm")
	public String deleteBookConfirm(@RequestParam("b_no") int b_no) {
		System.out.println("[BookController] deleteBookConfirm()");
		String nextPage = "admin/book/delete_book_ok";
		
		int result = bookService.deleteBookConfirm(b_no);
		if(result <= 0) {
			nextPage = "admin/book/delete_book_ng";
		}
		return nextPage;
	}
}
