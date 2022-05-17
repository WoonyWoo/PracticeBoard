package com.ptc.rain.notice.service;

import java.util.List;
import com.ptc.rain.notice.dto.NoticeDto;

public interface NoticeService {
	
	public List<NoticeDto> selectNoticeList() throws Exception;

	public NoticeDto selectNoticeOne(int notiNo) throws Exception;
	
	public void insertNotice(NoticeDto noticeDto) throws Exception;
	
	public void updateNotice(NoticeDto noticeDto) throws Exception;
	
	public void deleteNotice(int notiNo) throws Exception;
}
