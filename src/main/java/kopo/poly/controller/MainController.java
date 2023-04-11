package kopo.poly.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping(value = "/main")
@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping(value = "/index")
    public String home() throws Exception{
        log.info(this.getClass().getName()+" home Start!");

        log.info(this.getClass().getName()+" home End!");
        return "/index";
    }


}
