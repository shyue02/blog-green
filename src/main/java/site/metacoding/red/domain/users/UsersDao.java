package site.metacoding.red.domain.users;

import java.util.List;

public interface UsersDao {
	public void insert(Users users);
	public List<Users> findAll();
	public Users findById(Integer id);		//처음에는 Boards로 받고 나중에 수정 / id로 찾을거라서
	public void update(Users users);
	public void deleteById(Integer id);
	public Users findByUsername(String username);	
}