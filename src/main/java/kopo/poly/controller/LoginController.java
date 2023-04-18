package kopo.poly.controller;

import kopo.poly.dto.UserInfoDTO;
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
@RequiredArgsConstructor
@RequestMapping(value = "login")
@Controller
public class LoginController {

    @Resource(name = "UserInfoService")
    private IUserInfoService userInfoService;

    @Resource(name = "MailService")
    private MailService mailService;

    @GetMapping(value = "/loginForm")
    public String loginForm() throws Exception{
        log.info(this.getClass().getName()+" login Start! ");

        return "/login/loginForm";
    }


    @PostMapping(value = "/check_user")
    public String checkUser(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName()+" checkUser Controller start! ");

        // 로그인처리 결과 저장 변수
        int res = 0;

        //웹에서 바는 정보 저장할 변수
        UserInfoDTO pDTO = null;

        try{

            String userId = CmmUtil.nvl(request.getParameter("uId"));
            String userPwd = CmmUtil.nvl(request.getParameter("uPwd"));
            log.info("입력받은 유저 아이디 : " + userId);
            log.info("입력받은 유저 비밀번호 : " + userPwd);

            pDTO = new UserInfoDTO();
            pDTO.setUserId(userId);
            pDTO.setUserPwd(userPwd);

        }catch (Exception e){

        }finally {
            log.info(this.getClass().getName()+" check user End!");
        }


        return "";
    }

    @GetMapping(value = "/find_pwd")
    public String findPwd() throws Exception{
        log.info(this.getClass().getName()+" findPwd page STart!");
        log.info(this.getClass().getName()+" findPwd page End!");
        return "/login/findPwd";
    }

    @GetMapping(value = "/find_id")
    public String find_Id() throws Exception{
        log.info(this.getClass().getName()+"findId page Start!");
        log.info(this.getClass().getName()+"findId page End");
        return "/login/findId";
    }

    @ResponseBody
    @PostMapping(value = "findId")
    public String findId(HttpServletRequest request) throws Exception{
        log.info(this.getClass().getName()+" findId Controller Start!");
        Map<String, Integer> map = new HashMap<>();

        String userEmail = CmmUtil.nvl(request.getParameter("userEmail"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));

        UserInfoDTO uDTO = new UserInfoDTO();
        uDTO.setUserEmail(userEmail);
        uDTO.setUserName(userName);




        log.info(this.getClass().getName()+" findId Controller End!");
        return "";
    }

}
