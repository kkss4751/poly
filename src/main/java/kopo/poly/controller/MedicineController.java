package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @GetMapping("/putMedicine")
    public String putMedicine() throws Exception{
        log.info(this.getClass().getName()+" putMedicine Controller Start!");


        log.info(this.getClass().getName()+" putMedicine Controller End!");
        return "/medicine/putMedicine";
    }
}
