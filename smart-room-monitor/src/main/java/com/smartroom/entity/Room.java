package com.smartroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="room")
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String location;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SensorData> sensorDataList;

}
