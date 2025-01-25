package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSearchHistory is a Querydsl query type for SearchHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchHistory extends EntityPathBase<SearchHistory> {

    private static final long serialVersionUID = 1479012955L;

    public static final QSearchHistory searchHistory = new QSearchHistory("searchHistory");

    public final StringPath manuInfoNo = createString("manuInfoNo");

    public final StringPath productNo = createString("productNo");

    public final DateTimePath<java.time.LocalDateTime> search_history_date = createDateTime("search_history_date", java.time.LocalDateTime.class);

    public final StringPath search_history_keyword = createString("search_history_keyword");

    public final StringPath searchHistoryNo = createString("searchHistoryNo");

    public final StringPath userMemNo = createString("userMemNo");

    public QSearchHistory(String variable) {
        super(SearchHistory.class, forVariable(variable));
    }

    public QSearchHistory(Path<? extends SearchHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSearchHistory(PathMetadata metadata) {
        super(SearchHistory.class, metadata);
    }

}

