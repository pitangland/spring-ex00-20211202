package org.zerock.domain.project1;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class BoardVO {
// vo : value object, dto : data trasaction object = bean과 같음.
	
	private Integer id;
	private String title;
	private String content;
	private String writer;
//	@JsonFormat(shape = Shape.STRING)
	private LocalDateTime inserted;
//	@JsonFormat(shape = Shape.STRING)
	private LocalDateTime updated;
	private String nickName;
	private Integer replyCount;
	private Boolean hasFile;
	
	public String getCustomInserted() {
		// 현재일시
		LocalDateTime now = LocalDateTime.now(ZoneId.of("+09:00"));
		
		LocalDateTime beforeOneDayFromNow = now.minusDays(1);
		
		if (inserted.isBefore(beforeOneDayFromNow)) {
			return inserted.toLocalDate().toString();
		} else {
			return inserted.toLocalTime().toString();
		}
		
	}
	
}
