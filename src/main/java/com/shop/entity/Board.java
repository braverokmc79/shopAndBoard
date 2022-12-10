package com.shop.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity //User 클래스가 MySQL 에 테이블이 생성이 된다.
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//기본키 생성을 데이터베이스에 위임 한다. auto_increment
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false, length =100)
    private String title;

    //섬머노트 라이브러리 <html> 태그가 섞여서 디자인됨.
    @Lob //대용량 데이터
    private String content;

    //@ColumnDefault("0")
    private int count;//조회수

    /**
     * Member와 Team 사이가 다대일 @ManyToOne 관계로 매핑되어 있는 상황에서,*
     * @ManyToOne 어노테이션에 fetch 타입을 줄 수 있다.*
     * FetchType.LAZY
     * 무에서는 가급적 지연 로딩만 사용하다. 즉시 로딩 쓰지 말자.
     *
     * JPA 구현체도 한번에 가저오려고 하고, 한번에 가져와서 쓰면 좋지 않나?
     * 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생한다.
     *
     *즉시 로딩(Eager Loading)과 지연 로딩(Lazy Loading)
     * '즉시 로딩'은 불필요한 조인까지 포함해서 처리하는 경우가 많기 때문에 '지연 로딩'의 사용을 권장하고 있습니다.
     *
     * 각 연관관계의 default 속성은 다음과 같습니다.*
     * @OneToMany: LAZY
     * @ManyToOne: EAGER
     * @ManyToMany: LAZY
     * @OneToOne: EAGER
    ⌨️ 즉시 로딩(Eager Loading)과 지연 로딩(Lazy Loading)의 주의할 점
    가급적이면 지연 로딩(Lazy Loading)만 사용(특히 실무에서)
    즉시 로딩(Eager Loading)을 적용하면 예상하지 못한 SQL이 발생할 수 있음
    즉시 로딩(Earge Loading)은 JPQL에서 N+1 문제를 일으킴
     *
     */
    @ManyToOne(fetch = FetchType.EAGER) //Many=Many, User=One
    @JoinColumn(name="member_id")
    //@JsonIgnore
    private Member user; //DB 는 오브젝트를 저장활 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.1


    //mappedBy 연관관계의 주인이 아니다 난 FK 가 아니에요. DB에 컬럼을 만들지 마세요.
    /**
     *     @ManyToOne
     *     @JoinColumn(name = "boardId")
     *     private Board board;
     *     mappedBy 에는 board 를 적는다.
     */
	//EAGER 전략일 경우 replys 의 데이터를 같이 가져오나, LAZY 전략일경우  replys 할때 데이터를 가져온다.
	//LAZY 사용시    @JsonIgnore 사용
    //Column으로 쓰지않는 변수에 대한 선언. @Transient
    //@Transient
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) //하나의 게시판에 여러개의 댓글이 존재 , 따라서 oneToMany 의 기본전략은 LAZY 이다.
    @JsonIgnore  //@JsonIgnore 애노테이션을 사용함으로써   List<Reply> 객체는 포함하지 않는다.  그러나 replys 호출하는 순간 데이터를 가져오게 된다. 
    //@JsonIgnoreProperties({"board"})  
    @OrderBy("id desc")
    private List<Reply> replys;


    @CreationTimestamp
    private LocalDateTime createDate; //등록일


     


}





