package ce.mnu.project.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diaryuser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=20, nullable=false)
	private String name;
	
	@Column(length=50, unique=true, nullable=false)
	private String email;
	
	@Column(length=20, unique=true, nullable=false)
	private String userid;
	
	@Column(length=20, name="password", nullable=false)
	private String passwd;

	public SiteUser(String name, String email, String userid, String passwd) {
		this.name = name;
		this.email = email;
		this.userid = userid;
		this.passwd = passwd;
	}

}
