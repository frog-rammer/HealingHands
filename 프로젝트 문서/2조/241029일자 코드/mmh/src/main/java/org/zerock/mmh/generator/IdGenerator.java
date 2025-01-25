package org.zerock.mmh.generator;


import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.*;
import java.util.Properties;

public class IdGenerator implements IdentifierGenerator, Configurable {

    // 속성값 처리를 위한 Const
    public static final String METHOD = "method";
    public static final String SEQUENCENAME = "sequenceName";
    public static final String PREFIX = "prefix";


    // 전달받은 속성값
    private String method;
    private String sequenceName;
    private String prefix;

    // 속성값 처리 메소드 override
    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        method = ConfigurationHelper.getString(METHOD, params);
        sequenceName = ConfigurationHelper.getString(SEQUENCENAME, params);
        prefix = ConfigurationHelper.getString(PREFIX, params);
//        System.out.println("method: " + method);
//        System.out.println("sequenceName: " + sequenceName);
//        System.out.println("prefix: " + prefix);

    }

    // ID 생성 처리 메소드 override
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        String sql = null;
        String newId = null;

        switch (method) {
            case "SEQUENCE":
                sql = "SELECT NEXTVAL(" + sequenceName + ") FROM DUAL";
                System.out.println("sql: " + sql);
                break;
            default:
                break;
        }

        // JDBC Connection
        Connection con = null;
        try {
            con = session.getJdbcConnectionAccess().obtainConnection();  // 공유세션으로부터 jdbc connection을 얻는다
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();// SQL를 실행
            if (rs.next()) {
                newId = prefix+ String.format("%05d",rs.getLong(1)); // 결과값 추출
            }
            if (newId == null) {
                newId = prefix + " Error";
            }
        } catch (SQLException sqlException) {
            throw new HibernateException(sqlException);
        } finally {
            return newId;
        }
    }
}


