package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailCodeDTO {

    String toMail; // 받는사람
    String mail_code; // 메일로 전송될 코드

}
