package controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import config.ConfigCtxRoot;
import config.ConfigCtxSecurity;
import config.ConfigCtxWeb;

@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration(value = "src/main/webapp")  
@ContextHierarchy(value={
		@ContextConfiguration(name="root", classes={ConfigCtxRoot.class, ConfigCtxSecurity.class}),
		@ContextConfiguration(name="web", classes={ConfigCtxWeb.class})
}) 
public class LoginTest {
	
	
	@Autowired
	private WebApplicationContext wac;
    private MockMvc mockMvc;
    
    @Autowired
    private Filter springSecurityFilterChain;
    
    @Before
    public void setup() {
    	mockMvc = webAppContextSetup(wac)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void loginWithValidUserThenAuthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
            .user("andrew")
            .password("aaa");

        mockMvc.perform(login)
            .andExpect(authenticated().withUsername("andrew"));
    }

    @Test
    public void loginWithInvalidUserThenUnauthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
            .user("invalid")
            .password("invalidpassword");

        mockMvc.perform(login)
            .andExpect(unauthenticated());
    }
    
//    @Test
//    public void accessUnsecuredResourceThenOk() throws Exception {
//        mockMvc.perform(get("/")).andExpect(status().isOk());
//    }
//
//    @Test
//    public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
//        mockMvc.perform(get("/hello"))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrlPattern("**/login"));
//    }
//
//    @Test
//    @WithMockUser
//    public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
//        mockMvc.perform(get("/hello"))
//                .andExpect(status().isOk());
//    }
}
