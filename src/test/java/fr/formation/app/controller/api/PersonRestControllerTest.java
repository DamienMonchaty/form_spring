package fr.formation.app.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.formation.app.dto.PersonDto;
import fr.formation.app.model.Person;
import fr.formation.app.service.ICarService;
import fr.formation.app.service.IPersonService;

@WebMvcTest(PersonRestController.class)
public class PersonRestControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPersonService personService;
    
    @MockBean
    private ICarService carService;
    
    private ObjectMapper objectMapper;
    
	@BeforeEach
	void setup(){
		objectMapper = new ObjectMapper();
	}

    @Test
    public void testGetPersons() throws Exception {
    	PersonDto mockPerson1 = new PersonDto(1, "admin", "admin", 10);
    	PersonDto mockPerson2 = new PersonDto(2, "admin", "admin", 10);
		List<PersonDto> personDtoList = new ArrayList<>();
		personDtoList.add(mockPerson1);
		personDtoList.add(mockPerson2); 
		
    	when(personService.getAll()).thenReturn(personDtoList);

		String URI = "/api/v1/persons";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = objectMapper.writeValueAsString(personDtoList);
		String outputInJson = result.getResponse().getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);
    }
    
}
