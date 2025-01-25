package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QManufacturerService is a Querydsl query type for ManufacturerService
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManufacturerService extends EntityPathBase<ManufacturerService> {

    private static final long serialVersionUID = 1442553587L;

    public static final QManufacturerService manufacturerService = new QManufacturerService("manufacturerService");

    public final StringPath manuInfoNo = createString("manuInfoNo");

    public final StringPath manuServiceNo = createString("manuServiceNo");

    public final StringPath serviceNo = createString("serviceNo");

    public QManufacturerService(String variable) {
        super(ManufacturerService.class, forVariable(variable));
    }

    public QManufacturerService(Path<? extends ManufacturerService> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManufacturerService(PathMetadata metadata) {
        super(ManufacturerService.class, metadata);
    }

}

