package com.shop.repository;




import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.dto.QReplyDto;
import com.shop.dto.ReplyDto;
import com.shop.entity.Board;
import com.shop.entity.QReply;

public class ReplyRepositoryCustomImpl implements ReplyRepositoryCustom {

    /** 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용합니다. */
    private JPAQueryFactory queryFactory;


    /** JPAQueryFactory 생성자로 EntityManager 객체를 넣어줍니다.   */
    public ReplyRepositoryCustomImpl(EntityManager em){
        this.queryFactory =new JPAQueryFactory(em);
    }


	@Override
	public Page<ReplyDto> replySearchList(Pageable pageable, Board getBoard) {
		QReply reply =QReply.reply;
		
		QueryResults<ReplyDto> results=queryFactory.select(
						new QReplyDto(
								reply.id, 
								reply.content, 								
								reply.user, 
								reply.board,
								reply.createDate)				
				)	
				.from(reply)	
				.where(
						reply.board.eq(getBoard)
				)	
				.orderBy(reply.id.desc())
	            .offset(pageable.getOffset())
	            .limit(pageable.getPageSize())				
				.fetchResults();

		List<ReplyDto> content=results.getResults();
        long total=results.getTotal();
        
        //조회한 데이터를 page 클래스의 구현체인 Pageimpl 객체롤 반환합니다.
        return new PageImpl<>(content, pageable,total);
       
	}
		
	
	
	
  

}
