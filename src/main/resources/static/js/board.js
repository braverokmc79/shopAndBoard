let board ={
	
	init:function(){
		$("#btn-save").on("click",()=>{
			this.save();
		});
				
		$("#btn-delete").on("click",()=>{
			this.deleteById();
		});
		
		$("#btn-update").on("click",()=>{
			this.boardUpdate();
		});

		$("#btn-reply-save").on("click", ()=>{
			this.replySave();
		});
		
		$(".replyDelete").on("click", (e)=>{
			  if(confirm("정말 삭제 하시겠습니까?")){
				this.replyDelete($(e.target).data("boardid"),  $(e.target).data("replyid"));
			  }
				
		});
		
		
	},


	save:function(){
		const $Home=$("#home").val();
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");

		let data={
			title:$("#title").val(),
			content:$("#content").val(),
		};

		console.log(data);
		console.log("$Home : " +$Home);


		$.ajax({
			type:"POST",
			beforeSend:function(xhr){
				xhr.setRequestHeader(header,token);
			},
			url:$Home+"api/board",
			data:JSON.stringify(data),
			contentType:"application/json; charset=urf-8", 
			dataType:"json"	
		}).done(function(res, status){
			console.log(res, status);
			//alert("글쓰기가 완료 되었습니다.");
			location.href=$Home+"board";
			
			
		}).fail(function(res, status, error){
			console.log(res, status, error);
			console.log("res.responseText :" +res.responseText);
			console.log(JSON.stringify(res));
			alert(res.responseText);
		});


	},
	
	

	deleteById:function(){
		
		if(confirm("정말 삭제 하시겠습니까?")){
				const id=$("#id").text();
				const $Home=$("#home").val();
				const token = $("meta[name='_csrf']").attr("content");
				const header = $("meta[name='_csrf_header']").attr("content");
		
				$.ajax({
					type:"DELETE",
					beforeSend:function(xhr){
						xhr.setRequestHeader(header,token);
					},
					url:$Home+"api/board/"+id,				
					dataType:"json"	
				}).done(function(res, status){
					console.log(res, status);
					//alert("글이 삭제 되었습니다.");
					location.href= $Home+"board";
					
					
				}).fail(function(res, status, error){
					console.log(res, status, error);
					console.log("res.responseText :" +res.responseText);
					console.log(JSON.stringify(res));
					alert(res.responseText);
				});
		}


	},
	
		
	
	
	boardUpdate:function(){
		const id=$("#id").val();
		const $Home=$("#home").val();
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");
		
		let data={
			title:$("#title").val(),
			content:$("#content").val()			
		};

		console.log(data);
		console.log("$Home : " +$Home);


		$.ajax({
			type:"PUT",
			beforeSend:function(xhr){
				xhr.setRequestHeader(header,token);
			},
			url:$Home+"api/board/"+id,
			data:JSON.stringify(data),
			contentType:"application/json; charset=urf-8", 
			dataType:"json"	
		}).done(function(res, status){
			console.log(res, status);
			//alert("글 수정이 완료 되었습니다.");
			location.href=$Home+"board/"+id;		
			
		}).fail(function(res, status, error){
			console.log(res, status, error);
			console.log("res.responseText :" +res.responseText);
			console.log(JSON.stringify(res));
			alert(res.responseText);
		});

	},
	
	
	
	
	replySave:function(){
		const $Home=$("#home").val();
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");
		const boardId=$("#boardId").val();
		
		let data={
			content:$("#reply-content").val(),
			boardId:boardId
		};

		console.log(data);
		console.log("$Home : " +$Home  + " boardId : "+boardId);

	
		$.ajax({
			type:"POST",
			beforeSend:function(xhr){
				xhr.setRequestHeader(header,token);
			},
			url:$Home+`api/board/${boardId}/reply`,
			data:JSON.stringify(data),
			contentType:"application/json; charset=urf-8", 
			dataType:"json"	
		}).done(function(res, status){
			console.log(res, status);
			//alert("댓글작성이  완료 되었습니다.");
			location.href=$Home+`board/${boardId}`;
			
			
		}).fail(function(res, status, error){
			console.log(res, status, error);
			console.log("res.responseText :" +res.responseText);
			console.log(JSON.stringify(res));
			alert(res.responseText);
		});


	},
	
	
	
	
		
	replyDelete:function(boardId, replyId){
		console.log(boardId, replyId);
		
		const $Home=$("#home").val();
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");
	
		$.ajax({
			type:"DELETE",
			beforeSend:function(xhr){
				xhr.setRequestHeader(header,token);
			},
			url:$Home+`api/board/${boardId}/reply/${replyId}`,
			dataType:"json"	
		}).done(function(res, status){
			console.log(res, status);
			//alert("댓글 삭제 되었습니다.");
			location.href=$Home+`board/${boardId}`;

		}).fail(function(res, status, error){
			console.log(res, status, error);
			console.log("res.responseText :" +res.responseText);
			console.log(JSON.stringify(res));
			alert(res.responseText);
		});


	},
	
	

}

board.init();
