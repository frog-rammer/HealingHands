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
        System.out.println("method: " + method);
        System.out.println("sequenceName: " + sequenceName);
        System.out.println("prefix: " + prefix);
    }

    // ID 생성 처리 메소드 override
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        String sql = null;
        String newId = null;

        // SEQUENCE 방식을 사용하는 경우
        switch (method) {
            case "SEQUENCE":
                // MariaDB에서 시퀀스를 사용하려면 `SELECT`문을 통해 값을 가져오는 방식을 사용
                sql = "SELECT " + sequenceName + ".nextval";
                System.out.println("sql: " + sql);
                break;
            case "AUTO_INCREMENT":
                // 기본적으로 AUTO_INCREMENT 값 사용시에는 이 SQL을 사용
                sql = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = ?";
                break;
            default:
                break;
        }

        // JDBC Connection
        Connection con = null;
        try {
            con = session.getJdbcConnectionAccess().obtainConnection();  // 공유세션으로부터 jdbc connection을 얻는다
            PreparedStatement stmt = con.prepareStatement(sql);

            // 시퀀스 쿼리인 경우
            if ("SEQUENCE".equals(method)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    newId = prefix + String.format("%05d", rs.getLong(1)); // 시퀀스 값 추출 후 포맷팅
                }
            }
            // AUTO_INCREMENT 방식인 경우
            else if ("AUTO_INCREMENT".equals(method)) {
                stmt.setString(1, sequenceName);  // 테이블 이름을 매개변수로 설정
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    newId = prefix + String.format("%05d", rs.getLong("AUTO_INCREMENT"));  // AUTO_INCREMENT 값 추출 후 포맷팅
                }
            }

            if (newId == null) {
                newId = prefix + " Error"; // 오류 발생 시 처리
            }
        } catch (SQLException sqlException) {
            throw new HibernateException(sqlException);
        } finally {
            return newId;
        }
    }
}
