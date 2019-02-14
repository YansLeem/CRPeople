package com.example.Lab.People;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    private MockMvc mockMvc;

    ObjectMapper om = new ObjectMapper();

    @Before
    public void setup() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void testPeopleOne() throws Exception {
        mockMvc.perform(get("/people/777")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Lol")));
    }


    @Test
    public void testPeopleTwo() throws Exception {
        mockMvc.perform(get("/people/111")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Kek")));
    }



    @Test
    public void testPeopleThree() throws Exception {
        Person newperson = new Person(111L,"Name", "LastName");
        String jsonRequest = om.writeValueAsString(newperson);
        //System.out.println("HERE >>>"  + " <<< HERE");
        MvcResult result = mockMvc.perform(put("/people/111").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        String resultContent = result.getResponse().getContentAsString();
        Person person = om.readValue(resultContent,Person.class);
        //System.out.println("HERE >>>" + person.getName() + " <<< HERE");

    }
}