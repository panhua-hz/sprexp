package com.ht.alphatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.alphatest.domain.TestPlan;

public interface TestPlanRepository extends JpaRepository<TestPlan, Long> {

}
