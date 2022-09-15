package site.metacoding.red.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@Service // IoC컨테이너에 new 된다
public class UsersService {

	private final UsersDao usersDao;
	private final BoardsDao boardsDao;

	public void 회원가입(JoinDto joinDto) { // username, password,eamil /id, createdAt는 외부에서 안 받음-> dto로 받자 / users 테이블에 접근
		// 1. 디티오를 엔티티로 변경하는 코드
		Users users = joinDto.toEntity();
		// 2. 엔티티로 insert. 디비 수행
		usersDao.insert(users);
	}

	public Users 로그인(LoginDto logindto) { // username, password
		Users usersPS = usersDao.findByUsername(logindto.getUsername()); // 굳이 엔티티로 바꿀 필요 없다
		
		if (usersPS == null) {	//확인이 더 필요
			return null;
		}

		// if로 usersPS의 password와 디티오 password 비교
		if (usersPS.getPassword().equals(logindto.getPassword())) {
			return usersPS;
		} else {
			return null;
		}
	}

	public Users 회원수정(Integer id, UpdateDto updateDto) { // id, 디티오(password, eamil)
		// 1. 영속화 (id로 select)
		Users usersPS = usersDao.findById(id);
		// 2. 영속화 된 객체 변경
		usersPS.update(updateDto);
		// 3. db 수행
		usersDao.update(usersPS);
		return usersPS;
	}

	@Transactional(rollbackFor = RuntimeException.class) // db에서 deleteById를 하면 write수행하는 동시에 트랜젝션이 걸린다
	public void 회원탈퇴(Integer id) { //
		usersDao.deleteById(id);
		boardsDao.updateByUsersId(id); // 해당회원이적은 글을 모두 찾아서 usersId를 null로 업데이트();
	} // users(delete), boards(update) 둘다 연결 / 회원탈퇴 -> user 삭제, boards테이블의 user를 null로
		// 바꾸자

	public boolean 유저네임중복확인(String username) { // 데이터 한 건 받으니까 디티오 x
		Users usersPS = usersDao.findByUsername(username);

		if (usersPS == null) {// 있으면 true, 없으면 false
			return false; // 아이디 중복 x -> 회원가입 진행 가능
		} else {
			return true; // 아이디 중복
		}
	}

	public Users 회원정보보기(Integer id) {
		Users usersPS = usersDao.findById(id);
		return usersPS;

	}
}