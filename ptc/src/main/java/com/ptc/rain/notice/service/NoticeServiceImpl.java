package com.ptc.rain.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptc.rain.notice.dto.NoticeDto;
import com.ptc.rain.notice.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeMapper noticeMapper;

	@Override
	public List<NoticeDto> selectNoticeList() throws Exception{
		
		return noticeMapper.selectNoticeList();
	}

	@Override
	public void insertNotice(NoticeDto noticeDto) throws Exception {
		
	    try {
	    	noticeMapper.insertNotice(noticeDto);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	
	
}
