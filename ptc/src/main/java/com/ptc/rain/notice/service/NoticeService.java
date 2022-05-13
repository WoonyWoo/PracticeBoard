package com.ptc.rain.notice.service;

import java.util.List;
import com.ptc.rain.notice.dto.NoticeDto;

public interface NoticeService {
	
	List<NoticeDto> selectNoticeList() throws Exception;
}
