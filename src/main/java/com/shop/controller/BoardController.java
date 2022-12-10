package com.shop.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.dto.BoardDto;
import com.shop.dto.ReplyDto;
import com.shop.entity.Board;
import com.shop.entity.Member;
import com.shop.entity.Pagination;
import com.shop.entity.Reply;
import com.shop.entity.SearchCond;
import com.shop.service.BoardService;
import com.shop.service.MemberService;
import com.shop.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	
	private final BoardService boardService;
	
	
	private final ReplyService replyService;
	
	private final MemberService memberService;
	

	//@PageableDefault(size = 1, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    @GetMapping(value={"/board", "/board/"})
    public String index(SearchCond searchCond ,
						@RequestParam(value = "page",defaultValue ="1", required = false)
    			Optional<Integer> page, Model model) {
        //페이징을 위해서 PageRequest.of 메소드를 통해 Pageable 객체를 생성합니다.
        //첫 번째 파라미터로 조회할 페이지 번호, 두 번째 파라미터로 한 번에 가지고 올 데이터 수를 넣어줍니다.
        //URL 경로에 페이지 번호가 있으면 해당 페이지르 조회하도록 세팅하고, 페이지 번호가 없으면 0페이지를 조회하도록 합니다.    	
    	int pageSize=5; //한 페이지당 게시글 수  5
        Pageable pageable= PageRequest.of(page.isPresent()? page.get()-1 :0, pageSize);
        
    	Page<BoardDto> boards= boardService.boardSearchList(searchCond , pageable);
    	
    	//페이지 블럭 계산을 위한 처리
    	//전체페이지, 현재페이지, 한 페이지당 게시글 수,  페이지 블럭(range)
    	Pagination pagination =new Pagination((int)boards.getTotalElements(),  page.get(),  pageSize , 5);

    	model.addAttribute("boards", boards);
    	model.addAttribute("searchCond", searchCond);
    	model.addAttribute("pagination", pagination);
    	return "board/index";
    }
    
    
    //상세보기
    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, SearchCond searchCond ,  Principal principal, 
			@RequestParam(value = "page",defaultValue ="1", required = false) 
			Optional<Integer> page,    		
    		Model model) {
    
    	int pageSize=5;
     //   Pageable pageable= PageRequest.of(page.isPresent()? page.get()-1 :0, pageSize);  
//        Page<Reply>  replyList= replyService.replyList(pageable);
//        Pagination pagination =new Pagination((int)replyList.getTotalElements(),  page.get(),  pageSize , 5);        
     //   List<Reply>  replyList= replyService.replyList2(pageable , id);
   
        
//        int totalCount=replyService.replyTotalCount(pageable , id);      
//        Pagination pagination =new Pagination(totalCount,  page.get(),  pageSize , 5 );
//   

    	Member member =memberService.findByEmail(principal.getName());    	
    	Board  board=boardService.boardDetail(id);
    	
        Pageable pageable= PageRequest.of(page.isPresent()? page.get()-1 :0, pageSize);        
       	Page<ReplyDto> replyList= replyService.replySearchList(pageable, board);
       	Pagination pagination =new Pagination((int)replyList.getTotalElements(),  page.get(),  pageSize , 5);
 
    	model.addAttribute("auth",member);
    	model.addAttribute("board", board);
    	model.addAttribute("totalCount", replyList.getTotalElements());
    	model.addAttribute("replyList", replyList);
    	model.addAttribute("pagination", pagination);
    	return "board/detail";
    }
    

    
    
    
    @GetMapping("/board/saveForm")
    public String saveForm() {
    	return "board/saveForm";
    }
    
    
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable long id, Model model) {
    	model.addAttribute("board", boardService.boardDetail(id));    	
    	return "board/updateForm";
    }
    
    
    
    
    
    
    
    
}

