package com.ptc.rain.notice.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ptc.rain.notice.dto.NoticeDto;
import com.ptc.rain.notice.dto.NoticeFileDto;
import com.ptc.rain.notice.dto.PageList;
import com.ptc.rain.notice.dto.PagingDto;
import com.ptc.rain.notice.dto.SearchDto;
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
			@RequestParam Optional<Integer> page
			,@RequestParam(required = false, defaultValue = "srchTtl") String srchTyp
			,@RequestParam(required = false, defaultValue = "") String keyword) throws Exception{
		
		ModelAndView mv = new ModelAndView("layout/noticeDetail");

		int currentPage = page.orElse(1);
		
		SearchDto sd = new SearchDto(); // 검색 데이터
		sd.setSrchTyp(srchTyp);
		sd.setKeyword(keyword);
		
		NoticeDto notice = noticeService.selectNoticeOne(notiNo);
		
		List<NoticeFileDto> fileList = noticeService.selectNoticeFileList(notiNo);
		
		mv.addObject("notice", notice);
		mv.addObject("page", currentPage);
		mv.addObject("srchData", sd);
		mv.addObject("fileList", fileList);
		
		return mv;
	}
	
	// 게시글 수정 화면 이동
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView moveUpdate(@RequestParam int notiNo
			,@RequestParam Optional<Integer> page
			,@RequestParam(required = false, defaultValue = "srchTtl") String srchTyp
			,@RequestParam(required = false, defaultValue = "") String keyword) throws Exception{
		
		ModelAndView mv = new ModelAndView("layout/noticeUpdate");
		
		SearchDto sd = new SearchDto(); // 검색 데이터
		sd.setSrchTyp(srchTyp);
		sd.setKeyword(keyword);
		
		NoticeDto result = noticeService.selectNoticeOne(notiNo);
		
		int currentPage = page.orElse(1);
		
		List<NoticeFileDto> fileList = noticeService.selectNoticeFileList(notiNo);
		
		mv.addObject("notice", result);
		mv.addObject("page", currentPage);
		mv.addObject("srchData", sd);
		mv.addObject("fileList", fileList);
		
		return mv;
	}
	
	// 게시글 등록(파일 포함)
	@RequestMapping(value = "/registNotice", method = RequestMethod.POST)
	public int noticeRegist(@RequestPart(value = "key") NoticeDto notice, 
			@RequestPart(value = "file", required = false) MultipartFile[] file) throws Exception{
		
		NoticeDto nd = new NoticeDto();
		nd = notice;
		
		boolean nResult =  noticeService.insertNotice(nd, file);
		if(nResult == false) {
			System.out.print("게시물 등록 실패");
			return 0;
		}
		
		int notiNo = nd.getNotiNo();
		
		return notiNo;
		
	}
	
	// 게시글 수정
	@RequestMapping(value = "/updateNotice", method = RequestMethod.POST)
	public void noticeUpdate(@RequestPart(value = "key") NoticeDto notice,
			@RequestPart(value = "file", required = false) MultipartFile[] file) throws Exception{
		
		NoticeDto nd = new NoticeDto();
		nd = notice;
		
		boolean nResult = noticeService.updateNotice(nd, file);
		if(nResult == false) {
			System.out.print("게시물 수정 실패");
		}
		
	}
	
	// 게시글 삭제
	@RequestMapping(value = "/deleteNotice", method = RequestMethod.POST)
	public void noticeDelete(@RequestBody NoticeDto notice) throws Exception{
		
		NoticeDto nd = new NoticeDto();
		nd = notice;
		
		int notiNo = nd.getNotiNo();
		int fileId = nd.getFileId();
		
		if(notiNo != 0 && fileId != 0) {
			
			boolean nResult = noticeService.deleteNotice(nd);
			if(nResult == false) {
				System.out.print("게시물 수정 실패");
			}
			
		}else {
			System.out.print("ID 전송 실패");
		}
		
	}
	
	// 게시글 목록 조회(페이징)
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public ModelAndView selectNoticeList(
			@RequestParam Optional<Integer> page
			,@RequestParam(required = false, defaultValue = "srchTtl") String srchTyp
			,@RequestParam(required = false, defaultValue = "") String keyword) throws Exception{
		
		ModelAndView mv = new ModelAndView("layout/noticeList");
		
		PagingDto pg = new PagingDto();
		SearchDto sd = new SearchDto();
		
		sd.setSrchTyp(srchTyp);
		sd.setKeyword(keyword);
		
		pg.setPerPage(5); // 페이지당 게시물 수 5개
		pg.setCurrentPage(page.orElse(1));
		
		//NoticeDto noticeDto = new NoticeDto();
		//noticeDto.setPaging(pg);
		
		sd.setPaging(pg);
		
		PageList<NoticeDto> res = noticeService.selectNoticePageList(sd);
		
		mv.addObject("list", res.getItemList()); // 게시물 목록 데이터
		mv.addObject("page", res.getPaging()); // 페이징 데이터
		mv.addObject("srchData", sd); // 검색 데이터
		
		return mv;
	}
	
	// 파일 다운로드
	@RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
	public void fileDownload(@RequestParam int fileId , HttpServletResponse res) throws Exception {
		// GET으로만 동작함
		
		NoticeFileDto fileInfo = noticeService.selectNoticeFileDetail(fileId);
		if(fileInfo == null || "Y".equals(fileInfo.getDelYn())){
			throw new RuntimeException("파일 정보를 찾을 수 없습니다.");
		}
		
		String uploadDate = fileInfo.getRegDtime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dd = df.parse(uploadDate);
		uploadDate = new SimpleDateFormat("yyMMdd").format(dd); // yyMMdd 형태로 날짜 변경
		
		String uploadPath = Paths.get("C:","Users","rain","git","PracticeNotice","ptc","src","main","webapp","upload",uploadDate).toString(); // 파일이 저장되어있는 경로
		
		String filename = fileInfo.getOriginalName(); // 원본 파일명
		File file = new File(uploadPath , fileInfo.getSaveName()); // 저장되어있는 파일 가져오기
		
		try {
			
			byte[] data = FileUtils.readFileToByteArray(file); // 파일 정보(file)를 파라미터로 전달 받아서 실제 파일 데이터를 byte[]형태로 변환
			res.setContentType("application/octet-stream"); // 콘텐츠 타입 설정
			res.setContentLength(data.length); // 콘텐츠 길이 설정
			res.setHeader("Content-Transfer-Encoding", "binary"); // 인코딩
			res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";"); // UTF-8로 인코딩(안하면 파일을 다운로드하면 이상한 파일명이 됨)
			
			res.getOutputStream().write(data); // 파일 다운로드 시작
			res.getOutputStream().flush(); // 파일 다운로드 완료
			res.getOutputStream().close(); // 버퍼 정리
			
		}catch (Exception e) {
			throw new RuntimeException("시스템에 문제가 발생하였습니다.");
		}
	}
	
	
	// 게시글 목록 조회(Pageable) - 전체 조회라서 속도 이슈
	/*@RequestMapping(value = "/listNotices", method = RequestMethod.GET)
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
	}*/

}
