package org.zerock.mmh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.mmh.dto.PageRequestDTO;
import org.zerock.mmh.dto.PageResultDTO;
import org.zerock.mmh.dto.QnaBoardDTO;
import org.zerock.mmh.entity.QnaBoard;
import org.zerock.mmh.repository.QnaBoardRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class QnaBoardServiceImpl implements QnaBoardService {

    private final QnaBoardRepository repository;//반드시 final로 선언

    @Override
    public String register(QnaBoardDTO dto) {
        log.info("DTO......");
        log.info(dto);

        QnaBoard entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);

        return entity.getQnaBoardNo();
    }

    @Override
    public PageResultDTO<QnaBoardDTO, QnaBoard> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("qnaBoardNo").descending());
        Page<QnaBoard> result = repository.findAll(pageable);

        Function<QnaBoard, QnaBoardDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }


    @Override
    public QnaBoardDTO read(String qnaBoardNo) {
        Optional<QnaBoard> result = repository.findById(qnaBoardNo);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(String qnaBoardNo) {
        repository.deleteById(qnaBoardNo);
    }

    @Override
    public void modify(QnaBoardDTO dto) {
        //업데이트 하는 항목은 '카테고리', '제목', '내용'

        Optional<QnaBoard> result = repository.findById(dto.getQnaBoardNo());
        if (result.isPresent()) {
            QnaBoard entity = result.get();

            entity.changeTitle(dto.getQna_board_title());
            entity.changeContent(dto.getQna_board_content());
            entity.changeCategory(dto.getQna_board_category());

            repository.save(entity);
        }
    }

    public void reply(QnaBoardDTO dto) {

        Optional<QnaBoard> result = repository.findById(dto.getQnaBoardNo());
        if (result.isPresent()) {
            QnaBoard entity = result.get();
            entity.changeAnswer(dto.getQna_board_answer());
            repository.save(entity);
        }
    }

//    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {//Querydsl 처리
//        String type = requestDTO.getType();
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//        String keyword = requestDTO.getKeyword();
//        BooleanExpression expression = qGuestbook.gno.gt(0L);//gno>0 조건만 생성
//        booleanBuilder.and(expression);
//
//        if (type == null || type.trim().length() == 0) { //검색 조건이 없는 경우
//            return booleanBuilder;
//        }
//
//        //검색 조건을 작성하기
//        BooleanBuilder conditionBuilder = new BooleanBuilder();
//
//        if (type.contains("t")) {
//            conditionBuilder.or(qGuestbook.title.contains(keyword));
//        }
//        if (type.contains("c")) {
//            conditionBuilder.or(qGuestbook.content.contains(keyword));
//        }
//        if (type.contains("w")) {
//            conditionBuilder.or(qGuestbook.writer.contains(keyword));
//        }
//
//        //모든 조건 통합
//        booleanBuilder.and(conditionBuilder);
//
//        return booleanBuilder;
//
//    }
}
