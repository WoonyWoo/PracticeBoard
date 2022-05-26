package com.ptc.rain.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ptc.rain.notice.dto.NoticeFileDto;

@Mapper
public interface NoticeFileMapper {

	public int insertNoticeFile(List<NoticeFileDto> noticeFileDto);

	public NoticeFileDto selectNoticeFileDetail(int fileId);

	public int deleteNoticeFile(int notiNo);

	public List<NoticeFileDto> selectNoticeFileList(int notiNo);

	public int selectNoticeFileTotalCount(int notiNo);
	
}
