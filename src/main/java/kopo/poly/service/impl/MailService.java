package kopo.poly.service.impl;


import kopo.poly.dto.MailCodeDTO;

import kopo.poly.repository.UserInfoRepository;
import kopo.poly.repository.impl.UserInfoEntity;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.MailCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("MailService")
public class MailService implements IMailService {

    private final UserInfoRepository userInfoRepository;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    /**
     * 아이디 중복체크 후 이메일 보내기
     */
    @Override
    public int sendCode(MailCodeDTO mDTO) throws Exception {
        log.info(this.getClass().getName() + "sendCode Service Start!! ");


        int res = 0;


        String toMail = CmmUtil.nvl(mDTO.getToMail()); // 받는사람
        String sendCode = CmmUtil.nvl(mDTO.getMail_code()); // 전송될 코드
        log.info("받을 사람 toMail : " + toMail);
        log.info("보낸 코드 sendCode : " + sendCode);
        log.info("보낸사람 : " + fromMail);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserEmail(toMail);
        if (rEntity.isPresent()) {
            res = 1; //이메일 중복

        } else {
            res = 2; //사용가능 이메일

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

            try {
                message.setSubject("**** 로부터 이메일 인증번호가 도착했습니다. "); // 제목
                String msgg = "";
                msgg += "<div style = 'margin:100px;' >";
                msgg += "<h1> 안녕하세요 ******* 입니다 ! </h1> ";
                msgg += "<br>";
                msgg += "<p> 회원가입 창으로 돌아가 아래 코드를 입력해주세요 </p>";
                msgg += "<br>";
                msgg += "<p> 감사합니다 </p>";
                msgg += "<br>";
                // msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
                msgg += "<h3 style='color:blue;'> 회원가입 코드입니다. </h3>";
                msgg += "<div style='font-size:130%'>";
                msgg += "CODE : <strong>";
                msgg += sendCode + "</strong> <br> <div><br>";
                // msgg+= "</div>";

                message.setText(msgg, "utf-8", "html");
                message.setFrom(fromMail);

                messageHelper.setTo(toMail);
                messageHelper.setFrom(fromMail);

                mailSender.send(message);

            } catch (Exception e) {
                res = 0; // 메일발송이 실패하기 떄문에 0으로 변경
                log.info("[ERR0R] " + this.getClass().getName() + " doSendMail : " + e);
            }
            log.info(this.getClass().getName() + " do send Mail SErvice ENd 1! ");

        }

        return res;
    }

    /**
     * 이메일 체크후 아이디 보내주기
     */
    /*@Override
    public int sendId(UserInfoDTO uDTO) throws Exception {
        log.info(this.getClass().getName() + " sendId Start! ! ");

        int res = 0;

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByEmail(uDTO.getEmail());
        if (rEntity.isPresent()) {
            res = 1;
            log.info("servie res 값: " + res);
            ///Optional<UserInfoEntity> findByUserId(uDTO.getUserId());
            ///Optional<UserInfoEntity> uEntity = userInfoRepository.findByEmail(uDTO.getEmail());
            String userEmail = CmmUtil.nvl(uDTO.getEmail());
            log.info("받을사람 :: " + userEmail);

            String userId = CmmUtil.nvl(rEntity.get().getUserId());//rEntiy(Optional)에서 get함수를 통해 내용물 Entity를 꺼내고 꺼낸 Entity에서 다시 get함수를 통해 원하는 컬럼값을 꺼냄
            log.info("userId : " + userId);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

            try {
                message.setSubject("**** 로부터 이메일 인증번호가 도착했습니다. "); // 제목
                String msgg = "";
                msgg += "<div style = 'margin:100px;' >";
                msgg += "<h1> 안녕하세요 ******* 입니다 ! </h1> ";
                msgg += "<br>";
                msgg += "<p> 아래 아이디를 확인하시고 로그인창으로 돌아가세요. </p>";
                msgg += "<br>";
                msgg += "<p> 감사합니다 </p>";
                msgg += "<br>";
                // msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
                msgg += "<h3 style='color:blue;'> 회원님의 아이디 입니다. </h3>";
                msgg += "<div style='font-size:130%'>";
                msgg += "아이디 : <strong>";
                msgg += userId + "</strong> <br> <div><br>";
                // msgg+= "</div>";

                message.setText(msgg, "utf-8", "html");
                message.setFrom(fromMail);

                messageHelper.setTo(userEmail);
                messageHelper.setFrom(fromMail);

                mailSender.send(message);

            } catch (Exception e) {
                res = 0; // 메일발송이 실패하기 떄문에 0으로 변경
                log.info("[ERR0R] " + this.getClass().getName() + " doSendMail : " + e);
            }
        }
        log.info(this.getClass().getName() + " sendId End !! ");

        return res;
    }*/

    /*@Transactional
    @Override
    public int sendPwd(UserInfoDTO uDTO) throws Exception {
        log.info(this.getClass().getName() + " sendPwd Service Start! ");

        String userId = CmmUtil.nvl(uDTO.getUserId());
        String userEmail = CmmUtil.nvl(uDTO.getEmail());
        String newPwd = CmmUtil.nvl(MailCodeUtil.createKey());

        log.info("비밀번호 찾을 회원 : " + userId);
        log.info("받을 이메일 : " + userEmail);
        log.info("새로운 비밀번호 : " + newPwd);

        int res = 0;

        try {
            Optional<UserInfoEntity> rEntitiy = userInfoRepository.findByUserId(userId);
            if (rEntitiy.isPresent()) {
                res = 1;
                UserInfoEntity userInfoEntity = rEntitiy.get();
                String userId2 = userInfoEntity.getUserId();
                log.info("userId2 : " + userId2);
                int updateResult = userInfoRepository.updatePwd(userId2, newPwd);
//            UserInfoEntity uEntity = UserInfoEntity.builder().userId(userInfoEntity.getUserId()).password(newPwd).build();
//            userInfoRepository.save(uEntity); //데이터 수정후 메일 전송

                if (updateResult == 1) {
                    log.info("임시비밀번호로 업데이트 성공");
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");
                    message.setSubject("**** 로부터 임시 비밀번호가 도착했습니다. "); // 제목
                    String msgg = "";
                    msgg += "<div style = 'margin:100px;' >";
                    msgg += "<h1> 안녕하세요 ******* 입니다 ! </h1> ";
                    msgg += "<br>";
                    msgg += "<p> 아래 임시비밀번호를 확인하시고 로그인을 시도해주세요. </p>";
                    msgg += "<br>";
                    msgg += "<p> 로그인 후 반드시 회원수정을 통해 비밀번호를 변경해주세요! </p>";
                    msgg += "<p> 감사합니다 </p>";
                    msgg += "<br>";
                    // msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
                    msgg += "<h3 style='color:blue;'> 회원님의 임시비밀번호 입니다. </h3>";
                    msgg += "<div style='font-size:130%'>";
                    msgg += "임시비밀번호 : <strong>";
                    msgg += newPwd + "</strong> <br> <div><br>";
                    // msgg+= "</div>";

                    message.setText(msgg, "utf-8", "html");
                    message.setFrom(fromMail);

                    messageHelper.setTo(userEmail);
                    messageHelper.setFrom(fromMail);

                    mailSender.send(message);

                } else {
                    log.info("업데이트 실패");
                }
            }

        } catch (Exception e) {
            res = 0; // 메일발송이 실패하기 떄문에 0으로 변경
            log.info("[ERR0R] " + this.getClass().getName() + " doSendMail : " + e);
        }
        log.info(this.getClass().getName() + " sendId End !! ");


        return res;
    }*/


}
