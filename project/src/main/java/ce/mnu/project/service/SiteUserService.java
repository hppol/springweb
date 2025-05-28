package ce.mnu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ce.mnu.project.domain.UserDTO;
import ce.mnu.project.repository.SiteUser;
import ce.mnu.project.repository.SiteUserRepository;

@Service
public class SiteUserService {
	@Autowired
	private SiteUserRepository userRepository;

	public void save(UserDTO dto) {
		SiteUser user = new SiteUser(dto.getName(), dto.getEmail(), dto.getUserid(), dto.getPasswd());
		userRepository.save(user);
	}
	
	public SiteUser findByUserid(String userid) {
		return userRepository.findByUserid(userid);
	}

}
