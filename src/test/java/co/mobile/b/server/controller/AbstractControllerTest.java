package co.mobile.b.server.controller;

import co.mobile.b.server.MobileBServerApplication;
import co.mobile.b.server.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@ActiveProfiles
@Import(TestConfig.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = MobileBServerApplication.class)
public abstract class AbstractControllerTest {
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    protected RestDocumentationResultHandler document;

    @BeforeEach
    protected void setUp(WebApplicationContext webAppContext, RestDocumentationContextProvider restDocumentation) throws Exception {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document).build();
    }
}
