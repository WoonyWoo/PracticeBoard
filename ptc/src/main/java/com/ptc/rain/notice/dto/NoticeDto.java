package com.ptc.rain.notice.dto;

import lombok.Data;

@Data
public class NoticeDto {
	
	/* 게시물 ID */
	private int notiNo;
	
	/* 게시물 번호 */
	private int rn;
	
	/* 제목 */
	private String title;
	
	/* 작성자 */
	private String writer;
	
	/* 내용 */
	private String cont;
	
	/* 등록자 */
	private String regr;
	
	/* 등록시간 */
	private String regDtime;
	
	/* 수정자 */
	private String modr;
	
	/* 수정시간 */
	private String modDtime;
	
	/* 공개여부 */
	private String expYn;
	
	/* 게시물 유형 */
	private String notiTyp;
	
	/* 댓글 허용여부 */
	private String cmntYn;
	
	/* 삭제여부 */
	private String delYn;
	
	/* 페이징 */
	private PagingDto paging;
	
	/* 파일 변경 여부 */
	private String fileChangeYn;
	
	/* 파일 ID */
	private int fileId;
	
	
}
