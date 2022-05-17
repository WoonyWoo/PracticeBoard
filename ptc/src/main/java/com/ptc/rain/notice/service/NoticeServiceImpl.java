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

	// 게시글 목록 조회
	@Override
	public List<NoticeDto> selectNoticeList() throws Exception{
		
		return noticeMapper.selectNoticeList();
	}
	
	// 게시글 1개 조회
	@Override
	public NoticeDto selectNoticeOne(int notiNo) throws Exception{
		
		return noticeMapper.selectNoticeOne(notiNo);
	}

	// 게시글 등록
	@Override
	public void insertNotice(NoticeDto noticeDto) throws Exception {
		
	    try {
	    	noticeMapper.insertNotice(noticeDto);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}

	@Override
	public void updateNotice(NoticeDto noticeDto) throws Exception {
		
		try {
	    	noticeMapper.updateNotice(noticeDto);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
		
	}

	@Override
	public void deleteNotice(int notiNo) throws Exception {

		try {
	    	noticeMapper.deleteNotice(notiNo);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
		
	}
	
	
	
}
