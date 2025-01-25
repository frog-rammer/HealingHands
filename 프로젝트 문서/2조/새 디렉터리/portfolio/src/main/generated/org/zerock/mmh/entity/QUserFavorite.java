package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserFavorite is a Querydsl query type for UserFavorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserFavorite extends EntityPathBase<UserFavorite> {

    private static final long serialVersionUID = -988723176L;

    public static final QUserFavorite userFavorite = new QUserFavorite("userFavorite");

    public final StringPath manuInfoNo = createString("manuInfoNo");

    public final StringPath productNo = createString("productNo");

    public final StringPath userFavNo = createString("userFavNo");

    public final StringPath userFavoriteId = createString("userFavoriteId");

    public final StringPath userMemNo = createString("userMemNo");

    public QUserFavorite(String variable) {
        super(UserFavorite.class, forVariable(variable));
    }

    public QUserFavorite(Path<? extends UserFavorite> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserFavorite(PathMetadata metadata) {
        super(UserFavorite.class, metadata);
    }

}

