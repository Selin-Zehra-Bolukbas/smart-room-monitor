package com.smartroom.repository;

import com.smartroom.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData,Long> {
    List<SensorData> findByRoom_Id(Long roomId);
    Optional<SensorData> findFirstByRoomIdOrderByTimestampDesc(Long roomId);
    List<SensorData> findTop50ByRoomIdOrderByTimestampDesc(Long roomId);
}
