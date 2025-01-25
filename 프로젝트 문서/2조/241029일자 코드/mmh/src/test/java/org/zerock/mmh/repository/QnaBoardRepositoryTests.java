package org.zerock.mmh.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.mmh.entity.QQnaBoard;
import org.zerock.mmh.entity.QnaBoard;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class QnaBoardRepositoryTests {
    @Autowired
    private QnaBoardRepository qnaBoardRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            QnaBoard qnaBoard = QnaBoard.builder()
                    .qna_board_category("Category" + i)
                    .qna_board_title("Title..." + i)
                    .qna_board_content("Content..." + i)
                    .userMemNo("User" + i % 10)
                    .build();
            System.out.println(qnaBoardRepository.save(qnaBoard));
        });
    }


    @Test
    public void updateTest() {
        Optional<QnaBoard> result = qnaBoardRepository.findById("QB00001");//존재하는 번호로 테스트

        if (result.isPresent()) {
            QnaBoard qnaBoard = result.get();
            qnaBoard.changeCategory("Category2");
            qnaBoard.changeTitle("Changed Title");
            qnaBoard.changeContent("Changed Content");
            qnaBoardRepository.save(qnaBoard);

        }
    }

    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("modDate").descending());
        QQnaBoard qQnaBoard = QQnaBoard.qnaBoard;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qQnaBoard.qna_board_title.contains(keyword);
        builder.and(expression);
        Page<QnaBoard> result = qnaBoardRepository.findAll(builder, pageable);

        result.stream().forEach(qnaBoard -> {
            System.out.println(qnaBoard);
        });

    }

    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("modDate").descending());
        QQnaBoard qQnaBoard = QQnaBoard.qnaBoard;
        String keyword = "2";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qQnaBoard.qna_board_title.contains(keyword);
        BooleanExpression exCategory = qQnaBoard.qna_board_category.contains(keyword);
        BooleanExpression exContent = qQnaBoard.qna_board_content.contains(keyword);
        BooleanExpression exAll = exCategory.or(exTitle.or(exContent));
        builder.and(exAll);
        Page<QnaBoard> result = qnaBoardRepository.findAll(builder, pageable);

        result.stream().forEach(qnaBoard -> {
            System.out.println(qnaBoard);
        });
    }

}
