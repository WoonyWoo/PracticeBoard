package com.ptc.rain.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ptc.rain.notice.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
	
	public List<NoticeDto> selectNoticeList() throws Exception;
	
	public NoticeDto selectNoticeOne(int notiNo) throws Exception;
	
	public void insertNotice(NoticeDto noticeDto) throws Exception;
	
	public void updateNotice(NoticeDto noticeDto) throws Exception;

	public void deleteNotice(int notiNo) throws Exception;
}
