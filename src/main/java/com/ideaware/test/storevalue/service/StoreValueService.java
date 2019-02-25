package com.ideaware.test.storevalue.service;

import com.ideaware.test.storevalue.config.StoreValueProperties;
import com.ideaware.test.storevalue.entity.StoreValue;
import com.ideaware.test.storevalue.repository.StoreValueRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreValueService {

  private final StoreValueRepository storeValueRepositoryRepository;
  private final StoreValueProperties storeValueProperties;

  public List<String> getCurrentValues() {
    return storeValueRepositoryRepository.findAllByOrderByOrderAsc()
        .stream()
        .map(StoreValue::getValue)
        .collect(Collectors.toList());
  }

  @Transactional
  public List<String> storeNewValues(List<String> newValues) {
    if(newValues != null) {
      storeValueRepositoryRepository.deleteAllInBatch();
      if (!newValues.isEmpty()) {
        int maxItems = Math.min(storeValueProperties.getMaxAllowValues() , newValues.size());
        for (int i = 0; i < maxItems; i++) {
          storeValueRepositoryRepository.save(StoreValue
              .builder()
              .order(i)
              .value(newValues.get(newValues.size() - 1 - i))
              .build());
        }
      }
    }
    return getCurrentValues();
  }

}
