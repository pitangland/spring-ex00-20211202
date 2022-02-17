package org.zerock.controller.p03controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.controller.p03controller.bean.Bean01;
import org.zerock.controller.p03controller.bean.Bean02;

@Controller
@RequestMapping("/cont06")
public class Controller06 {

	@RequestMapping("/met01")
	public void method01(HttpServletRequest request) {
		request.setAttribute("myattr12", "hello world");
	}

	@RequestMapping("/met02")
	public void method02(Model model) {
		
		// 4. add attribute
		model.addAttribute("myattr1", "myvalue1");
		
		// 5. forward/redirect
		
	}
	
	// 연습 : 요청경로 - /cont06/met03, view name - /cont06/met03
	// 		 view 에서 사용하는 attribute 명 : "myattr2", myattr2의 값 : "myvalue2"
	//  	 가 되도록 method03 작성
	// 또한, view도 작성해서 myattr2가 myvalue2를 출력하는지 확인
	@RequestMapping("/met03")
	public void method03(Model model) {
		model.addAttribute("myattr2", "myvalue2");
	}
	
	@RequestMapping("/met04")
	public void method04(Model model) {
		// 3. 
		Bean01 bean = new Bean01();
		bean.setAge(99);
		bean.setName("john");
		
		// 4.
		model.addAttribute("myBean", bean);
	}
	
	@RequestMapping("/met05")
	public void method05(Model model) {
		// 3. 
		Bean01 bean = new Bean01();
		bean.setAge(88);
		bean.setName("kim");
		
		// 4.
		// attribute의 이름이 클래스(type)의 이름(to lower camel case)과 같은 경우 생략 가능.
//		model.addAttribute("bean01", bean);
		model.addAttribute(bean);
		
	}
	
	@RequestMapping("/met06")
	public void method06(Model model) {
		model.addAttribute("attr1", "val1");
		model.addAttribute("attr2", "val2");
	}
	
	@RequestMapping("/met07")
	public void method07(String name, String city, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("city", city);
	}
	
	@RequestMapping("/met08")
	public String method08(@ModelAttribute("name") String name, 
						   @ModelAttribute("city") String city) {
		
		return "/cont06/met07";
	}
	
	@RequestMapping("/met09")
	public void method09(Bean01 bean, Model model) {
		model.addAttribute("bean", bean);
	}
	
	@RequestMapping("/met10")
	public String method10(@ModelAttribute("bean") Bean01 bean) {
		
		return "/cont06/met09";
	}
	
	@RequestMapping("/met11")
	public void method11 (@ModelAttribute Bean01 bean) {
		
	}
	
	@RequestMapping("/met12")
	public String method12(Bean01 bean) {
		return "/cont06/met11";
	}
	
	// /cont06/met13?city=seoul&address=gangnam
	@RequestMapping("/met13")
	public void method13(Bean02 bean) {
		// Bean02 클래스 완성
		// /cont06/met13.jsp 를 작성해서 seoul, gangnam 출력
	}
	
}





























