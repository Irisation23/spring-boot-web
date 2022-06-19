package com.calmdev.ex1.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_memo")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    /**
     * 테이블에 컬럼으로 생성되지 않아도 되는 필드를 지정하는 경우에는
     * @Transient 어노테이션을 적용함.
     * @Column(columnDefinition = "varchar(255) default 'Yes'")
     * columnDefinition 은 기본값을 지정하기 위함 임.
     */

    @Column(length = 200, nullable = false)
    private String memoText;
}
