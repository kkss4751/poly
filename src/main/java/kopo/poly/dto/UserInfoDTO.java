package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {

    private long userSeq;
    private String userName;
    private String userEmail;
    private String userId;
    private String userPwd;

    private String exists_yn; // 중복방지 변수/ DB테이블에 존재하지 않는 가상의 컬럼(ALIAS)
}
