package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;
import org.springframework.ui.ModelMap;

public interface IUserInfoService {

    //회원가입(회원정보 등록하기)
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    //로그인위해 아이디와비밀번호 일치하는지 확인
    int UserLoginCheck(UserInfoDTO pDTO) throws Exception;

    /* 아이디 중복체크 */
    int userIdCheck(String userId) throws Exception;


    /* 이메일과 이름으로 유저확인하기*/
    // int userCheck(UserInfoDTO pDTO) throws Exception;

    UserInfoDTO findUserId(UserInfoDTO pDTO) throws Exception;

    int userCheck(UserInfoDTO pDTO) throws Exception;

    /* 비밀번호 수정하기 */
    void updateUserPwd(UserInfoDTO pDTO) throws Exception;

    /* 삭제허기 */
    void deleteUserInfo(UserInfoDTO pDTO) throws Exception;
}
