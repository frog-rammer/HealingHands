package org.zerock.mmh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QnaBoardDTO {
    private String qnaBoardNo;
    private String qna_board_category;
    private String qna_board_title;
    private String qna_board_content;
    private String qna_board_answer;
    private String userMemNo;
    private LocalDateTime regDate, modDate;
}
