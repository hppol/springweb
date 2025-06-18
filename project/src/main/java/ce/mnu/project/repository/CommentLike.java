package ce.mnu.project.repository;

import jakarta.persistence.*;

@Entity
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Comment comment;

    private String userId;


    public void setComment(Comment comment) { this.comment = comment; }
    public void setUserId(String userId) { this.userId = userId; }
    // getter도 추가
}

