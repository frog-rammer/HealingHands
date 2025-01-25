package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QManufacturerMember is a Querydsl query type for ManufacturerMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManufacturerMember extends EntityPathBase<ManufacturerMember> {

    private static final long serialVersionUID = -1649429860L;

    public static final QManufacturerMember manufacturerMember = new QManufacturerMember("manufacturerMember");

    public final NumberPath<Integer> manu_mem_approval = createNumber("manu_mem_approval", Integer.class);

    public final StringPath manu_mem_bnumber = createString("manu_mem_bnumber");

    public final StringPath manu_mem_id = createString("manu_mem_id");

    public final StringPath manu_mem_mail = createString("manu_mem_mail");

    public final StringPath manu_mem_name = createString("manu_mem_name");

    public final StringPath manu_mem_pw = createString("manu_mem_pw");

    public final StringPath manuMemNo = createString("manuMemNo");

    public QManufacturerMember(String variable) {
        super(ManufacturerMember.class, forVariable(variable));
    }

    public QManufacturerMember(Path<? extends ManufacturerMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManufacturerMember(PathMetadata metadata) {
        super(ManufacturerMember.class, metadata);
    }

}

