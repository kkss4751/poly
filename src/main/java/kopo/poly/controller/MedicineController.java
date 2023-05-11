package kopo.poly.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import kopo.poly.dto.MedicineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Slf4j
@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @GetMapping("/medicineAPI")
    public String medicine(ModelMap model) throws Exception{


        return "";
    }

    @GetMapping("/putMedicine")
    public String putMedicine() throws Exception{
        log.info(this.getClass().getName()+" putMedicine Controller Start!");


        log.info(this.getClass().getName()+" putMedicine Controller End!");
        return "/medicine/putMedicine";
    }
}
