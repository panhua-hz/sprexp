package com.ht.alphatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.alphatest.domain.TestStep;

public interface TestStepRepository extends JpaRepository<TestStep, Long> {

}
