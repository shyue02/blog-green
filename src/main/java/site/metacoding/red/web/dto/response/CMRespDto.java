package site.metacoding.red.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CMRespDto<T> {	// 공통응답 dto	
	private Integer code;	// 1정상, -1실패
	private String msg; // 실패의 이유 or 성공한 이유 메시지를 담자
	private T data;	// 응답할 데이터 / 응답해줄 때 마다 데이터 타입이 다르니까 제네릭으로 담자
}
