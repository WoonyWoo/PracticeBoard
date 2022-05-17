package com.ptc.rain.notice.dto;

import java.util.List;

import lombok.Data;

@Data
public class NoticeDto {
	
	private int notiNo;
	private String title;
	private String writer;
	private String cont;
	private String regr;
	private String regDtime;
	private String modr;
	private String modDtime;
	private String expYn;
	private String notiTyp;
	private String cmntYn;
	private String delYn;
	
}
