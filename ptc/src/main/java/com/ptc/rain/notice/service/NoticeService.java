package com.ptc.rain.notice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ptc.rain.notice.dto.NoticeDto;
import com.ptc.rain.notice.dto.PageList;
import com.ptc.rain.notice.dto.SearchDto;

public interface NoticeService {
	
	/* 게시글 목록 조회 */
	public List<NoticeDto> selectNoticeList(NoticeDto nd) throws Exception;
	
	/* 게시글 1개 조회 */
	public NoticeDto selectNoticeOne(int notiNo) throws Exception;
	
	/* 게시글 등록 */
	public void insertNotice(NoticeDto noticeDto) throws Exception;
	
	/* 게시글 수정 */
	public void updateNotice(NoticeDto noticeDto) throws Exception;

	/* 게시글 삭제 */
	public void deleteNotice(int notiNo) throws Exception;
	
	/* 게시글 목록 조회(페이징) */
	public PageList<NoticeDto> selectNoticePageList(SearchDto sd) throws Exception;
	
	/* 게시글 목록 조회(페이징, Pageable) */
	//public Page<NoticeDto> findPaginated(Pageable pageable, NoticeDto nd) throws Exception;
}
