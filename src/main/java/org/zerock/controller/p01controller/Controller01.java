package org.zerock.controller.p01controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//controller가 붙은 클래스 bean
@Controller
@RequestMapping("/cont01")
public class Controller01 {
	
	// "/cont01"
	@RequestMapping("")
	public void method00() {
		System.out.println("메소드 00 일함.");
	}
	
	// "/cont01/met01"
	@RequestMapping("/met01")
	public void method01() {
		System.out.println("메소드 01 일함.");
	}
	
	// "/cont01/met02" 요청이 왔을 때 일하는 메소드(method02)를 작성하세요. return : void
	@RequestMapping("/met02")
	public void method02() {
		System.out.println("메소드 02 일함.");
	}
	
	// "/cont01/met03", "/cont01/met04" 로 요청이 왔을 때 일하는 메서드
	@RequestMapping({"/met03", "/met04"}) 
	public void method03() {
		System.out.println("메소드 03 일함.");
	}
	
	// "/cont01/met05", "/cont01/met06" 로 요청이 왔을 때 일하는 메서드 method 05 작성
	@RequestMapping({"/met05", "/met06"}) 
	public void method05() {
		System.out.println("메소드 05 일함.");
	}
	
}
