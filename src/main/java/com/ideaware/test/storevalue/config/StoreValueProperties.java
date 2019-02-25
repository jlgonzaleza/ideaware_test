package com.ideaware.test.storevalue.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("inst.storevalue")
@Data
public class StoreValueProperties {

  private int maxAllowValues;
}
