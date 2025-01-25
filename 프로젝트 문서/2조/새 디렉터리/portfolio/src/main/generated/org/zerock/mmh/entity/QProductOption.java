package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductOption is a Querydsl query type for ProductOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOption extends EntityPathBase<ProductOption> {

    private static final long serialVersionUID = 160799443L;

    public static final QProductOption productOption = new QProductOption("productOption");

    public final StringPath manuInfoNo = createString("manuInfoNo");

    public final StringPath optionNo = createString("optionNo");

    public final StringPath productNo = createString("productNo");

    public final StringPath proOptionNo = createString("proOptionNo");

    public final NumberPath<Integer> proOptionPrice = createNumber("proOptionPrice", Integer.class);

    public QProductOption(String variable) {
        super(ProductOption.class, forVariable(variable));
    }

    public QProductOption(Path<? extends ProductOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductOption(PathMetadata metadata) {
        super(ProductOption.class, metadata);
    }

}

