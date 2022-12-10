package com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shop.dto.ReplyDto;
import com.shop.entity.Board;

public interface ReplyRepositoryCustom {
	
	 Page<ReplyDto> replySearchList(Pageable pageable, Board getBoard);
	
}
