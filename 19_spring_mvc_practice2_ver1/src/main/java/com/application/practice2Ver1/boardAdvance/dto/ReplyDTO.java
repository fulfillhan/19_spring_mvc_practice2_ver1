package com.application.practice2Ver1.boardAdvance.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDTO {
	
	private long replyId;
	private String writer;
	private String content;
	private String passwd;
	private Date  enrollAt;
	private long boardId;
	
	

}
