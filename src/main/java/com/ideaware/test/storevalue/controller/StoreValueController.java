package com.ideaware.test.storevalue.controller;

import com.ideaware.test.storevalue.service.StoreValueService;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("store_valueV1")
@RequestMapping("/store_value")
@Slf4j
@RequiredArgsConstructor
public class StoreValueController {

  private final StoreValueService storeValueService;

  @ApiOperation(value = "update new stored values")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Successful add new values"),
      @ApiResponse(code = 500, message = "Internal server error")})
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public List<String> insertNewValues(@ApiParam(name = "body", value = "array of new values", required = true) @RequestBody List<String> newValues) {
      return storeValueService.storeNewValues(newValues);
  }

  @ApiOperation(value = "get stored values")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful return values"),
      @ApiResponse(code = 500, message = "Internal server error")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<String> getCurrentValues() {
    return storeValueService.getCurrentValues();
  }
}
