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
            name = "user_id",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED)
    private Long userId;
    @Column(value="band_id")
    private Long bandId;
    @Column(value="task_id")
    private Long taskId;
    @Column(value="name")
    private String name;


}
