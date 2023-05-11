package kopo.poly.service.impl;


import kopo.poly.dto.MailCodeDTO;

import kopo.poly.dto.UserInfoDTO;
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

            res = getRes(res, toMail, sendCode);
            log.info(this.getClass().getName() + " do send Mail SErvice ENd 1! ");

        }

        return res;
    }

    @Override
    public int pwdCode(MailCodeDTO mDTO) throws Exception {
        log.info(this.getClass().getName() + "pwdCOde Service Start!! ");


        int res = 0;


        String toMail = CmmUtil.nvl(mDTO.getToMail()); // 받는사람
        String sendCode = CmmUtil.nvl(mDTO.getMail_code()); // 전송될 코드
        log.info("받을 사람 toMail : " + toMail);
        log.info("보낸 코드 sendCode : " + sendCode);
        log.info("보낸사람 : " + fromMail);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserEmail(toMail);
        if (rEntity.isPresent()) {
            res = 1; //존재하는 이메일임으로 전송가능

            res = getRes(res, toMail, sendCode);
            log.info(this.getClass().getName() + " do pwdCode Service ENd 1! ");

        } else {
            res = 2; //불가능

        }

        return res;
    }

    private int getRes(int res, String toMail, String sendCode) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

        try {
            message.setSubject("**** 로부터 이메일 인증번호가 도착했습니다. "); // 제목
            String msgg = "";
            msgg += "<div style = 'margin:auto; width:600px; border:1px solid black; padding:10px; text-align:center; background-color:#CEF6F5'>";
            msgg += "<h1 style='color:#0101DF; font-family: Helvetica, Sans-Serif;'> hhhh 안녕하세요 ******* 입니다 ! </h1> ";
            msgg += "<br>";
            msgg += "<p style='color: black;'> ****페이지로 돌아가 아래 코드를 입력해주세요 </p>";
            msgg += "<br>";
            msgg += "<p style='color: black;> 감사합니다 </p>";
            msgg += "<br>";
            // msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
            msgg += "<div style='border:1px solid #CEF6F5; padding:10px; background-color:#CEF6F5'>";
            msgg += "<h3 style='color:blue; background-color: #ffffff; '> 이메일 인증 코드입니다. </h3><hr>";
            msgg += "<div style='font-size:130%; background-color:#ffffff'>";
            msgg += "CODE : <strong>";
            msgg += sendCode + "</strong> <br> <div><br> </div>";
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
        return res;
    }


}
