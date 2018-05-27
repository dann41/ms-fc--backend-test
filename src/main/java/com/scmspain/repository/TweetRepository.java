package com.scmspain.repository;

import com.scmspain.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("SELECT t FROM Tweet AS t WHERE pre2015MigrationStatus<>99 AND discarded = false ORDER BY id DESC")
    List<Tweet> findAllSortedByIdDesc();

}
