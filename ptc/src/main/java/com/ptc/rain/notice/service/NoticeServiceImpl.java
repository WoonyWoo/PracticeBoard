package com.ptc.rain.notice.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ptc.rain.notice.dto.NoticeDto;
import com.ptc.rain.notice.dto.PageList;
import com.ptc.rain.notice.dto.ResultDto;
import com.ptc.rain.notice.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeMapper noticeMapper;
	
	// 게시글 목록 조회
	@Override
	public List<NoticeDto> selectNoticeList(NoticeDto nd) throws Exception{
		
		return noticeMapper.selectNoticeList(nd);
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

	// 게시글 수정
	@Override
	public void updateNotice(NoticeDto noticeDto) throws Exception {
		
		try {
	    	noticeMapper.updateNotice(noticeDto);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
		
	}

	// 게시글 삭제
	@Override
	public void deleteNotice(int notiNo) throws Exception {

		try {
	    	noticeMapper.deleteNotice(notiNo);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
		
	}

	/* 게시글 목록 조회(페이징) */
	@Override
	public PageList<NoticeDto> selectNoticePageList(NoticeDto noticeDto) throws Exception {
		
		PageList<NoticeDto> selectNoticePageList = new PageList<NoticeDto>();
		selectNoticePageList.setPaging(noticeDto.getPaging());
		
		selectNoticePageList.setItemList(noticeMapper.selectNoticePageList(noticeDto));
		selectNoticePageList.setItemTotalCount(noticeMapper.selectNoticeListTotalCount());
		
		return selectNoticePageList;
	}

	@Override
	public Page<NoticeDto> findPaginated(Pageable pageable, NoticeDto nd) throws Exception {
		
		// 게시물 전체 리스트
		List<NoticeDto> notices =  noticeMapper.selectNoticeList(nd);
		
		int pageSize = pageable.getPageSize(); // 페이지당 갯수
		int currentPage = pageable.getPageNumber(); // 현재 페이지
		int startItem = currentPage * pageSize; // 보여지는 페이지의 게시물 시작 번호
		List<NoticeDto> list;
		
		if (notices.size() < startItem) { // 게시물 전체 갯수가 시작 아이템 순번보다 작으면 빈 리스트를 보내줌
			
			list = Collections.emptyList();
			
		} else {
			
			int toIndex = Math.min(startItem + pageSize, notices.size()); // 보여지는 페이지의 마지막 순번(시작 순번 + 페이지당 갯수 와 리스트 전체 사이즈 중 작은 것)
			list = notices.subList(startItem, toIndex); // 시작 순번부터 마지막 순번까지 자르기(subList: start부터 toIndex 이전까지의 요소들을 자름, 그래서 toIndex가 1 더 큼)
			
		}
		
		// PageImpl(List<T> content, Pageable pageable, long total)
		// PageRequst.of(int page, int size) - 페이지 번호(0부터 시작), 페이지당 데이터의 수
		// PageRequst.of(int page, int size, Sort.Direction direction, String ...props) - 페이지 번호, 페이지당 데이터의 수, 정렬 방향, 속성(칼럼)들
		// PageRequst.of(int page, int size, Sort sort) - 페이지 번호, 페이지당 데이터의 수, 정렬방향
		Page<NoticeDto> noticePage
			= new PageImpl<NoticeDto>(list, PageRequest.of(currentPage, pageSize), notices.size()); 
		
		return noticePage;
	}
	
	
	
	
	
}
