package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQnaBoard is a Querydsl query type for QnaBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaBoard extends EntityPathBase<QnaBoard> {

    private static final long serialVersionUID = 1490088307L;

    public static final QQnaBoard qnaBoard = new QQnaBoard("qnaBoard");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath qna_board_answer = createString("qna_board_answer");

    public final StringPath qna_board_category = createString("qna_board_category");

    public final StringPath qna_board_content = createString("qna_board_content");

    public final StringPath qna_board_title = createString("qna_board_title");

    public final StringPath qnaBoardNo = createString("qnaBoardNo");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath userMemNo = createString("userMemNo");

    public QQnaBoard(String variable) {
        super(QnaBoard.class, forVariable(variable));
    }

    public QQnaBoard(Path<? extends QnaBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQnaBoard(PathMetadata metadata) {
        super(QnaBoard.class, metadata);
    }

}

