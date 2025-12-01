package com.techmath.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techmath.allcrud.controller.CrudControllerIntegrationTests;
import com.techmath.allcrud.converter.Converter;
import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.example.converter.BookConverter;
import com.techmath.example.entity.Author;
import com.techmath.example.entity.Book;
import com.techmath.example.entity.Category;
import com.techmath.example.repository.AuthorRepository;
import com.techmath.example.repository.BookRepository;
import com.techmath.example.repository.CategoryRepository;
import com.techmath.example.vo.BookVO;
import io.restassured.http.ContentType;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTests extends CrudControllerIntegrationTests<Book, BookVO, UUID> {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookConverter converter;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public BookControllerIntegrationTests() {
        super(BookVO.class, UUID.class, BookController.class);
    }

    @Override
    protected EntityRepository<Book, UUID> getRepository() {
        return repository;
    }

    @Override
    protected Converter<Book, BookVO, UUID> getConverter() {
        return converter;
    }

    @Test
    @Override
    public void givenValidVO_whenCreate_thenReturnStatus201AndPersistedEntity() throws JsonProcessingException {
        Author author = authorRepository.save(Instancio.of(Author.class).withSettings(settings)
                .ignore(Select.field(Author::getId))
                .ignore(Select.field(Author::getCreatedDate))
                .ignore(Select.field(Author::getCreatedBy))
                .ignore(Select.field(Author::getLastUpdatedDate))
                .ignore(Select.field(Author::getLastUpdatedBy))
                .set(Select.field(Author::getActive), true)
                .create());

        Category category = categoryRepository.save(Instancio.create(Category.class));

        BookVO voToCreate = Instancio.of(BookVO.class).withSettings(settings)
                .set(Select.field(BookVO::getAuthorId), author.getId())
                .set(Select.field(BookVO::getCategoryId), category.getId())
                .create();

        voToCreate.setId(null);

        String response = given().contentType(ContentType.JSON).body(voToCreate)
                .when().post(basePath)
                .then().log().ifValidationFails()
                .assertThat().status(HttpStatus.CREATED)
                .body(notNullValue(), not(emptyString()))
                .body("id", notNullValue())
                .extract().asString();

        BookVO createdVO = mapper.readValue(response, BookVO.class);
        UUID id = createdVO.getId();

        assertNotNull(id);

        Book persistedEntity = getRepository().findById(createdVO.getId()).orElse(null);

        assertNotNull(persistedEntity, "Entity was not persisted in database");
        assertEquals(id, persistedEntity.getId(), "Persisted entity ID should match");
    }

    @Override
    public void givenExistingEntity_whenUpdate_thenReturnStatus200AndUpdatedEntity() throws JsonProcessingException {
        BookVO voCreated = createEntity();
        UUID id = voCreated.getId();

        BookVO voToUpdate = Instancio.of(BookVO.class)
                .withSettings(settings)
                .set(Select.field(BookVO::getId), id)
                .ignore(Select.field(BookVO::getAuthorId))
                .ignore(Select.field(BookVO::getCategoryId))
                .create();

        String response = given().contentType(ContentType.JSON).body(voToUpdate)
                .when().put(basePath + "/" + id)
                .then().log().ifValidationFails()
                .assertThat()
                    .status(HttpStatus.OK)
                    .body(notNullValue(), not(emptyString()))
                    .body("id", equalTo(id.toString()))
                .extract().asString();

        BookVO voUpdated = mapper.readValue(response, BookVO.class);
        Book persistedEntity = repository.findById(id).orElse(null);
        assertNotNull(persistedEntity, "Updated entity should exist in database");
        assertEquals(id, persistedEntity.getId(), "Entity ID should not change");
        assertNotEquals(voCreated.toString(), voUpdated.toString(), "Entity should be updated");
    }

    @Test
    @Override
    public void givenNonExistentId_whenUpdate_thenReturnStatus404() {
        Author author = authorRepository.save(Instancio.of(Author.class).withSettings(settings)
                .ignore(Select.field(Author::getId))
                .ignore(Select.field(Author::getCreatedDate))
                .ignore(Select.field(Author::getCreatedBy))
                .ignore(Select.field(Author::getLastUpdatedDate))
                .ignore(Select.field(Author::getLastUpdatedBy))
                .set(Select.field(Author::getActive), true)
                .create());

        UUID nonExistentId = Instancio.create(UUID.class);
        BookVO voToPartialUpdate = Instancio.of(BookVO.class).withSettings(settings).create();
        voToPartialUpdate.setAuthorId(author.getId());

        given().contentType(ContentType.JSON).body(voToPartialUpdate)
                .when().patch(basePath + "/" + nonExistentId)
                .then().log().ifValidationFails()
                .assertThat()
                    .status(HttpStatus.NOT_FOUND)
                    .body(not(emptyString()));
    }

    @Test
    public void givenInactiveAuthor_whenCreateBook_thenReturnStatus422() {
        Author inactiveAuthor = authorRepository.save(Instancio.of(Author.class).withSettings(settings)
                .ignore(Select.field(Author::getId))
                .ignore(Select.field(Author::getCreatedDate))
                .ignore(Select.field(Author::getCreatedBy))
                .ignore(Select.field(Author::getLastUpdatedDate))
                .ignore(Select.field(Author::getLastUpdatedBy))
                .set(Select.field(Author::getActive), false)
                .create());

        Category category = new Category("TEST_CAT", "Test Category", "Desc");
        category = categoryRepository.save(category);

        BookVO bookVO = createValidBookVO(inactiveAuthor.getId(), category.getId());

        given().contentType(ContentType.JSON).body(bookVO)
            .when().post(basePath)
            .then().log().ifValidationFails()
            .assertThat()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body("[0].description", containsStringIgnoringCase("inactive author"));
    }

    @Override
    protected BookVO createEntity() throws JsonProcessingException {
        Author author = authorRepository.save(Instancio.of(Author.class).withSettings(settings)
                .ignore(Select.field(Author::getId))
                .ignore(Select.field(Author::getCreatedDate))
                .ignore(Select.field(Author::getCreatedBy))
                .ignore(Select.field(Author::getLastUpdatedDate))
                .ignore(Select.field(Author::getLastUpdatedBy))
                .set(Select.field(Author::getActive), true)
                .create());

        Category category = categoryRepository.save(Instancio.create(Category.class));

        BookVO voToCreate = createValidBookVO(author.getId(), category.getId());

        String createResponse = given().contentType(ContentType.JSON).body(voToCreate)
                .when().post(basePath)
                .then().log().ifValidationFails()
                .assertThat().status(HttpStatus.CREATED)
                .extract().asString();

        BookVO voCreated = mapper.readValue(createResponse, BookVO.class);
        voCreated.setAuthorId(author.getId());
        voCreated.setCategoryId(category.getId());
        return voCreated;
    }

    private BookVO createValidBookVO(Long authorId, String categoryId) {
        return Instancio.of(BookVO.class)
                .withSettings(settings)
                .set(Select.field(BookVO::getId), null)
                .set(Select.field(BookVO::getAuthorId), authorId)
                .set(Select.field(BookVO::getCategoryId), categoryId)
                .generate(Select.field(BookVO::getPublicationDate), gen -> gen.temporal().localDate().past())
                .generate(Select.field(BookVO::getIsbn), gen -> gen.text().pattern("#d#d#d-#d-#d#d#d-#d#d#d#d#d-#d"))
                .create();
    }

}
