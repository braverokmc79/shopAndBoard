package com.shop.service;

import java.security.Principal;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.BoardDto;
import com.shop.dto.ReplySaveRequestDto;
import com.shop.entity.Board;
import com.shop.entity.Member;
import com.shop.entity.Reply;
import com.shop.entity.SearchCond;
import com.shop.repository.BoardRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

/**
 * 서비스 1. 트랜잭션 관리 2. 두개 이상 서비스 create, update,
 *
 * select 만 있을 경우 : @Transactional(readOnly = true) 스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을
 * 해줌. IoC 를 해준다. *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final BoardRepository boardRepository;

	final private MemberService userService;

	private final ReplyRepository replyRepository;
	
	private final MemberRepository userRepository;
	
	// 글쓰기
	public int boardSave(Board board, Principal principal) {
		int result = 0;
		Member user = userService.findByEmail(principal.getName());
		if (user != null) {
			log.info("가져온 유저 정보  {} ", user.toString());
			board.setUser(user);
			board.setCount(0);
			boardRepository.save(board);
			result = 1;
		}
		return result;
	}

	@Transactional(readOnly = true)
	public Page<BoardDto> boardSearchList(SearchCond searchCond, Pageable pageable) {
		return boardRepository.boardSearchList(searchCond, pageable);
	}

	// 글 상세보기
	@Transactional(readOnly = true)
	public Board boardDetail(Long id) {
		return boardRepository.findById(id).orElseThrow(EntityExistsException::new);
	}

	public void deleteByid(Long id) {
		boardRepository.deleteById(id);
	}

	
	public void updateBoard(long id, Board requestBoard) {
		Board board=boardRepository.findById(id).orElseThrow(EntityExistsException::new); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시에 트랜잭션이 service 가 종료될때) 트랜잭션이 종료. 이때 더티체킹 -자동 업데이트가 됨. flush		
	}

	//댓글 등록
	public void replySave(ReplySaveRequestDto replySaveRequestDto) {

/*
		User user =userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(EntityExistsException::new);
		Board board =boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(EntityExistsException::new);
		

		Reply reply=Reply.builder()
				.user(user)
				.board(board)
				.content(replySaveRequestDto.getContent())
				.build();
				
		replyRepository.save(reply);
*/
		int result=replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		log.info("result   :  {}  "   , result);
	}

	
	//댓글 삭제
	public int replyDelete(long replyId,  Principal principal) {
	  Reply reply= replyRepository.findById(replyId).orElseThrow(EntityExistsException::new);

	  Member member =userRepository.findByEmail(principal.getName());

	  if(reply.getUser().getId() ==member.getId()) {
		  replyRepository.deleteById(replyId);
		  return 1;
	  }
	  
	  return 0;		
	}
	
	
	

}
