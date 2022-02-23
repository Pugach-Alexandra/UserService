package com.example.Mafia.repository;

import com.example.Mafia.model.User;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserRepository extends CassandraRepository<User,Long> {

}
