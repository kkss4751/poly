package kopo.poly.service;

import kopo.poly.dto.MailCodeDTO;
import kopo.poly.dto.UserInfoDTO;

public interface IMailService {

    //이메일 인증
    int sendCode(MailCodeDTO mDTO) throws Exception;

    //ㅇㅏ이디 찾아서 보내주기 (아이디찾기
    //int sendId(UserInfoDTO uDTO) throws Exception;
    int pwdCode(MailCodeDTO mDTO) throws Exception;
}
