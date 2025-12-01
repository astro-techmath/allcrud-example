package com.techmath.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techmath.allcrud.controller.CrudControllerIntegrationTests;
import com.techmath.allcrud.converter.Converter;
import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.example.converter.AuthorConverter;
import com.techmath.example.entity.Author;
import com.techmath.example.repository.AuthorRepository;
import com.techmath.example.vo.AuthorVO;
import io.restassured.http.ContentType;
import org.instancio.Instancio;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests extends CrudControllerIntegrationTests<Author, AuthorVO, Long> {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private AuthorConverter converter;

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

    @Override
    protected AuthorVO createEntity() throws JsonProcessingException {
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
