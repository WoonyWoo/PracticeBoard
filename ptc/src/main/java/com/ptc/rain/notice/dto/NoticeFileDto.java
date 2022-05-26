package com.ptc.rain.notice.dto;

import lombok.Data;

@Data
public class NoticeFileDto {

	/* 파일 ID */
	private int fileId;

	/* 게시물 ID(FK) */
	private int notiNo;
	
	/* 삭제여부 */
	private String delYn;
	
	/* 원본 파일명 */
	private String originalName;
	
	/* 저장 파일명 */
	private String saveName;
	
	/* 파일 크기 */
	private Long fileSize;
	
	/* 등록자 */
	private String regr;
	
	/* 등록시간 */
	private String regDtime;
	
	/* 삭제자 */
	private String delr;
	
	/* 삭제시간 */
	private String delDtime;
}
