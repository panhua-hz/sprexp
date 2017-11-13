package com.ht.alphatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.alphatest.domain.TestCase;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

}
