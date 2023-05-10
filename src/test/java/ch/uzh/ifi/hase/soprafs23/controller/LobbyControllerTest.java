package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LobbyController.class)
class LobbyControllerTest {
    @Autowired
    private MockMvc mockMvc;
/*
    @MockBean
    private UserService userService;*/

    @MockBean
    private  LobbyService lobbyService;


    @Test
    void createLobby_successful() throws Exception {
        String code = "abcdef";
        given(lobbyService.createLobby()).willReturn(code);

        MockHttpServletRequestBuilder postRequest = post("/lobbys/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("id", "1");

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Test
    void joinLobby_successful() throws Exception {
        given(lobbyService.joinLobby(Mockito.any(), Mockito.any())).willReturn(true);

        MockHttpServletRequestBuilder postRequest = post("/lobbys/join/1/1")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(postRequest)
                .andExpect(status().isOk());
    }
}
