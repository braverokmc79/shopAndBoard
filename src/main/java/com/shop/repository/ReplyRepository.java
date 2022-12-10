package com.shop.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.shop.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{

	@Transactional
	@Modifying
	@Query(value="INSERT INTO `reply` (member_id, board_id, content, create_date ) VALUES(:userId, :boardId, :content, now() )", nativeQuery = true)
	int mSave(@Param("userId") long userId, @Param("boardId") long boardId, @Param("content") String content);

	
	@Modifying
	@Query(value="SELECT r.id, r.content , r.board_id as boardId , r.member_id as memberId, r.create_date , "
			+ "(select m.name from `member` m  where m.member_id=r.member_id) as name "			
			+ "FROM reply r "
			+ " WHERE r.board_id =:boardId  ORDER BY r.create_date DESC ", nativeQuery = true)
	List<Reply>  replyList( @Param("boardId") Long id);
  
	
	
	@Query(value="SELECT count(*) as cnt FROM reply r WHERE r.board_id = :boardId ", nativeQuery = true)
	int replyTotalCount( @Param("boardId") Long boardId);
	
	

		        
		       
}
