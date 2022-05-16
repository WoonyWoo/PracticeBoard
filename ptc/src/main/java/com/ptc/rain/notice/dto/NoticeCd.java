package com.ptc.rain.notice.dto;

import lombok.Data;

@Data
public class NoticeCd {
	public static final String TYPE_ALL = "전체";
	public static final String TYPE_FREE = "자유";
	public static final String TYPE_ANON = "익명";
	
	public static final String EXP_Y = "Y";
	public static final String EXP_N = "N";
	
	public static final String CMNT_Y = "Y";
	public static final String CMNT_N = "N";
}
