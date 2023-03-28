package kr.ac.poly.ex2.repository;

import jakarta.transaction.Transactional;
import kr.ac.poly.ex2.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import java.util.List;
import java.util.*;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // select , 특정 mno 값의 범위의 값들을 내림차순 정렬
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long form, Long to);

    // select , 특정 mno 값의 범위의 값들을 내림차순 정렬 + 페이징처리
    Page<Memo> findByMnoBetween(Long form, Long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);

    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
    int updateMemoText1(@Param("mno") Long mno, @Param("memoText") String memoText);
    //memoRepository.updateMemoText(30L,"mno가 30인 내용 수정"); 으로 30L과 내용을 두 개의인자로 호출한다.
    //호출된 내용들은 각각의 위치에서 @Param으로 각 이름, 자료형, 이름으로 Query에 전달되어 실행된다.
    // Memo m 테이블에서 memoText = : memoText를 수정하는데, mno= :mno각 번호가 동일한 것을 찾음을 의미하고
    // 여기에서 가져온 m 자체가 테이블이다
    // 가져오는작업과 upate를 같이하기위해 Transactional과 modifying을 같이 사용한다.

    @Transactional
    @Modifying // update를 위한 Transactional , Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno=:#{#param.mno} ")
    int updateMemoText2(@Param("param") Memo memo);
    //상위 updateMemoText와 어노테이션선언과 구조는 비슷하지만 함수의 인자가 한개다. Memo 테이블를 가져와서 두 개의 속성을 수정할 수 있다.
    //각 튜플은 param으로 불려와, param.memoText와 param.mno로 query문 내에서 사용되고, 이렇ㄱ ㅔ사용하는 이유는 복잡해질 가능성을 줄이기 위함이다.
    // 특징으로는 1번항목에서는 단순히 :속성명 이었다면, :#{파라미터.속성명}으로 사용하는 특징이 있다.
    // updateMemoText1과 updateMemoText2의 Memo는 java>ex2>entity>Memo 클래스 파일을 의미한다. 테이터베이스에 직접접근한게 아닌 불러온 파일을 가지고 작업하는 순서다.

    @Query(value= "select m from Memo m where m.mno > :mno",
            countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);


}
