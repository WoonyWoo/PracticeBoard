package com.ptc.rain.notice.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ptc.rain.notice.dto.NoticeDto;
import com.ptc.rain.notice.dto.NoticeFileDto;
import com.ptc.rain.notice.dto.PageList;
import com.ptc.rain.notice.dto.ResultDto;
import com.ptc.rain.notice.dto.SearchDto;
import com.ptc.rain.notice.mapper.NoticeFileMapper;
import com.ptc.rain.notice.mapper.NoticeMapper;
import com.ptc.rain.notice.util.FileUtils;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private NoticeFileMapper noticeFileMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
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
	public boolean insertNotice(NoticeDto noticeDto) throws Exception {
		int queryResult = 0;
		
	    try {
	    	queryResult = noticeMapper.insertNotice(noticeDto);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    return (queryResult == 1)? true : false;
	}
	
	// 게시글 등록(파일 포함)
	@Override
	public boolean insertNotice(NoticeDto noticeDto, MultipartFile[] files) throws Exception {
		int isRegistedNotice = 0;
		int isRegistedFile = 0;
		
		try {
			
			isRegistedNotice = noticeMapper.insertNotice(noticeDto);
			if(isRegistedNotice == 0) {
				System.out.printf("게시물 저장 실패");
				return false;
			}
			
		}catch(DataAccessException de){
			de.printStackTrace();
			System.out.printf("데이터베이스 처리과정에 문제 생김");
		}catch(Exception e) {
			e.printStackTrace();
		    System.out.printf("시스템 문제");
		}
		
		if(files != null) { // file이 있다면
			
			List<NoticeFileDto> fileList = fileUtils.uploadFiles(files, noticeDto.getNotiNo()); // 파일 업로드 요청
			if(CollectionUtils.isEmpty(fileList) == false) { // 파일 리스트가 존재한다면
				
				try {
					
					isRegistedFile = noticeFileMapper.insertNoticeFile(fileList); // 파일 정보 DB에 저장
					if(isRegistedFile == 0) {
						System.out.printf("게시물 저장 실패");
						return false;
					}
					
				}catch(DataAccessException de){
					de.printStackTrace();
					System.out.printf("데이터베이스 처리과정에 문제 생김");
				}catch(Exception e) {
					e.printStackTrace();
					System.out.printf("시스템 문제");
				}
				
			}
			
		}
		
		return true; 
		
	}
	
	// 파일 리스트 조회
	@Override
	public List<NoticeFileDto> selectNoticeFileList(int notiNo) throws Exception {
		int fileTotalCnt = noticeFileMapper.selectNoticeFileTotalCount(notiNo);
		if(fileTotalCnt < 1) {
			return Collections.emptyList();
		}

		return noticeFileMapper.selectNoticeFileList(notiNo);
	}
	
	// 파일 1개 조회
	@Override
	public NoticeFileDto selectNoticeFileDetail(int fileId) throws Exception {
		return noticeFileMapper.selectNoticeFileDetail(fileId);
	}

	// 게시글 수정
	@Override
	public boolean updateNotice(NoticeDto noticeDto) throws Exception {
		int queryResult = 0;
		
		try {
			queryResult = noticeMapper.updateNotice(noticeDto);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		return (queryResult == 1)? true : false;
	}
	
	// 게시글 수정(파일 포함)
	@Override
	public boolean updateNotice(NoticeDto noticeDto, MultipartFile[] files) throws Exception {
		int isUpdatedNotice = 0;
		int isDeletedFile = 0;
		int isRegistedFile = 0;
		
		// 게시물 수정
		try {
			
			isUpdatedNotice = noticeMapper.updateNotice(noticeDto);
			if(isUpdatedNotice == 0) {
				System.out.printf("게시물 수정 실패");
				return false;
			}
			
		}catch(DataAccessException de){
			de.printStackTrace();
			System.out.printf("데이터베이스 처리과정에 문제 발생");
		}catch(Exception e) {
			e.printStackTrace();
		    System.out.printf("시스템 문제");
		}
		
		// 파일 수정 및 삭제
		int fileId = noticeDto.getFileId(); // 넘어온 Form의 FileId
		String fileChangeYn = noticeDto.getFileChangeYn(); // 첨부파일 변경여부
		
		if(fileChangeYn.equals("Y")) { // 첨부파일 변경이 있다면
			
			try {
				
				if(fileId != 0) { // 첨부파일이 존재했었다면
					
					isDeletedFile = noticeFileMapper.deleteNoticeFile(fileId); // 파일 DB 삭제
					if(isDeletedFile == 0) { // 파일 DB 삭제 실패 false 리턴
						System.out.printf("파일 DB 삭제 실패");
						return false; 
					}
					
				}
				
				if(files != null) { // 전송된 File이 존재한다면
					
					List<NoticeFileDto> fileList = fileUtils.uploadFiles(files, noticeDto.getNotiNo()); // 파일 업로드 요청
					
					if(CollectionUtils.isEmpty(fileList) == false) { // 파일 리스트가 존재한다면
					
						isRegistedFile = noticeFileMapper.insertNoticeFile(fileList); // 파일 DB에 저장
						if(isRegistedFile == 0) { // 파일 DB에 저장 실패 false 리턴
							System.out.printf("파일 DB 저장 실패");
							return false; 
						}
					}else {
						System.out.printf("FileUtils 문제 발생");
						return false;
					}
					
				}
				
			}catch(DataAccessException de){
				de.printStackTrace();
				System.out.printf("데이터베이스 처리과정에 문제 생김");
			}catch(Exception e) {
				e.printStackTrace();
				System.out.printf("시스템 문제");
			}
			
		}
		
				
			
		return true;
	}

	// 게시글 삭제(파일 포함)
	@Override
	public boolean deleteNotice(NoticeDto noticeDto) throws Exception {
		int isDeletedNotice = 0;
		int isDeletedFile = 0;

		// 게시물 삭제
		try {
			
			isDeletedNotice = noticeMapper.deleteNotice(noticeDto.getNotiNo());
			if(isDeletedNotice == 0) {
				System.out.printf("게시물 삭제 실패");
				return false;
			}
			
		}catch(DataAccessException de){
			de.printStackTrace();
			System.out.printf("데이터베이스 처리과정에 문제 발생");
		}catch(Exception e) {
			e.printStackTrace();
		    System.out.printf("시스템 문제");
		}
		
		// 파일 삭제
		int fileId = noticeDto.getFileId(); // 넘어온 Form의 FileId
		
		try {
			
			isDeletedFile = noticeFileMapper.deleteNoticeFile(fileId); // 파일 DB 삭제
			if(isDeletedFile == 0) { // 파일 DB 삭제 실패 false 리턴
				System.out.printf("파일 DB 삭제 실패");
				return false; 
			}
			
		}catch(DataAccessException de){
			de.printStackTrace();
			System.out.printf("데이터베이스 처리과정에 문제 발생");
		}catch(Exception e) {
			e.printStackTrace();
		    System.out.printf("시스템 문제");
		}
		
		return true;
		
	}

	/* 게시글 목록 조회(페이징) */
	@Override
	public PageList<NoticeDto> selectNoticePageList(SearchDto sd) throws Exception {
		
		PageList<NoticeDto> selectNoticePageList = new PageList<NoticeDto>();
		selectNoticePageList.setPaging(sd.getPaging());
		
		selectNoticePageList.setItemTotalCount(noticeMapper.selectNoticeListTotalCount(sd));
		//selectNoticePageList.setItemTotalCount(0);
		if(selectNoticePageList.getItemTotalCount() > 0) {
			selectNoticePageList.setItemList(noticeMapper.selectNoticePageList(sd));
		}
		
		return selectNoticePageList;
	}


	/* 게시글 목록 조회(Pageable) - 전체 조회로 인한 속도 이슈 */
	/*@Override
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
	}*/

	
	
	
	
	
}
