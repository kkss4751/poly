package kopo.poly.repository.impl;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userInfo")
@DynamicUpdate
@DynamicInsert
@Builder
@Entity
public class UserInfoEntity {

    @Id // PK 값
    //AutoIncrememt와 같이 자동으로 값을 증가시킴
    //항상 Id 어노테이션과 같이 사용됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userSeq")
    private Long userSeq; // 기본키, 순번

    @NonNull
    @Column(name = "userEmail", length = 500, nullable = false)
    private String userEmail;

    @NonNull
    @Column(name = "userName", length = 500, nullable = false)
    private String userName;

    @NonNull
    @Column(name = "userId", length = 500, nullable = false)
    private String userId;

    @NonNull
    @Column(name = "userPwd", length = 100, nullable = false)
    private String userPwd;


}
