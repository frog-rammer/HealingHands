package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1265161026L;

    public static final QProduct product = new QProduct("product");

    public final StringPath product_desc = createString("product_desc");

    public final StringPath product_name = createString("product_name");

    public final StringPath product_period = createString("product_period");

    public final StringPath product_size = createString("product_size");

    public final StringPath product_thumbnail = createString("product_thumbnail");

    public final StringPath productNo = createString("productNo");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

