package com.coding404.myweb.command;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicVO {
	
	private Integer top_num;
	private LocalDateTime top_regdate;
	private String top_id;
	private String top_title;
	private String top_content;

}
