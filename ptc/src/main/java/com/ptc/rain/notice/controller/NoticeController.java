package com.ptc.rain.notice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ptc.rain.notice.dto.NoticeDto;
import com.ptc.rain.notice.dto.PageList;
import com.ptc.rain.notice.dto.PagingDto;
import com.ptc.rain.notice.dto.SessionConst;
import com.ptc.rain.notice.dto.UserDto;
import com.ptc.rain.notice.service.NoticeService;

@RestController
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private NoticeService noticeService;
	
	// 로그인
	@PostMapping("/login")
	public String login(HttpServletRequest req, HttpServletResponse res) {
		
		UserDto usrDto = new UserDto();
		usrDto.setUsrId("rain");
		usrDto.setPasswd("123");
		
		// 세션 매니저를 통해 세션 생성 및 회원정보 보관
		// 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
		HttpSession session = req.getSession(true);
		session.setAttribute(SessionConst.LOGIN_USR, usrDto);
		
		return "/listPage";
	}
	
	// 로그아웃
	@PostMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res) {
		
		// 세션이 있으면 있는 세션 반환, 없으면 null 반환
		HttpSession session = req.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		
		return "/listPage";
	}
	
	// 게시글 등록 화면 이동
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public ModelAndView moveRegist(@RequestParam("page") Optional<Integer> page) throws Exception{
		
		ModelAndView mv = new ModelAndView("layout/noticeRegist");
		
		int currentPage = page.orElse(1);
		
		mv.addObject("page", currentPage);
		
		return mv;
	}
	
	// 게시글 목록 화면 이동
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView moveList(@RequestBody(required = false) NoticeDto nd) throws Exception{
		
		ModelAndView mv = new ModelAndView("layout/noticeList");
		
		List<NoticeDto> list = noticeService.selectNoticeList(nd);
		
		
		mv.addObject("list", list);
		
		return mv;
	}
	
	// 게시글 상세 화면 이동
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView moveDetail(@RequestParam int notiNo, 
			@RequestParam(required = false) Optional<Integer> page, HttpServletRequest req) throws Exception{
		
		ModelAndView mv = new ModelAndView("layout/noticeDetail");
		
		NoticeDto result = noticeService.selectNoticeOne(notiNo);
		
		int currentPage = page.orElse(1);
		
		//HttpSession session = req.getSession(true);
		//session.setAttribute("notiNo", notiNo);
		
		mv.addObject("notice", result);
		mv.addObject("page", currentPage);
		
		return mv;
	}
	
	// 게시글 수정 화면 이동
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView moveUpdate(@RequestParam int notiNo, @RequestParam(required = false) Optional<Integer> page) throws Exception{
		
		ModelAndView mv = new ModelAndView("layout/noticeUpdate");
		
		NoticeDto result = noticeService.selectNoticeOne(notiNo);
		
		int currentPage = page.orElse(1);
		
		mv.addObject("notice", result);
		mv.addObject("page", currentPage);
		
		return mv;
	}
	
	// 게시글 등록
	@RequestMapping(value = "/registNotice", method = RequestMethod.POST)
	public void noticeRegist(@RequestBody NoticeDto notice, Model model) throws Exception{
		
		NoticeDto nd = new NoticeDto();
		nd = notice;
		
		noticeService.insertNotice(nd);
		
	}
	
	// 게시글 수정
	@RequestMapping(value = "/updateNotice", method = RequestMethod.POST)
	public void noticeUpdate(@RequestBody NoticeDto notice) throws Exception{
		
		NoticeDto nd = new NoticeDto();
		nd = notice;
		
		noticeService.updateNotice(nd);
		
	}
	
	// 게시글 삭제
	@RequestMapping(value = "/deleteNotice", method = RequestMethod.POST)
	public void noticeDelete(@RequestBody NoticeDto notice) throws Exception{
		
		NoticeDto nd = new NoticeDto();
		nd = notice;
		
		noticeService.deleteNotice(nd.getNotiNo());
		
	}
	
	// 게시글 목록 조회(페이징)
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public ModelAndView selectNoticeList(@RequestParam(required = false) Optional<Integer> page) throws Exception{
		
		ModelAndView mv = new ModelAndView("layout/noticeList");
		
		PagingDto pg = new PagingDto();
		
		pg.setPerPage(5); // 페이지당 게시물 수 5개
		pg.setCurrentPage(page.orElse(1));
			
		NoticeDto noticeDto = new NoticeDto();
		noticeDto.setPaging(pg);
		
		PageList<NoticeDto> res = noticeService.selectNoticePageList(noticeDto);
		
		mv.addObject("list", res.getItemList());
		mv.addObject("page", res.getPaging());
		
		return mv;
	}
	
	// 게시글 목록 조회(Pageable) - 전체 조회라서 속도 이슈
	@RequestMapping(value = "/listNotices", method = RequestMethod.GET)
	public ModelAndView listNotices(@RequestParam(required = false) NoticeDto nd, 
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, ModelAndView mv) throws Exception {
		
		// orElse: Optional 클래스의 기능, 값이 null이면 대체 값으로 넣음
		int currentPage = page.orElse(1); // 현재 페이지(Default) : 1 페이지
		int pageSize = size.orElse(5); // 페이지당 게시물 수(Default) : 5 개
		
		// noticePage 정보: content(데이터 목록), number(현재 페이지), size(총 데이터 갯수), sort(정렬 정보), totalPages(총 페이지 수)
		Page<NoticeDto> noticePage = noticeService.findPaginated(PageRequest.of(currentPage - 1, pageSize), nd); // -1을 하는 이유는 index가 0부터 시작하기 때문
		
		mv.addObject("noticePage", noticePage); // 게시물 리스트 데이터
		// mv.addObject("currentPage", currentPage);
		
		int totalPages = noticePage.getTotalPages();
		if (totalPages > 0) {
			// IntStream.boxed().collect(Collectors.toList()) - IntStream을 List로 변환
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages) // IntStream.rangeClosed(start, end): start부터 end까지 차례대로 반복 
					.boxed() // int 자체로는 Collection에 못 담기 때문에 Integer 클래스로 변환하여 List<Integer> 클래스로 담기 위해 사용(int -> Integer)
					.collect(Collectors.toList()); // Collection을 List로 변환
			mv.addObject("pageNumbers", pageNumbers); 
		}
		
		mv.setViewName("layout/noticeListPage"); // 경로 설정
		
		return mv;
	}

}
