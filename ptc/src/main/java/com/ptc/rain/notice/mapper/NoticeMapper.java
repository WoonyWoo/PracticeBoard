package com.ptc.rain.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ptc.rain.notice.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
	
	List<NoticeDto> selectNoticeList() throws Exception;

}
