package com.web.board.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardComment {
	private int boardCommentNo;
	private int level;
	private String boardCommentContent;
	private String boardCommentWriter;
	private int boardRef;
	private int boardCommentRef;
	private Date boardCommentDate;
}
