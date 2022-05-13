package com.ptc.rain.notice.dto;

import lombok.Data;

@Data
public class NoticeDto {
	
	private int notiNo;
	private String title;
	private String writer;
	private String cont;
	private String regDtime;
	private String modDtime;
	private String expYn;
	private String notiTyp;
	private String CmntYn;
	
}
