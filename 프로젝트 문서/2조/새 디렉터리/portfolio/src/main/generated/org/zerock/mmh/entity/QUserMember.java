package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserMember is a Querydsl query type for UserMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserMember extends EntityPathBase<UserMember> {

    private static final long serialVersionUID = 1874294806L;

    public static final QUserMember userMember = new QUserMember("userMember");

    public final StringPath user_mem_address = createString("user_mem_address");

    public final NumberPath<Integer> user_mem_age = createNumber("user_mem_age", Integer.class);

    public final ComparablePath<Character> user_mem_gender = createComparable("user_mem_gender", Character.class);

    public final StringPath user_mem_id = createString("user_mem_id");

    public final StringPath user_mem_mail = createString("user_mem_mail");

    public final StringPath user_mem_name = createString("user_mem_name");

    public final StringPath user_mem_phone = createString("user_mem_phone");

    public final StringPath user_mem_pw = createString("user_mem_pw");

    public final StringPath userMemNo = createString("userMemNo");

    public QUserMember(String variable) {
        super(UserMember.class, forVariable(variable));
    }

    public QUserMember(Path<? extends UserMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserMember(PathMetadata metadata) {
        super(UserMember.class, metadata);
    }

}

