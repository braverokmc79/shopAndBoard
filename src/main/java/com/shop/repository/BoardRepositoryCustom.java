package com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shop.dto.BoardDto;
import com.shop.entity.SearchCond;

public interface BoardRepositoryCustom {
	
	 Page<BoardDto> boardSearchList(SearchCond searchCond, Pageable pageable);
	
}
