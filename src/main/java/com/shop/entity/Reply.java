package com.shop.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reply")
@ToString
public class Reply {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private Long id; //시퀀스, auto_increment

    @Column(nullable = false, length = 200)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="member_id")    
    private Member user;


    @CreationTimestamp
    private LocalDateTime createDate;




}
