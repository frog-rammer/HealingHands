package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QManufacturerInfo is a Querydsl query type for ManufacturerInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManufacturerInfo extends EntityPathBase<ManufacturerInfo> {

    private static final long serialVersionUID = -538139344L;

    public static final QManufacturerInfo manufacturerInfo = new QManufacturerInfo("manufacturerInfo");

    public final StringPath manu_info_name = createString("manu_info_name");

    public final StringPath manu_info_site = createString("manu_info_site");

    public final StringPath manuInfoNo = createString("manuInfoNo");

    public final StringPath manuMemNo = createString("manuMemNo");

    public QManufacturerInfo(String variable) {
        super(ManufacturerInfo.class, forVariable(variable));
    }

    public QManufacturerInfo(Path<? extends ManufacturerInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManufacturerInfo(PathMetadata metadata) {
        super(ManufacturerInfo.class, metadata);
    }

}

