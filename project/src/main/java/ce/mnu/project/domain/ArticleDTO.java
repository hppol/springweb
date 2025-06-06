package ce.mnu.project.domain;

import lombok.Data;

@Data
public class ArticleDTO {
	private String author;
	private String title;
	private String contents;
	private String created_at;
}
