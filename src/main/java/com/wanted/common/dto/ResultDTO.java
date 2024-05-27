package com.wanted.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 결과 데이터를 담는 DTO 클래스입니다.
 *
 * @param <T> 결과 데이터의 타입
 */
@Data
@AllArgsConstructor
@Builder
public class ResultDTO<T> {
	private HttpStatus statusCode;
	private String resultMsg;
	private T resultData;

	/**
	 * 결과 메시지만 담는 ResultDTO를 생성합니다.
	 *
	 * @param statusCode 상태 코드
	 * @param resultMsg 결과 메시지
	 */
	public ResultDTO(final HttpStatus statusCode, final String resultMsg) {
		this.statusCode = statusCode;
		this.resultMsg = resultMsg;
		this.resultData = null;
	}

	/**
	 * 결과 메시지만 담는 ResultDTO를 생성하는 정적 메서드입니다.
	 *
	 * @param statusCode 상태 코드
	 * @param resultMsg 결과 메시지
	 * @param <T> 결과 데이터의 타입
	 * @return 결과 메시지만 담는 ResultDTO
	 */
	public static<T> ResultDTO<T> res(final HttpStatus statusCode, final String resultMsg) {
		return res(statusCode, resultMsg, null);
	}

	/**
	 * 결과 데이터와 메시지를 담는 ResultDTO를 생성하는 정적 메서드입니다.
	 *
	 * @param statusCode 상태 코드
	 * @param resultMsg 결과 메시지
	 * @param t 결과 데이터
	 * @param <T> 결과 데이터의 타입
	 * @return 결과 데이터와 메시지를 담는 ResultDTO
	 */
	public static<T> ResultDTO<T> res(final HttpStatus statusCode, final String resultMsg, final T t) {
		return ResultDTO.<T>builder()
		                .resultData(t)
		                .statusCode(statusCode)
		                .resultMsg(resultMsg)
		                .build();
	}
}