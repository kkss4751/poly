package kopo.poly.repository;

import kopo.poly.repository.impl.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {

    /* 회원존재 여부 체크 // 이메일로 중복체크함 //이메일중복체크 확인
    * 쿼리 예: Select * From userInfo WHere userId = 'dddd' */
    Optional<UserInfoEntity> findByUserEmail(String userEmail);

    //로그인
    Optional<UserInfoEntity> findByUserIdAndUserPwd(String userId, String userPwd);

    /* 유저 아이디 중복체크 */
    Optional<UserInfoEntity> findByUserId(String userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE USERSEQ A SET A.READ_CNT = IFNULL(A.READ_CNT, 0) + 1 WHERE A.USERSEQ = : noticeSeq",
            nativeQuery = true)
    int updateReadCnt(@Param("userSeq") Long userSeq);

}
