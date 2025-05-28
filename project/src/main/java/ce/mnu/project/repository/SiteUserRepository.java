package ce.mnu.project.repository;

import org.springframework.data.repository.CrudRepository;

public interface SiteUserRepository extends CrudRepository<SiteUser, Long> {
	SiteUser findByUserid(String userid);
}
