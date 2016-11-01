package com.roy.repository;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.roy.domain.User;


/**
 * @author Roy Zeng
 */
@Repository
@CacheConfig(cacheNames = "demo")
public interface UserRepository extends JpaRepository<User, Integer> {

	@Cacheable
    User findByName(String name);

    @Query("from User u where u.name=:name")
    @Cacheable
    User findUser(@Param("name") String name);
}