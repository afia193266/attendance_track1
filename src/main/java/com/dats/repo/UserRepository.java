package com.dats.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dats.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	UserEntity findByUsername(String username);
	
	UserEntity findByEmail(String email);
	
	@Query("select ue from UserEntity ue where ue.username like CONCAT('%', :name, '%')")
	List<UserEntity> findByMatchingName(@Param("name") String name);
	
	
}
