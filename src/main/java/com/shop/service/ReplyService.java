package com.shop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.ReplyDto;
import com.shop.entity.Board;
import com.shop.entity.Reply;
import com.shop.entity.SearchCond;
import com.shop.repository.ReplyPageRepository;
import com.shop.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
	
	private final ReplyRepository replyRepository;
	
	private final ReplyPageRepository replyPageRepository;
	
	public Page<Reply> replyList(Pageable pageable) {
		return replyRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<ReplyDto> replySearchList(Pageable pageable, Board getBoard) {
		return replyPageRepository.replySearchList(pageable,  getBoard);
	}
	
	
	public List<Reply>  replyList2(Pageable pageable, Long id) {
		return  replyRepository.replyList(id);
	}

	public int replyTotalCount(Pageable pageable, Long id) {
		return  replyRepository.replyTotalCount(id);
	}
	
	

}
