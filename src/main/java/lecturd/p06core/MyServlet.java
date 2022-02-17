package lecturd.p06core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyServlet {

	private MyDao myDao;
	
//	이 생성자를 대신 만들어주는게 AllArgsConstructor
//	public MyServlet(MyDao myDao) {
//		this.myDao = myDao;
//	}
	
}
