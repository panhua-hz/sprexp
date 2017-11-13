package com.ht.alphatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.alphatest.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
