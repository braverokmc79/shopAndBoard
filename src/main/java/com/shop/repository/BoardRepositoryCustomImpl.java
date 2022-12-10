package com.shop.repository;




import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.dto.BoardDto;
import com.shop.dto.QBoardDto;
import com.shop.entity.QBoard;
import com.shop.entity.SearchCond;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    /** 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용합니다. */
    private JPAQueryFactory queryFactory;


    /** JPAQueryFactory 생성자로 EntityManager 객체를 넣어줍니다.   */
    public BoardRepositoryCustomImpl(EntityManager em){
        this.queryFactory =new JPAQueryFactory(em);
    }


	@Override
	public Page<BoardDto> boardSearchList(SearchCond searchCond, Pageable pageable) {
		QBoard board =QBoard.board;

		System.out.println("pageable offset : " +pageable.getOffset());
        
		QueryResults<BoardDto> results=queryFactory.select(
						new QBoardDto(
								board.id, 
								board.title, 
								board.count, 
								board.user, 
								board.createDate)				
				)	
				.from(board)
				.where(
						regDtsAfter(searchCond.getSearchDateType()),
						searchByLike(searchCond.getSearchType(), searchCond.getKeyword())		
				)				
				.orderBy(board.id.desc())
	            .offset(pageable.getOffset())
	            .limit(pageable.getPageSize())				
				.fetchResults();

		List<BoardDto> content=results.getResults();
        long total=results.getTotal();
        
        //조회한 데이터를 page 클래스의 구현체인 Pageimpl 객체롤 반환합니다.
        return new PageImpl<>(content, pageable,total);
       
	}

	
	
	
	
	
    private BooleanExpression searchByLike(String searchType, String keyword){        
    	if(StringUtils.equals("title", searchType)){
            return QBoard.board.title.like("%"+keyword+"%");
        }else if (StringUtils.equals("username", searchType)){
            return QBoard.board.user.name.like("%"+keyword+"%");
        }else if(StringUtils.equals("content", searchType)) {
        	return QBoard.board.content.like("%"+keyword+"%");
        }
        return  null;
    }
	
    
    private BooleanExpression regDtsAfter(String searchDataType){
        LocalDateTime dateTime=LocalDateTime.now();

        if(StringUtils.equals("all", searchDataType) || searchDataType ==null || searchDataType.equals("")){
            return null;
        }else if(StringUtils.equals("1d",searchDataType)){
            dateTime=dateTime.minusDays(1);
        }else if(StringUtils.equals("1w", searchDataType)){
            dateTime=dateTime.minusWeeks(1);
        }else if(StringUtils.equals("1m", searchDataType)){
            dateTime=dateTime.minusMonths(1);
        }else if(StringUtils.equals("6m",searchDataType)){
            dateTime=dateTime.minusMonths(6);
        }else{
        	return null;
        }
        return  QBoard.board.createDate.after(dateTime);
    }


}
