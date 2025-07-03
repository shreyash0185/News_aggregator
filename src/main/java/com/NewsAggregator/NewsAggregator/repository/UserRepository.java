package com.NewsAggregator.NewsAggregator.repository;


import com.NewsAggregator.NewsAggregator.entity.User;
import com.NewsAggregator.NewsAggregator.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

//    void saveVerificationToken(VerificationToken verificationToken);
}
