package kopo.poly.controller;

import kopo.poly.dto.MailCodeDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserInfoService;
import kopo.poly.service.impl.MailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/signup")
@RequiredArgsConstructor
public class SignupController {

    @Resource(name = "UserInfoService")
    private IUserInfoService userInfoService;

    @Resource(name = "MailService")
    private MailService mailService;

    @GetMapping(value = "/signupForm")
    public String signup() throws Exception{
        log.info(this.getClass().getName()+" signup Start!!");

        log.info(this.getClass().getName()+" signup end!1");
        return "/signup/signupForm";
    }

    /** 유저 이메일 체크 */
    @ResponseBody
    @PostMapping(value = "emailCheck")
    public Map<String, Integer> sendCode(HttpServletRequest request, HttpSession session) throws Exception{
        log.info(this.getClass().getName()+" emailCheck Controller Start!!");
        Map<String, Integer> map = new HashMap<>();

        String userEmail = CmmUtil.nvl(request.getParameter("userEmail"));
        String mailCode = CmmUtil.nvl(request.getParameter("mailCode"));

        session.getAttribute(mailCode);
        log.info("받는사람 : " + userEmail);
        log.info("메일코드 : " + mailCode);

        MailCodeDTO mDTO = new MailCodeDTO();
        mDTO.setToMail(userEmail);
        mDTO.setMail_code(mailCode);

        int res = mailService.sendCode(mDTO);

        if (res == 1){
            log.info("이미 사용중인 아이디 입니다.");

        } else if (res == 2) {
            log.info("성공적으로 이메일을 발송하였습니다.");

        }else {
            log.info("이메일전송실패.");
        }
        map.put("code", res);

        log.info(this.getClass().getName()+" sendMail Controller End!");
        return map;
    }


    /** 유저 아이디 체크 */
    @ResponseBody
    @PostMapping(value = "IdCheck")
    public Map<String, Integer> userIdCheck(HttpServletRequest request) throws Exception{
        log.info(this.getClass().getName() + " userIdCHeck Controller Start!");

        Map<String, Integer> map = new HashMap<>();

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        log.info("userId : " + userId);

        int res = userInfoService.userIdCheck(userId);

        map.put("code", res);
        log.info(this.getClass().getName()+" userIdCheck controller End!");

        return map;
    }

    @PostMapping(value = "/insertUserInfo")
    public String signin(HttpServletRequest request, ModelMap model) throws Exception{
        log.info(this.getClass().getName()+"signin Start!!");

        String msg = "";

        UserInfoDTO pDTO = null;

        try {

            String userEmail = CmmUtil.nvl(request.getParameter("userEmail"));
            String userName = CmmUtil.nvl(request.getParameter("userName"));
            String userId = CmmUtil.nvl(request.getParameter("userId"));
            String userPwd = CmmUtil.nvl(request.getParameter("userPwd"));

            log.info("유저 이메일 : " + userEmail);
            log.info("유저 이름 : " + userName);
            log.info("유저 아이디 : " + userId);
            log.info("유저 비밀번호 : " + userPwd); //암호화 해주기

            pDTO = new UserInfoDTO();
            pDTO.setUserEmail(userEmail);
            pDTO.setUserName(userName);
            pDTO.setUserId(userId);
            pDTO.setUserPwd(userPwd);

            // 회원가입
            int res = userInfoService.insertUserInfo(pDTO);
            log.info("회원가입 결과 : " + res);

            if (res == 1){
                msg = "회원가입되었습니다.";

            }else if (res == 2){
                msg = " 이미 가입된 이메일 주소입니다.";

            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
            }

        }catch (Exception e ){
            // 저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : " + e;
            log.info(e.toString());
            e.printStackTrace();

        }finally {
            log.info(this.getClass().getName()+"insertUserInfoEnd!");

            model.addAttribute("msg", msg);
            model.addAttribute("pDTO", pDTO);

            pDTO = null;
        }

        return "redirect";
    }
}
