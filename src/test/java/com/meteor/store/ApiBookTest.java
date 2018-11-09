package com.meteor.store;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**/
import static java.util.Collections.singletonList;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiBookTest {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation))
				.alwaysDo(document("{method-name}", 
					    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.build();
	}

	@Test
	public void book() throws Exception {
		/*
		MvcResult mvcResult = this.mockMvc.perform(get("/")).andReturn();
		
		System.out.println(
				"mvcResult.getResponse().getContentAsString() : " + mvcResult.getResponse().getContentAsString());
		*/
		
		this.mockMvc.perform(get("/")).andExpect(status().isOk())
		.andDo(document("index", links(linkWithRel("crud").description(
		"The CRUD resource")), responseFields(subsectionWithPath("_links")
		.description("Links to other resources")),
		responseHeaders(headerWithName("Content-Type") .description(
		"The Content-Type of the payload"))));
		
	}
	
	@Test
	public void store() throws Exception {
		MvcResult mvcResult1 = this.mockMvc.perform(get("/store")).andReturn();

		
		System.out.println(
				"mvcResult.getResponse().getContentAsString() : " + mvcResult1.getResponse().getContentAsString());
		
		/*
		 * this.mockMvc.perform(get("/")).andExpect(status().isOk())
		 * .andDo(document("index", links(linkWithRel("crud").description(
		 * "The CRUD resource")), responseFields(subsectionWithPath("_links")
		 * .description("Links to other resources")),
		 * responseHeaders(headerWithName("Content-Type") .description(
		 * "The Content-Type of the payload"))));
		 */
	}
	
	@Test
	public void crudDeleteExample() throws Exception {
	    this.mockMvc.perform(put("/store/{id}", 10).contentType(MediaType.APPLICATION_JSON).content("{\"value\":\"testVal\"}")
	    		) .andExpect(status().isCreated())
	      .andDo(document("crud-delete-example", 
	      pathParameters(
	        parameterWithName("id").description("The id of the input to delete")
	      )));
	}

}
