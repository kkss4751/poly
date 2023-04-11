package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MsgDTO {

    //DTO를 JSON 형태로 변환할떄 값이 기본값(빈값, 초기화값)이 아닌 변수만 JSON형태로 변환

    private int result; // 성공:1 / 실패 : 그 외
    private String msg;
}
