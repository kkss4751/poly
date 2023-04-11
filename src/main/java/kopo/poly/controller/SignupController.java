package kopo.poly.controller;

import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping(value = "/signup")
@RequiredArgsConstructor
public class SignupController {

    @GetMapping(value = "/signupForm")
    public String signup() throws Exception{
        log.info(this.getClass().getName()+" signup Start!!");

        log.info(this.getClass().getName()+" signup end!1");
        return "/signup/signupForm";
    }

    @PostMapping(value = "/signup")
    public String signin(HttpServletRequest request) throws Exception{
        log.info(this.getClass().getName()+"signin Start!!");

        String userEmail = CmmUtil.nvl(request.getParameter("userEmail"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String userPwd = CmmUtil.nvl(request.getParameter("userPwd"));

        log.info("유저 이메일 : " + userEmail);
        log.info("유저 이름 : " + userName);
        log.info("유저 아이디 : " + userId);
        log.info("유저 비밀번호 : " + userPwd); //암호화 해주기

        log.info(this.getClass().getName()+"signin End!!");
        return "";
    }
}
