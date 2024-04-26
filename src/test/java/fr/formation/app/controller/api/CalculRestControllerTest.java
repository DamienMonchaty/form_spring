package fr.formation.app.controller.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.formation.app.model.Calcul;

@WebMvcTest(CalculRestController.class)
public class CalculRestControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void testCalculate() throws Exception {    
	    Calcul calcul = new Calcul("9", "4");
	        
	    String inputInJson = this.mapToJson(calcul);

	    String URI = "/api/v1/calculate";

	    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
	            .content(inputInJson).contentType(MediaType.APPLICATION_JSON);

	    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	    MockHttpServletResponse response = result.getResponse();

	    String outputInJson = response.getContentAsString();
	    
	    double expected = Math.sqrt(Integer.parseInt(calcul.getA())) / Integer.parseInt(calcul.getB());
	    double res = Double.parseDouble(outputInJson);

	    assertThat(res).isEqualTo(expected);
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
