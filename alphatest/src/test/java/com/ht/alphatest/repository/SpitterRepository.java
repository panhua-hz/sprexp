package com.ht.alphatest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.alphatest.domain.KeyWord;

/**
 * Repository interface with operations for {@link Spitter} persistence.
 * @author habuma
 */
public interface SpitterRepository extends JpaRepository<KeyWord, Long>, SpitterSweeper {
	  
	//KeyWord findByUsername(String username);
	
	//List<KeyWord> findByUsernameOrFullNameLike(String username, String fullName);

}
