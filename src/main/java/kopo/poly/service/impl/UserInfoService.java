package kopo.poly.service.impl;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.repository.UserInfoRepository;
import kopo.poly.repository.impl.UserInfoEntity;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("UserInfoService")
public class UserInfoService implements IUserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {
        // 성공 : 1 // 중복 실패 : 2 // 기타에러 : 0
        int res = 0;

        long userSeq = pDTO.getUserSeq();

        String userEmail = CmmUtil.nvl(pDTO.getUserEmail());
        String userName = CmmUtil.nvl(pDTO.getUserName());
        String userId = CmmUtil.nvl(pDTO.getUserId());
        String userPwd = CmmUtil.nvl(pDTO.getUserPwd());

        // log.info("userSeq : " + userSeq);

        log.info("userEmail : " + userEmail);
        log.info("userName : "+ userName);
        log.info("userId : " + userId);
        log.info("userPwd : " + userPwd);

        // 회원가입 중복방지위해 DB에서 데이터 조회
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserEmail(userEmail);

        //값존재시 res 2 로 변경
        if (rEntity.isPresent()){
            res = 2;

        }else {

            //회원가입을 위한 Entity 생성
            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userSeq(userSeq).userEmail(userEmail).userName(userName).userId(userId).userPwd(userPwd)
                    .build();

            // 회원정보 DB에 저장
            userInfoRepository.save(pEntity);

            rEntity = userInfoRepository.findByUserEmail(userEmail);

            if (rEntity.isPresent()){
                res = 1;

            }else {
                res = 0;
            }
        }
        return res;
    }

    @Override
    public int UserLoginCheck(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName()+" userLoginCheck Service Start!!");
        //로그인 성공1 , 실패:0
        int res = 0;

        String userId = CmmUtil.nvl(pDTO.getUserId());
        String userPwd = CmmUtil.nvl(pDTO.getUserPwd());

        log.info("userId : " + userId);
        log.info("userPwd : " + userPwd);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserIdAndUserPwd(userId, userPwd);

        if (rEntity.isPresent()){
            res = 1;

        } else {
            res = 0;
        }

        log.info(this.getClass().getName()+" userLoginCheck Service End!!");
        return res;

    }

    @Override
    public int userIdCheck(String userId) throws Exception {
        log.info(this.getClass().getName()+"userIdCheck Service Start!!");

        int res = 0;
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        if (rEntity.isPresent()){
            res = 1;
        }

        log.info(this.getClass().getName()+" userIdCheck Service End");
        return res;
    }

    @Override
    public UserInfoDTO findUserId(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName()+" userCheck for userEmail and UserName Service Start!");
        int res = 0;
        String userEmail = pDTO.getUserEmail();
        String userName = pDTO.getUserName();
        log.info("#### userEmail : "+ userEmail);
        log.info("#### userName : "+ userName);

        UserInfoDTO rDTO = null;

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserEmailAndUserName(userEmail, userName);
        //String userId = CmmUtil.nvl(rEntity.get().getUserId());


        String userId = CmmUtil.nvl(rEntity.get().getUserId());
        log.info("userId :" +userId);
        //String userId = userInfoRepository.findByUserId();
        if (rEntity.isPresent()){
            res = 1;

            log.info("userId : "  + userId);

            rDTO = new UserInfoDTO();
            rDTO.setUserId(userId);
            rDTO.setRes(res);

            //return userInfoRepository.findUserIdByUserEmailAndUserName();
        }

        log.info(this.getClass().getName()+" userCheck for userEmail and UserName Service End!");
        return rDTO;
    }

    @Override
    public int userCheck(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName()+" userCheck Service Start!!");

        String userEmail = CmmUtil.nvl(pDTO.getUserEmail());
        String userName = CmmUtil.nvl(pDTO.getUserName());
        String userId = CmmUtil.nvl(pDTO.getUserId());

        int res = 0;


        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserEmailAndUserNameAndUserId(userEmail, userName, userId);
        if (rEntity.isPresent()){
            res = 1;

        }

        log.info(this.getClass().getName()+" userCheck Service End!!!");
        return res;
    }

    /** 유저 비밀번호 변경*/
    @Transactional
    @Override
    public void updateUserPwd(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + " updateUserPwd Service Starat!!");

        String userEmail = CmmUtil.nvl(pDTO.getUserEmail());
        String newPwd = CmmUtil.nvl(pDTO.getUserPwd());

        log.info("userEmail : " + userEmail);
        log.info("newPwd : " + newPwd);

        UserInfoEntity rEntity = userInfoRepository.findByUserEmail(pDTO.getUserEmail()).get();

        UserInfoEntity pEntity = UserInfoEntity.builder()
                .userEmail(userEmail)
                .userName(rEntity.getUserName())
                .userId(rEntity.getUserId())
                .userPwd(newPwd).build();

        userInfoRepository.save(pEntity);

        log.info(this.getClass().getName() + " updateUserPwd Service End!!");

    }

    @Override
    public void deleteUserInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName()+" deleteUserInfo Service Start!!");
/*

        String userEmail = CmmUtil.nvl(pDTO.getUserEmail());
        log.info("userEmail : " + userEmail);

        userInfoRepository.deleteById(userEmail);

*/
        log.info(this.getClass().getName()+" deleteUserInfo Service End!!");
    }
}
