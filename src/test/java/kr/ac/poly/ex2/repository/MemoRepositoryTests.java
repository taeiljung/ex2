package kr.ac.poly.ex2.repository;

import jakarta.transaction.Transactional;
import kr.ac.poly.ex2.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//import org.springframework.data.domain.*; 이게 더 나을지도.
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

//import java.util.*;
import java.util.Optional;
import java.util.stream.IntStream;

//3월21일 ,CRUD 수업
@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;


    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }


    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }
    @Test
    public void testSelect(){
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);


        System.out.println("====================================="); // 순서대로 처리된다.

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }
    @Transactional

    public void testSelect2(){
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno);

        System.out.println("====================================="); // 이게 먼저 출력되고 그 다음에 데이터베이스에 접근한다.
        System.out.println(memo);
    }


    public void testUpdate(){
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
    }

    public void testDelete(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
//        for (Long mno=0L;mno<=500L;mno++){
//            memoRepository.deleteById(mno);
//        }

    }
//    @Test
//    public void testPageDefault(){
//        Pageable pageable = PageRequest.of(0,10);
//        Page<Memo> result = memoRepository.findAll(pageable);
//        System.out.println(result);
//    }
    @Test
    public void testPageDefault2(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("-----------------------");
        System.out.println("Total Pages: : "+result.getTotalPages());
        System.out.println("Total Count: : "+result.getTotalElements());
        System.out.println("Page Number: : "+result.getNumber());
        System.out.println("Page Size: : "+result.getSize());
        System.out.println("has next page?: : "+result.hasNext());
        System.out.println("first page?: : "+result.isFirst());
        System.out.println("-----------------------");
        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }
    @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0,10,sort1);
        //페이징과 소팅을 한번에
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo->{
            System.out.println(memo);
        });
    }

}
