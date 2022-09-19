package site.metacoding.red.domain.boards;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

public interface BoardsDao {
	public void insert(Boards boards);
	public List<MainDto> findAll(@Param("startNum") int startNum,@Param("keyword") String keyword, @Param("row") int row);
	public PagingDto paging(@Param("page") Integer page, @Param("keyword") String keyword, @Param("row") int row);
	public Boards findById(Integer id);		//처음에는 Boards로 받고 나중에 수정 / id로 찾을거라서
	public void update(Boards boards);
	public void deleteById(Integer id);		// id로 삭제할거라서
	public void updateByUsersId(Integer usersId);
}	//param이 여러개 일때는 @을 붙여줘야 한다