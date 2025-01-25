package org.zerock.mmh.service;

import org.zerock.mmh.dto.PageRequestDTO;
import org.zerock.mmh.dto.PageResultDTO;
import org.zerock.mmh.dto.QnaBoardDTO;
import org.zerock.mmh.entity.QnaBoard;

public interface QnaBoardService {
    String register(QnaBoardDTO dto);

    PageResultDTO<QnaBoardDTO, QnaBoard> getList(PageRequestDTO requestDTO);

    QnaBoardDTO read(String qnaBoardNo);

    void remove(String qnaBoardNo);

    void modify(QnaBoardDTO qnaBoardNo);

    void reply(QnaBoardDTO qnaBoardNo);

    default QnaBoard dtoToEntity(QnaBoardDTO dto) {
        QnaBoard entity = QnaBoard.builder()
                .qnaBoardNo(dto.getQnaBoardNo())
                .qna_board_category(dto.getQna_board_category())
                .qna_board_title(dto.getQna_board_title())
                .qna_board_content(dto.getQna_board_content())
                .qna_board_answer(dto.getQna_board_answer())
                .userMemNo(dto.getUserMemNo())
                .build();
        return entity;
    }

    default QnaBoardDTO entityToDto(QnaBoard entity) {
        QnaBoardDTO dto = QnaBoardDTO.builder()
                .qnaBoardNo(entity.getQnaBoardNo())
                .qna_board_category(entity.getQna_board_category())
                .qna_board_title(entity.getQna_board_title())
                .qna_board_content(entity.getQna_board_content())
                .qna_board_answer(entity.getQna_board_answer())
                .userMemNo(entity.getUserMemNo())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
