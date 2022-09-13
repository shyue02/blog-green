package site.metacoding.red.domain.boards;

import java.util.List;

public interface BoardsDao {
	public void insert(Boards boards);
	public List<Boards> findAll();
	public Boards findById(Integer id);		//처음에는 Boards로 받고 나중에 수정 / id로 찾을거라서
	public void update(Boards boards);
	public void deleteById(Integer id);		// id로 삭제할거라서
	public void updateByUsersId(Integer id);
}