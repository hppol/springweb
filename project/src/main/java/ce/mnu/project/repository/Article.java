package ce.mnu.project.repository;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "diaryarticle")
@Data
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long num;
	@Column(length = 20, nullable = false)
    private String author;
	 @Column(length = 50, nullable = false)
	 private String title;
	 @Column(length = 2048, nullable = false)
	 private String contents;
	 @Column(name = "created_at", nullable = false)
	 private LocalDateTime createdAt;
}
