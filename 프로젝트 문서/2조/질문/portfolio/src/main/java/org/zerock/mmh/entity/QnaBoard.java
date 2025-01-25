package org.zerock.mmh.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.zerock.mmh.generator.IdGenerator;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_qna_board")
public class QnaBoard extends BaseEntity {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "qna_board_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "QB")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name="qna_board_no")
    private String qnaBoardNo;

    @Column(length = 20, nullable = false)
    private String qna_board_category;

    @Column(length = 50, nullable = false)
    private String qna_board_title;

    @Column(length = 500, nullable = false)
    private String qna_board_content;

    @Column(length = 500, nullable = true)
    private String qna_board_answer;

    @Column(length = 10, nullable = false, name = "user_mem_no")
    private String userMemNo;

    public void changeTitle(String title) {
        this.qna_board_title = title;
    }
    public void changeContent(String content) {
        this.qna_board_content = content;
    }
    public void changeCategory(String category) {
        this.qna_board_category = category;
    }
}
