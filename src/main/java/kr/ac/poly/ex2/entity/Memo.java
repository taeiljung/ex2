package kr.ac.poly.ex2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_memo")
@ToString
@Getter //필수
@Setter
@Builder //빌더 사용을 위해 AllArgsConstructor와 NoArgsConstructor를 사용해야 컴파일에러가 나지 않는다.
@AllArgsConstructor
@NoArgsConstructor

public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity == 사용하는 DB가 키 생성을 결정
    private long mno;
    // pk값 지정 @Id작성, @GeneratedValue로 설정, [키 생성 전략]
    @Column(length = 200, nullable = false) //추가적인 필드 사용을위한 @Column 길이최대 200, not null
    private String memoText;
}
