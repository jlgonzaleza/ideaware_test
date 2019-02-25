package com.ideaware.test.storevalue.repository;

import com.ideaware.test.storevalue.entity.StoreValue;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreValueRepository extends JpaRepository<StoreValue, Long> {
  List<StoreValue> findAllByOrderByOrderAsc();
}
