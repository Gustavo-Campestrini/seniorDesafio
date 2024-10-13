package com.example.desafio_hotel_senior;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = {//
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=CREATE TYPE \"JSONB\" AS TEXT;MODE=PostgreSQL", //
        "spring.datasource.driverClassName=org.h2.Driver", //
        "spring.datasource.username=sa", //
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect" //
}, showSql = false)
@ContextConfiguration
@EnableAutoConfiguration
@ActiveProfiles("unit-test")
public abstract class BaseRepositoryTest {
}
