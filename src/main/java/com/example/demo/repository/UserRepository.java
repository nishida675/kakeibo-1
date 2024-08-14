package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value="SELECT * FROM user_table WHERE email = :email LIMIT 1", nativeQuery=true)
	public User findByEmail(@Param("email") String email);
	
	@Query(value="SELECT * FROM user_table WHERE name = :name AND email = :email LIMIT 1", nativeQuery=true)
	public User findByEmailAndPassword(@Param("name") String name, @Param("email") String email);
	
	
}