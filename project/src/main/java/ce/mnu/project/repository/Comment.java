package ce.mnu.project.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="commnet")
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private String author;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

}
