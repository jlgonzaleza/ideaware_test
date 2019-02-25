package com.ideaware.test.storevalue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ideaware.test.storevalue.config.StoreValueProperties;
import com.ideaware.test.storevalue.entity.StoreValue;
import com.ideaware.test.storevalue.repository.StoreValueRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StoreValueServiceTest {

  private static final int MAX_VALUES = 5;

  @Mock
  private StoreValueRepository storeValueRepositoryRepository;

  @Mock
  private StoreValueProperties storeValueProperties;

  @InjectMocks
  private StoreValueService storeValueService;


  @BeforeEach
  public void initResults() {

    StoreValue storeValue1 = StoreValue.builder()
        .order(0)
        .value("First Value")
        .build();
    StoreValue storeValue2 = StoreValue.builder()
        .order(2)
        .value("Second Value")
        .build();

    Mockito.when(storeValueRepositoryRepository.findAllByOrderByOrderAsc())
        .thenReturn(Arrays.asList(storeValue1, storeValue2));

  }

  @Test
  public void getCurrentValuesTest() {

    List<String> result = storeValueService.getCurrentValues();

    Mockito.verify(storeValueRepositoryRepository).findAllByOrderByOrderAsc();

    validateResults(result);
  }

  @Test
  public void storeNewValuesNullList() {

    List<String> result = storeValueService.storeNewValues(null);

    Mockito.verify(storeValueRepositoryRepository).findAllByOrderByOrderAsc();
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(0)).save(Mockito.any(StoreValue.class));
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(0)).deleteAllInBatch();

    validateResults(result);
  }

  @Test
  public void storeNewValuesEmptyList() {

    List<String> result = storeValueService.storeNewValues(Collections.emptyList());

    Mockito.verify(storeValueRepositoryRepository).findAllByOrderByOrderAsc();
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(0)).save(Mockito.any(StoreValue.class));
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(1)).deleteAllInBatch();

    validateResults(result);
  }

  @Test
  public void storeNewValuesOneItem() {

    Mockito.when(storeValueProperties.getMaxAllowValues())
        .thenReturn(MAX_VALUES);

    storeValueService.storeNewValues(Collections.singletonList("First Item"));

    Mockito.verify(storeValueRepositoryRepository).findAllByOrderByOrderAsc();
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(1)).deleteAllInBatch();
    ArgumentCaptor<StoreValue> storeValueArgumentCaptor = ArgumentCaptor.forClass(StoreValue.class);
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(1)).save(storeValueArgumentCaptor.capture());

    List<StoreValue> capturedStoreValues = storeValueArgumentCaptor.getAllValues();
    assertEquals(1, capturedStoreValues.size());
    assertEquals(0, capturedStoreValues.get(0).getOrder());
    assertEquals("First Item", capturedStoreValues.get(0).getValue());
  }

  @Test
  public void storeNewValuesFiveItems() {

    Mockito.when(storeValueProperties.getMaxAllowValues())
        .thenReturn(MAX_VALUES);

    storeValueService.storeNewValues(Arrays.asList("e1","e2","e3","e4","e5"));

    Mockito.verify(storeValueRepositoryRepository).findAllByOrderByOrderAsc();
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(1)).deleteAllInBatch();
    ArgumentCaptor<StoreValue> storeValueArgumentCaptor = ArgumentCaptor.forClass(StoreValue.class);
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(5)).save(storeValueArgumentCaptor.capture());

    List<StoreValue> capturedStoreValues = storeValueArgumentCaptor.getAllValues();
    assertEquals(5, capturedStoreValues.size());
    assertEquals(0, capturedStoreValues.get(0).getOrder());
    assertEquals("e5", capturedStoreValues.get(0).getValue());
    assertEquals(1, capturedStoreValues.get(1).getOrder());
    assertEquals("e4", capturedStoreValues.get(1).getValue());
    assertEquals(2, capturedStoreValues.get(2).getOrder());
    assertEquals("e3", capturedStoreValues.get(2).getValue());
    assertEquals(3, capturedStoreValues.get(3).getOrder());
    assertEquals("e2", capturedStoreValues.get(3).getValue());
    assertEquals(4, capturedStoreValues.get(4).getOrder());
    assertEquals("e1", capturedStoreValues.get(4).getValue());
  }

  @Test
  public void storeNewValuesMoreThanFiveItems() {

    Mockito.when(storeValueProperties.getMaxAllowValues())
        .thenReturn(MAX_VALUES);

    storeValueService.storeNewValues(Arrays.asList("e1","e2","e3","e4","e5","e6"));

    Mockito.verify(storeValueRepositoryRepository).findAllByOrderByOrderAsc();
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(1)).deleteAllInBatch();
    ArgumentCaptor<StoreValue> storeValueArgumentCaptor = ArgumentCaptor.forClass(StoreValue.class);
    Mockito.verify(storeValueRepositoryRepository, Mockito.times(5)).save(storeValueArgumentCaptor.capture());

    List<StoreValue> capturedStoreValues = storeValueArgumentCaptor.getAllValues();
    assertEquals(5, capturedStoreValues.size());
    assertEquals(0, capturedStoreValues.get(0).getOrder());
    assertEquals("e6", capturedStoreValues.get(0).getValue());
    assertEquals(1, capturedStoreValues.get(1).getOrder());
    assertEquals("e5", capturedStoreValues.get(1).getValue());
    assertEquals(2, capturedStoreValues.get(2).getOrder());
    assertEquals("e4", capturedStoreValues.get(2).getValue());
    assertEquals(3, capturedStoreValues.get(3).getOrder());
    assertEquals("e3", capturedStoreValues.get(3).getValue());
    assertEquals(4, capturedStoreValues.get(4).getOrder());
    assertEquals("e2", capturedStoreValues.get(4).getValue());
  }

  private void validateResults(List<String> result) {

    assertEquals(2, result.size());
    assertEquals("First Value", result.get(0));
    assertEquals("Second Value", result.get(1));
  }


}
