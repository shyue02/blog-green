package site.metacoding.red.web.dto.request.users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
	private String username;
	private String password;
	private boolean remember;	// true or false 로 나오니까 boolean
}