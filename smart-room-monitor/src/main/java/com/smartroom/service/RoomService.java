package com.smartroom.service;

import com.smartroom.dto.request.RoomRequest;
import com.smartroom.dto.response.RoomResponse;

import java.util.List;


public interface RoomService {
    RoomResponse createRoom(RoomRequest roomRequest);
    RoomResponse getRoomById(Long roomId);
    List<RoomResponse> getAllRooms();
    RoomResponse updateRoom(Long roomId,RoomRequest roomRequest);
    void  deleteRoomById(Long roomId);
}
