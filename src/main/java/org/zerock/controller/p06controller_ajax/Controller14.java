package org.zerock.controller.p06controller_ajax;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cont14")
public class Controller14 {

	@RequestMapping("/met01")
	@ResponseBody
	public String method01(@RequestParam("name") String name) {
		System.out.println("/cont14/met01 일함.");
		System.out.println(name);
		
		return "hello";
	}
	
	@RequestMapping("/met02")
	@ResponseBody
	public String method02(@RequestParam("city") String city) {
		System.out.println(city);
		
		return "hello";
	}
	
	@RequestMapping("/met03")
	@ResponseBody
	public String method03(@RequestParam("city") String city, @RequestParam("name") String name) {
		System.out.println(city);
		System.out.println(name);
		
		return "hello";
	}
	
	@RequestMapping("/met04")
	@ResponseBody
	public String method04(String data1) {
		System.out.println(data1);
		
		return "hello";
	}
	
	@RequestMapping("/met05")
	@ResponseBody
	public void method05(@RequestParam("name") String name, @RequestParam("address") String address) {
		System.out.println(name);
		System.out.println(address);
	}
	
}



























