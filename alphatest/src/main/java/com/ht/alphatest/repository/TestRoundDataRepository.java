package com.ht.alphatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.alphatest.domain.TestRoundData;

public interface TestRoundDataRepository extends JpaRepository<TestRoundData, Long> {

}
