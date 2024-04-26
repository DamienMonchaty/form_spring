package fr.formation.app.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.formation.app.dto.PersonDto;
import fr.formation.app.service.ICarService;
import fr.formation.app.service.IPersonService;

@WebMvcTest(PersonRestController.class)
@WithMockUser(username="admin",roles={"Admin"})
// @AutoConfigureMockMvc(addFilters = false)
public class PersonRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IPersonService personService;

	@MockBean
	private ICarService carService;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		objectMapper = new ObjectMapper();
	}

	@Test
    void testGetPersons() throws Exception { 
    	
    	PersonDto mockPerson1 = new PersonDto(1, "admin", "admin", 10);
    	PersonDto mockPerson2 = new PersonDto(2, "admin", "admin", 10);
		List<PersonDto> personDtoList = new ArrayList<>();
		personDtoList.add(mockPerson1);
		personDtoList.add(mockPerson2); 
		
    	when(personService.getAll()).thenReturn(personDtoList);

		String URI = "/api/v1/persons";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(URI)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = objectMapper.writeValueAsString(personDtoList);
		
		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @Test
    void testAddPerson() throws Exception { 
    	
    	PersonDto mockPerson = new PersonDto(1, "admin", "admin", 10);
    	
    	when(personService.saveOrUpdate(any(PersonDto.class))).thenReturn(mockPerson);

		String URI = "/api/v1/persons";
		
		String inputJson = objectMapper.writeValueAsString(mockPerson);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.with(csrf()) 
				.accept(MediaType.APPLICATION_JSON)
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(inputJson);
		
		verify(personService, times(1)).saveOrUpdate(any(PersonDto.class));
		
		JsonNode responseJson = objectMapper.readTree(outputInJson);
		
		assertThat(responseJson.has("firstName")).isTrue();
		assertThat(responseJson.get("firstName").asText()).isEqualTo(mockPerson.getFirstName());
    }
    
    @Test
	public void testGetPerson() throws Exception {

    	PersonDto mockPerson = new PersonDto(1, "admin", "admin", 10);

		when(personService.getById(mockPerson.getId())).thenReturn(Optional.of(mockPerson));
		
		String URI = "/api/v1/persons/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = objectMapper.writeValueAsString(mockPerson);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
    
    @Test
	public void testEditPerson() throws Exception {

    	PersonDto mockPerson = new PersonDto(1, "admin", "admin", 10);

		when(personService.getById(mockPerson.getId())).thenReturn(Optional.of(mockPerson));

		PersonDto personFromDB = personService.getById(mockPerson.getId()).get();

		personFromDB.setFirstName("admino");

		String inputInJson = objectMapper.writeValueAsString(personFromDB);

		String URI = "/api/v1/persons/1";

		when(personService.saveOrUpdate(any(PersonDto.class))).thenReturn(mockPerson);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(URI)
				.with(csrf()) 
				.accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);
	}
    
    @Test
	public void testDeletePerson() throws Exception {
    	PersonDto mockPerson = new PersonDto(1, "admin", "admin", 10);
		when(personService.getById(mockPerson.getId())).thenReturn(Optional.of(mockPerson));

		when(personService.deleteById(mockPerson.getId())).thenReturn(true);

		String URI = "/api/v1/persons/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(URI)
				.with(csrf()) 
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andReturn();

		verify(personService, times(1)).getById(mockPerson.getId());
		verify(personService, times(1)).deleteById(mockPerson.getId());
	}
}
