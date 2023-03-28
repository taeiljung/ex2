package kr.ac.poly.ex2.repository;

import kr.ac.poly.ex2.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {


}
