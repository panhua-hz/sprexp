package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.TCase;

public interface TCaseRepository extends JpaRepository<TCase, Long> {

}
