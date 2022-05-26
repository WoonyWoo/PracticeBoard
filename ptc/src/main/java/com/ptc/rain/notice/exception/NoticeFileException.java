package com.ptc.rain.notice.exception;

// 객체를 파일에 쓰거나 전송하기 위해 객체 클래스에 Serializable 인터페이스를 implements 함
// 하지만, Serializable 인터페이스를 implements 하게 되면 warnning이 발생
// warnning이 발생하지만 동작하는데 문제가 없다
// @SuppressWarnings : 부적절한 compiler의 경고를 제거하기 위해 사용, compile시 warnning을 체크하지 않기 위해 사용
// serial : serialVersionUID를 정의해주지 않은 경우 나타나는 warnning을 체크하지 않음
@SuppressWarnings("serial")
public class NoticeFileException extends RuntimeException{

	public NoticeFileException(String message) {
		super(message);
	}

	public NoticeFileException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
