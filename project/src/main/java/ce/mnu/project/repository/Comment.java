package ce.mnu.project.repository;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable=false)
    private int likes = 0;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    // 댓글 좋아요 연관관계 추가 (cascade = REMOVE, orphanRemoval = true)
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();
}
