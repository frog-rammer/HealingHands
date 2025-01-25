package org.zerock.mmh.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mmh.dto.PageRequestDTO;
import org.zerock.mmh.dto.PageResultDTO;
import org.zerock.mmh.dto.QnaBoardDTO;
import org.zerock.mmh.entity.QnaBoard;

@SpringBootTest
public class QnaBoardServiceTests {
    @Autowired
    private QnaBoardService service;

    @Test
    public void testRegister() {
        QnaBoardDTO qnaBoardDTO = QnaBoardDTO.builder()
                .qna_board_category("Sample Category....")
                .qna_board_title("Sample Title.....")
                .qna_board_content("Sample Content...")
                .qna_board_answer("Sample Answer...")
                .userMemNo("USER0")
                .build();
        System.out.println(service.register(qnaBoardDTO));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<QnaBoardDTO,QnaBoard> resultDTO = service.getList(pageRequestDTO);

        for(QnaBoardDTO qnaBoardDTO : resultDTO.getDtoList()){
            System.out.println(qnaBoardDTO);
        }
    }
}
