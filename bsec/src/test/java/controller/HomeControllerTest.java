package controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import config.ConfigCtxRoot;
import config.ConfigCtxSecurity;
import config.ConfigCtxWeb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration(value = "src/main/webapp")  
@ContextHierarchy(value={
		@ContextConfiguration(name="root", classes={ConfigCtxRoot.class, ConfigCtxSecurity.class}),
		@ContextConfiguration(name="web", classes={ConfigCtxWeb.class})
})  

public class HomeControllerTest {
	@Test
	public void homeTest() throws Exception{
		//No need WebAppConfiguration for this testcase
		HomeController controller = new HomeController();
		MockMvc mockMvc = standaloneSetup(controller).build(); 
		mockMvc.perform(get("/")).andExpect(view().name("home"));
	}
	
	@Autowired
	private WebApplicationContext wac;
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
    	mockMvc = webAppContextSetup(wac).build();
    }
    
	@Test
	public void homeTest2() throws Exception{
		mockMvc.perform(get("/")).andExpect(view().name("home"));
	}
}
