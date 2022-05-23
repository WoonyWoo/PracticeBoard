package com.ptc.rain.notice.dto;

import lombok.Data;

@Data
public class SearchDto{
	
	/* 검색 조건 */
	private String srchTyp;
	
	/* 검색 키워드 */
	private String keyword;
	
	/* 검색 키워드 */
	private PagingDto paging;
}
