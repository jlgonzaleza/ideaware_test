package com.ideaware.test.storevalue.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideaware.test.storevalue.repository.StoreValueRepository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class StoreControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private StoreValueRepository storeValueRepository;

  @BeforeEach
  public void initResults() {
    storeValueRepository.deleteAllInBatch();
  }

  @Test
  public void getCurrentEmptyValuesTest() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/store_value"))
        .andReturn();
    assertEquals(200, mvcResult.getResponse().getStatus());
    List<String> result = mapResult(mvcResult);
    assertTrue(result.isEmpty());
  }

  @Test
  public void insertOneValueTest() throws Exception {
    List<String> value = Arrays.asList("e1");
    MvcResult mvcResult = mockMvc.perform(post("/store_value")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(value)))
        .andReturn();
    assertEquals(201, mvcResult.getResponse().getStatus());
    List<String> result = mapResult(mvcResult);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals("e1", result.get(0));
  }

  @Test
  public void insertFiveValuesTest() throws Exception {
    List<String> value = Arrays.asList("e1","e2","e3","e4","e5");
    MvcResult mvcResult = mockMvc.perform(post("/store_value")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(value)))
        .andReturn();
    assertEquals(201, mvcResult.getResponse().getStatus());
    List<String> result = mapResult(mvcResult);
    assertFalse(result.isEmpty());
    assertEquals(5, result.size());
    assertEquals("e5", result.get(0));
    assertEquals("e4", result.get(1));
    assertEquals("e3", result.get(2));
    assertEquals("e2", result.get(3));
    assertEquals("e1", result.get(4));
  }

  @Test
  public void insertMoreThanFiveValuesTest() throws Exception {
    List<String> value = Arrays.asList("e1","e2","e3","e4","e5","e6");
    MvcResult mvcResult = mockMvc.perform(post("/store_value")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(value)))
        .andReturn();
    assertEquals(201, mvcResult.getResponse().getStatus());
    List<String> result = mapResult(mvcResult);
    assertFalse(result.isEmpty());
    assertEquals(5, result.size());
    assertEquals("e6", result.get(0));
    assertEquals("e5", result.get(1));
    assertEquals("e4", result.get(2));
    assertEquals("e3", result.get(3));
    assertEquals("e2", result.get(4));
  }

  private List<String> mapResult (MvcResult mvcResult) throws Exception {
    return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<String>>() {});
  }

}
