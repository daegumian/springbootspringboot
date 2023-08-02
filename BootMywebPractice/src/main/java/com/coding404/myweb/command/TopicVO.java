package com.coding404.myweb.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicVO {
	
	private Integer top_num;
	private String top_regdate;
	private String top_id;
	private String top_title;
	private String top_content;

}
