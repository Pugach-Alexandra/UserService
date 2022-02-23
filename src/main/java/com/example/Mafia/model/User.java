package com.example.Mafia.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;


@Data
@Table(value="user")
public class User {
    @Id
    @PrimaryKeyColumn(
            name = "userid",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED)
    private Long userId;
    @Column(value="name")
    private String name;
    @Column(value="bandid")
    private Long bandId;
    @Column(value="taskid")
    private Long taskId;



}
