package com.shop.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.shop.entity.Board;
import com.shop.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyDto {

	private Long id;
    private String content;
    private Member user;
    private Board board; 
    private LocalDateTime createDate; 

    @QueryProjection
	public ReplyDto(Long id, String content,  Member user, Board board,  LocalDateTime createDate) {
		super();
		this.id = id;
		this.content = content;
		this.user = user;
		this.board=board;
		this.createDate = createDate;
	}
    
}
