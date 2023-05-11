package kopo.poly.dto;

import lombok.Data;

@Data
public class MedicineDTO {

    private String entpName; //업체명
    private String itemName; // 제품명
    private String efcyQesitm; // 약의 효능
    private String useMethodQesitm; // 약 사용법
    private String atpnWarnQesitm; // 사용하기전 주의사항
    private String atpnQesitm; // 약 상요상 주의사항
    private String intrcQesitm; // 약을 사용하는 동안 주의해야 할 약 또는 음식
    private String seQesitm; // 부작용
    private String depositMethodQesitm; // 보관법

}
