package com.ptc.rain.notice.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ptc.rain.notice.dto.NoticeCd;
import com.ptc.rain.notice.dto.NoticeDto;
import com.ptc.rain.notice.service.NoticeService;

@Controller
public class NoticeController {
	
	//private static final Logger Log = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private NoticeService noticeService;
	
	// 게시글 등록 화면 이동
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public ModelAndView moveRegist() throws Exception{
		
		ModelAndView mv = new ModelAndView("layout/noticeRegist");
		
		
		return mv;
	}
	
	// 게시글 목록 화면 이동
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView moveList() throws Exception{
		
		ModelAndView mv = new ModelAndView("test");
		
		List<NoticeDto> list = noticeService.selectNoticeList();
		
		mv.addObject("list", list);
		
		return mv;
	}
	
	// 게시글 등록
	@RequestMapping(value = "/registNotice", method = RequestMethod.POST)
	@ResponseBody
	public void noticeRegist(@RequestBody NoticeDto notice) throws Exception{
		
		noticeService.insertNotice(notice);
		
	}

}
