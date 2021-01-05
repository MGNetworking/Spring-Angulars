package com.spring.security.jwt.springsecurityjwt.dao;

import com.spring.security.jwt.springsecurityjwt.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
