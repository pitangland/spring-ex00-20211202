package org.zerock.controller.p01controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cont02")
public class Controller02 {

	// url : "/cont02/met01" , method : get
	@RequestMapping(value = "/met01", method = RequestMethod.GET)
	public void method01() {
		System.out.println("메소드 01 일함.");
	}
	
	// url : "/cont02/met01" , method : post
	@RequestMapping(value = "/met01", method = RequestMethod.POST) 
	public void method02() {
		System.out.println("메소드 02 일함.");
	}
	
//	@RequestMapping(value = "/met03", method = RequestMethod.GET)
	@GetMapping("/met03")
	public void method03() {
		System.out.println("메소드 03 일함.");
	}
	
	// url : "/cont02/met03", method : post 일하는 메서드 method04 작성
//	@RequestMapping(value = "/met03", method = RequestMethod.POST) 
	@PostMapping("/met03")
	public void method04() {
		System.out.println("메소드 04 일함.");
	}
	
	@RequestMapping(value="/met05", method= {RequestMethod.GET, RequestMethod.POST})
	public void method05() {
		System.out.println("메소드 05 일함.");
	}
	
}
