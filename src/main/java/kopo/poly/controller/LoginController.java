package kopo.poly.controller;

import kopo.poly.dto.MailCodeDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoService;
import kopo.poly.service.impl.MailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping(value = "/loginForm")
    public String loginFormPost() throws Exception{
        log.info(this.getClass().getName()+" loginForm Post Startt!");

        return "/login/loginForm";
    }


    @PostMapping(value = "/check_userr")
    public String checkUser(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName()+" checkUser Controller start! ");

        // 로그인처리 결과 저장 변수
        int res = 0;

        //웹에서 바는 정보 저장할 변수
        UserInfoDTO pDTO = null;

        String msg = "";
        String url = "";

        try{

            String userId = CmmUtil.nvl(request.getParameter("uId"));
            String userPwd = CmmUtil.nvl(request.getParameter("uPwd"));
            log.info("입력받은 유저 아이디 : " + userId);
            log.info("입력받은 유저 비밀번호 : " + userPwd);

            pDTO = new UserInfoDTO();
            pDTO.setUserId(userId);
            pDTO.setUserPwd(userPwd);

            res = userInfoService.UserLoginCheck(pDTO); // 유저 정보 확인하기
            log.info("res : " + res);

            if (res == 1){
                msg = "안녕하세요" + userId +"님";
                url = "/main/index";

                /* 아이디가 비밀번호가 일치하므로 로그인 성공*/
                session.setAttribute("SS_USER_ID", userId);
                model.addAttribute("userId", userId);
                model.addAttribute("msg",msg);
                model.addAttribute("url", url);


            }

        }catch (Exception e){
            res = 2;
            log.info(e.toString());
            e.printStackTrace();

            msg = "아이디, 비밀번호를 다시한번 확인해주세요";
            url = "/login/loginForm";
            model.addAttribute("msg",msg);
            model.addAttribute("url",url);

        }finally {
            log.info(this.getClass().getName()+" check user End!");

            model.addAttribute("res", String.valueOf(res));

            //finally 은 무조건 실행된다
            //msg = "회원정보를 다시한번 확인해주세요";
            //url = "/login/loginForm";
            //model.addAttribute("msg",msg);
            //model.addAttribute("url",url);

            pDTO = null;
        }
        /*model.addAttribute("userId", pDTO.getUserId());*/
        /* 이부분 res 뜨지 않음 */

        return "redirect";

    }

    @GetMapping(value = "/find_pwd")
    public String findPwd(/*@RequestParam("userId")*/String userId, ModelMap model) throws Exception{
        log.info(this.getClass().getName()+" findPwd page STart!");

        model.addAttribute("userId", userId);

        log.info(this.getClass().getName()+" findPwd page End!");
        return "/login/findPwd";
    }


    /** 비밀번호 찾기 로직
     *  이메일, 이름, 아이디 일치하면 바로 변경하기 페이지 띄워줌 */
    @PostMapping(value = "/find_pwd/check_user")
    public String findUserPwd( HttpServletRequest request, ModelMap model) throws Exception{
        log.info(this.getClass().getName()+" findUserPwd Controller Start!");
        String userEmail = CmmUtil.nvl(request.getParameter("uEmail"));
        String userName = CmmUtil.nvl(request.getParameter("uName"));
        String userId = CmmUtil.nvl(request.getParameter("uId"));

        log.info("userEmail : " + userEmail);
        log.info("userName : " + userName);
        log.info("userId : " + userId);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserEmail(userEmail);
        pDTO.setUserName(userName);
        pDTO.setUserId(userId);

        int res = 0;

        res = userInfoService.userCheck(pDTO);

        String msg = "";
        String url = "";

        if (res == 1){
            /* 비밀번호 변경 페이지 */
            msg = "회원임이 확인되었습니다. \n\n 새로운 비밀번호를 입력해주세요.";
            url = "/login/find_pwd/change_pwd_form";
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);
        }else {
            /* 다시 입력 하기 */
            msg = "정보를 다시한번 확인해주세요.";
            url = "/login/find_pwd?userId";
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);
        }

        log.info(this.getClass().getName()+" findUserPWd COntroller End!");
        return "redirect";
    }

    @ResponseBody
    @PostMapping("/pwdChange_ing")
    public Map<String, Integer> pwdChanging( HttpServletRequest request, ModelMap model) throws Exception{
        log.info(this.getClass().getName()+" pwdCHanging... Start!");
        Map<String, Integer> map = new HashMap<>();

        int res = 0;
        try{
            String userEmail = CmmUtil.nvl(request.getParameter("userEmail"));
            String newPwd = CmmUtil.nvl(request.getParameter("newPwd"));

            log.info("userEMail : " + userEmail);
            log.info("newPwd : " + newPwd);

            UserInfoDTO pDTO = new UserInfoDTO();
            pDTO.setUserEmail(userEmail);
            pDTO.setUserPwd(newPwd);

            res = 1;
            userInfoService.updateUserPwd(pDTO);



        }catch (Exception e){
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            log.info(this.getClass().getName()+ "pwdChanging End!");

        }
        map.put("code", res);

        log.info(this.getClass().getName()+" pwdCHanging... End!");
        return map;
    }



    /* 비밀번호 찾기 창 바꿈
    @GetMapping(value = "/find_pwd/change_pwd_form")
    public String getChangePwd(){
        return "/login/changePwd";
    }

    @PostMapping(value = "/find_pwd/change_user_pwd")
    public String postChangePwd(HttpServletRequest request){
        String newPwd = CmmUtil.nvl(request.getParameter("newPwd"));



        return "/login/changePwd";
    }*/



    @GetMapping(value = "/find_id")
    public String find_Id() throws Exception{
        log.info(this.getClass().getName()+"findId page Start!");
        log.info(this.getClass().getName()+"findId page End");
        return "/login/findId";
    }


    /*아이디 찾기 로직 구현 컨트롤러 */
    @PostMapping(value = "/find_UserId")
    public String findUserId(HttpServletRequest request, ModelMap model)throws Exception{
        log.info(this.getClass().getName()+"findUserId Controller Start!");

        int res = 0;

        String msg = "";
        String url = "";
        UserInfoDTO pDTO = null;

        try{
            String userEmail = CmmUtil.nvl(request.getParameter("uEmail"));
            String userName = CmmUtil.nvl(request.getParameter("uName"));
            log.info("userEmail :" + userEmail);
            log.info("UserName : " + userName);

            pDTO = new UserInfoDTO();
            pDTO.setUserEmail(userEmail);
            pDTO.setUserName(userName);

            UserInfoDTO uDTO = userInfoService.findUserId(pDTO);
            res = uDTO.getRes(); // 결과값
            if (res == 1){


                String userId = uDTO.getUserId();
                log.info("서비스에서 받아온 userId : "+ userId );
                // String userId = findUserId();
                model.addAttribute("userId",userId);

            }
        }catch (Exception e){
            res = 2;

        }finally {

        }


        log.info(this.getClass().getName()+"findUserId Controller End!");
        return "/login/idPage";
    }

    @ResponseBody
    @PostMapping("pwdCodeSend")
    public Map<String, Integer> emailCheck(HttpServletRequest request, HttpSession session) throws Exception{
        log.info(this.getClass().getName()+ " emailCheck Controller start!!");
        Map<String, Integer> map = new HashMap<>();

        String userEmail = CmmUtil.nvl(request.getParameter("userEmail"));
        String mailCode = CmmUtil.nvl(request.getParameter("mailCode"));

        session.getAttribute(mailCode);
        log.info("받는사람 : " + userEmail);
        log.info("메일코드 : " + mailCode);

        MailCodeDTO mDTO = new MailCodeDTO();
        mDTO.setToMail(userEmail);
        mDTO.setMail_code(mailCode);

        int res = mailService.pwdCode(mDTO);

        if (res == 1){
            log.info("이메일 존재 코드전송 성공 ");
        }else if (res == 2){
            log.info(" 없는 이메일 코드 전송 실패");
        }else {
            log.info("실패");
        }
        map.put("code",res);
        log.info(this.getClass().getName()+" send pwdCode ");
        return map;
    }
}
