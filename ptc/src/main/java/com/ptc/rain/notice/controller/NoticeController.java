package com.ptc.rain.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ptc.rain.notice.dto.NoticeDto;
import com.ptc.rain.notice.service.NoticeService;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String noticeRegist() throws Exception{
		return "layout/noticeRegist";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView noticeList() throws Exception{
		
		ModelAndView mv = new ModelAndView("test");
		
		List<NoticeDto> list = noticeService.selectNoticeList();
		
		mv.addObject("list", list);
		
		return mv;
	}

}
