package com.shop.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.shop.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

	private Long id;
    private String title;
    private int count;//조회수
    private Member user;
    private LocalDateTime createDate; //등록일


    @QueryProjection
	public BoardDto(Long id, String title, int count, Member user, LocalDateTime createDate) {
		super();
		this.id = id;
		this.title = title;
		this.count = count;
		this.user = user;
		this.createDate = createDate;
	}
    
    
}
