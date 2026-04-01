package br.com.gpmendes7.integrationtests.controllers.withyaml;

import br.com.gpmendes7.config.TestConfigs;
import br.com.gpmendes7.integrationtests.controllers.withyaml.mapper.YAMLMapper;
import br.com.gpmendes7.integrationtests.dto.AccountCredentialsDTO;
import br.com.gpmendes7.integrationtests.dto.TokenDTO;
import br.com.gpmendes7.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerYamlTest extends AbstractIntegrationTest {

    private static TokenDTO tokenDto;
    private static YAMLMapper yamlMapper;

    @BeforeAll
    static void setUp() {
        yamlMapper = new YAMLMapper();

        tokenDto = new TokenDTO();
    }

    @Test
    @Order(1)
    void signIn() {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("leandro", "admin123");

        tokenDto = given() .config(
                RestAssuredConfig.config()
                        .encoderConfig(
                                EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )
                .basePath("/auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                    .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(credentials, yamlMapper)
                    .when()
                .post()
                    .then()
                    .statusCode(200)
                        .extract()
                        .body()
                        .as(TokenDTO.class, yamlMapper);

        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
    }

    @Test
    @Order(2)
    void refreshToken() {
        tokenDto = given().config(
                RestAssuredConfig.config()
                        .encoderConfig(
                                EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                  .pathParam("username", tokenDto.getUsername())
                  .header(TestConfigs.HEADER_PARAM_AUTHOTIZATION, "Bearer " + tokenDto.getRefreshToken())
                .when()
                  .put("{username}")
                   .then()
                   .statusCode(200)
                     .extract()
                     .body()
                     .as(TokenDTO.class, yamlMapper);

        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
    }
}