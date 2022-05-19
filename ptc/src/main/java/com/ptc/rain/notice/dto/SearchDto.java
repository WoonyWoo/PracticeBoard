package com.ptc.rain.notice.dto;

import lombok.Data;

@Data
public class SearchDto extends NoticeDto{
	
	/* 검색 조건(제목) */
	private String ttl;
	
	/* 검색 조건(작성자) */
	private String wrt;
	
	/* 검색 조건(내용) */
	private String content;
	
}
