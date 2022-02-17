package org.zerock.controller.p08restcontroller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cont17")
public class Controller17 {

	@RequestMapping("/met01")
	public String method01() {
		// view 로 해석되지 않음. RestController 때문에 (responsebody를 붙이지 않아도 됨.)
		return "hello";
	}
	
	// /board/123
	@RequestMapping("/met02/{id}")
	public String method02(@PathVariable("id") Integer id) {
		System.out.println(id);
		
		return "hello met02";
	}
	
	@RequestMapping("/met03/{number}")
	public String method03(@PathVariable Integer number) {
		System.out.println(number);
		
		return "hello met03";
	}
	
	@RequestMapping("/met04/{str}")
	public String method04(@PathVariable String str) {
		System.out.println(str);
		
		return str;
	}
	
	@RequestMapping("/met05/{id}/{city}")
	public String method05(@PathVariable Integer id, @PathVariable String city) {
		System.out.println("id : " + id);
		System.out.println("city : " + city);
		
		return "hello met05";
	}
	
	@RequestMapping("/met06/id/{id}/city/{city}")
	public String method06(@PathVariable Integer id, @PathVariable String city) {
		System.out.println("id : " + id);
		System.out.println("city : " + city);
		
		return "hello met06";
	}
	
}





















