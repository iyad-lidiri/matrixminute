package com.matrixminute.repository;

import com.matrixminute.entity.AttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttemptRepository extends JpaRepository<AttemptEntity, Long> {

    Optional<AttemptEntity> findByUserIdAndDate(String userId, String date);
}
