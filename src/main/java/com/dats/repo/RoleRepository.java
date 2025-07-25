package com.dats.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dats.entity.RoleEntity;


public interface RoleRepository extends JpaRepository<RoleEntity, Integer>{
	RoleEntity findByName(String name);
}
