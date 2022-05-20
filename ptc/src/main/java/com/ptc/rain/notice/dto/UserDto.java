package com.ptc.rain.notice.dto;

import lombok.Data;

@Data
public class UserDto {
	
	/* 회원 PrimaryKey */
	private int usrNo;
	
	/* 회원 ID */
	private String usrId;
	
	/* 회원명 */
	private String usrNm;
	
	/* 회원 비밀번호 */
	private String passwd;
	
	/* 등록자 */
	private String regr;
	
	/* 등록시간 */
	private String regDtime;
	
	/* 수정자 */
	private String modr;
	
	/* 수정시간 */
	private String modDtime;
	
	/* 삭제여부 */
	private String delYn;
}
