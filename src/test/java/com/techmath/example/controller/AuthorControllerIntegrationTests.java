package com.techmath.example.controller;

import com.techmath.allcrud.controller.CrudControllerIntegrationTests;
import com.techmath.allcrud.converter.Converter;
import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.example.converter.AuthorConverter;
import com.techmath.example.entity.Author;
import com.techmath.example.repository.AuthorRepository;
import com.techmath.example.vo.AuthorVO;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests extends CrudControllerIntegrationTests<Author, AuthorVO, Long> {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private AuthorConverter converter;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    public AuthorControllerIntegrationTests() {
        super(AuthorVO.class, Long.class, AuthorController.class);
    }

    @Override
    protected EntityRepository<Author, Long> getRepository() {
        return repository;
    }

    @Override
    public Converter<Author, AuthorVO, Long> getConverter() {
        return converter;
    }

    @Test
    public void givenExistingAuthor_whenDelete_thenRowIsKeptWithActiveFalseAndGetReturnsStatus404() {
        AuthorVO author = createEntity();

        given()
            .when().delete(basePath + "/" + author.getId())
            .then().log().ifValidationFails()
            .assertThat().status(HttpStatus.NO_CONTENT);

        entityManager.flush();
        entityManager.clear();

        Integer rows = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM author_tb WHERE id = ?", Integer.class, author.getId());
        Boolean active = jdbcTemplate.queryForObject(
                "SELECT active FROM author_tb WHERE id = ?", Boolean.class, author.getId());
        assertEquals(1, rows);
        assertFalse(active);

        given()
            .when().get(basePath + "/" + author.getId())
            .then().log().ifValidationFails()
            .assertThat().status(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenSoftDeletedAuthor_whenFindAll_thenInactiveAuthorIsNotReturned() {
        AuthorVO kept = createEntity();
        AuthorVO deleted = createEntity();

        given()
            .when().delete(basePath + "/" + deleted.getId())
            .then().log().ifValidationFails()
            .assertThat().status(HttpStatus.NO_CONTENT);

        entityManager.flush();
        entityManager.clear();

        given()
            .when().get(basePath)
            .then().log().ifValidationFails()
            .assertThat()
                .body("id", hasItem(kept.getId().intValue()))
                .body("id", not(hasItem(deleted.getId().intValue())));
    }

    @Override
    protected AuthorVO createEntity() {
        AuthorVO voToCreate = Instancio.of(AuthorVO.class).withSettings(settings)
                .set(Select.field(AuthorVO::getActive), true)
                .create();
        voToCreate.setId(null);

        String createResponse = given().contentType(ContentType.JSON).body(voToCreate)
                .when().post(basePath)
                .then()
                    .status(HttpStatus.CREATED)
                    .extract().asString();

        return mapper.readValue(createResponse, AuthorVO.class);
    }

}
