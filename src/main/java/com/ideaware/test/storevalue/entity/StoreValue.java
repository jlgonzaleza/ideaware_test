package com.ideaware.test.storevalue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_value")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreValue {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(name = "item_order")
  private long order;

  @NotNull
  @NotEmpty
  @Column(name = "item_value")
  private String value;

}
