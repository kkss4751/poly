package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

public interface IUserInfoService {

    //회원가입(회원정보 등록하기)
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    //로그인위해 아이디와비밀번호 일치하는지 확인
    int UserLoginCheck(UserInfoDTO pDTO) throws Exception;

    /* 아이디 중복체크 */
    int userIdCheck(String userId) throws Exception;
}
