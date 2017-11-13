package com.ht.alphatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.alphatest.domain.TestCaseTrack;

public interface TestCaseTrackRepository extends JpaRepository<TestCaseTrack, Long> {

}
