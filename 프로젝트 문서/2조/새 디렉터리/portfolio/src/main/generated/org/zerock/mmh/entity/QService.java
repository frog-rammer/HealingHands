package org.zerock.mmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QService is a Querydsl query type for Service
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QService extends EntityPathBase<Service> {

    private static final long serialVersionUID = 1028466308L;

    public static final QService service = new QService("service");

    public final StringPath service_desc = createString("service_desc");

    public final StringPath service_name = createString("service_name");

    public final StringPath service_thumbnail = createString("service_thumbnail");

    public final StringPath serviceNo = createString("serviceNo");

    public QService(String variable) {
        super(Service.class, forVariable(variable));
    }

    public QService(Path<? extends Service> path) {
        super(path.getType(), path.getMetadata());
    }

    public QService(PathMetadata metadata) {
        super(Service.class, metadata);
    }

}

