package com.ptc.rain.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ptc.rain.notice.dto.NoticeDto;
import com.ptc.rain.notice.dto.SearchDto;

@Mapper
public interface NoticeMapper {
	
	/* 게시글 목록 조회 */
	//public List<NoticeDto> selectNoticeList(NoticeDto nd) throws Exception;
	public List<NoticeDto> selectNoticeList(NoticeDto nd) throws Exception;
	
	/* 게시글 1개 조회 */
	public NoticeDto selectNoticeOne(int notiNo) throws Exception;
	
	/* 게시글 등록 */
	public void insertNotice(NoticeDto noticeDto) throws Exception;
	
	/* 게시글 수정 */
	public void updateNotice(NoticeDto noticeDto) throws Exception;

	/* 게시글 삭제 */
	public void deleteNotice(int notiNo) throws Exception;
	
	/* 게시글 전체 갯수 조회 */
	public int selectNoticeListTotalCount(SearchDto sd) throws Exception;
	
	/* 게시글 목록 조회(페이징) */
	public List<NoticeDto> selectNoticePageList(SearchDto sd) throws Exception;
}
