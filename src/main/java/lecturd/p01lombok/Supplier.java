package lecturd.p01lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

	// 필드 작성 및 lombok 어노테이션으로 
	// getter, setter, toString 생성자 등을 만들
	private int id;
	private String name;
	
}
