package com.WiredBrain.friends.service;

import com.WiredBrain.friends.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FriendService extends CrudRepository<Friend, Integer> {
    Iterable<Friend> findByFirstNameAndLastName(String firstname, String lastname);
    Iterable<Friend> findByFirstName(String firstname);
    Iterable<Friend> findByLastName(String lastname);

}
