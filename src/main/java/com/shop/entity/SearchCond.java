package com.shop.entity;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class SearchCond {

    /**
     * 현재 시간과  등록일을
     * all : 등록일 전체
     * 1d: 최근 하루 동안 
     * 1w : 최근 일주일 
     * 1m : 최근 한달 
     * 6m : 최근 6개월 
     */
    private String searchDateType;


    /**
	 * 검색 유형 
	 * 제목 (title), 작성자(username) , 내용(content)
     */
    private String searchType;


    
    /**
     * 조회할 검색어 저장할 변수입니다.
     */
    private String keyword="";

    
	
	public String getBoardsLink() {
		UriComponentsBuilder builder=UriComponentsBuilder.fromPath("")
				.queryParam("searchDateType", searchDateType)
				.queryParam("searchType", searchType)
				.queryParam("keyword", keyword);
		return builder.toUriString();	
	}
	
}
