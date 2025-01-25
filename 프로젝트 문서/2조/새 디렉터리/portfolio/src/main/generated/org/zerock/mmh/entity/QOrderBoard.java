package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderBoard is a Querydsl query type for OrderBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderBoard extends EntityPathBase<OrderBoard> {

    private static final long serialVersionUID = 1197421065L;

    public static final QOrderBoard orderBoard = new QOrderBoard("orderBoard");

    public final StringPath manuInfoNo = createString("manuInfoNo");

    public final StringPath order_board_answer = createString("order_board_answer");

    public final StringPath order_board_ddate = createString("order_board_ddate");

    public final StringPath order_board_desc = createString("order_board_desc");

    public final NumberPath<Integer> order_board_quantity = createNumber("order_board_quantity", Integer.class);

    public final StringPath orderBoardNo = createString("orderBoardNo");

    public final StringPath productNo = createString("productNo");

    public final StringPath userMemNo = createString("userMemNo");

    public QOrderBoard(String variable) {
        super(OrderBoard.class, forVariable(variable));
    }

    public QOrderBoard(Path<? extends OrderBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderBoard(PathMetadata metadata) {
        super(OrderBoard.class, metadata);
    }

}

