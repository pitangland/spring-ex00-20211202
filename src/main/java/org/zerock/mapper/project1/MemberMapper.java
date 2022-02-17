package org.zerock.mapper.project1;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.project1.MemberVO;

public interface MemberMapper {

	public int insert(MemberVO member);
	
	public MemberVO select(String id);
	
	public int update(MemberVO member);
	
	public int delete(String id);
	
	// 실제로는 일어나면 안되는 일.
	public List<MemberVO> list();

	// 페이지 처리
	public List<MemberVO> listPage(@Param("from") Integer from, @Param("items") Integer numberPerPage);

	public Integer getCountRows();

	public MemberVO selectByNickName(String nickName);
	
}
