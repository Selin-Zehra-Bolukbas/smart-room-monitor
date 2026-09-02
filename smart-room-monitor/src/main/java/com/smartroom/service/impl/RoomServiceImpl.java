package com.smartroom.service.impl;

import com.smartroom.dto.request.RoomRequest;
import com.smartroom.dto.response.RoomResponse;
import com.smartroom.entity.Room;
import com.smartroom.repository.RoomRepository;
import com.smartroom.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        Room room=modelMapper.map(roomRequest, Room.class);
        roomRepository.save(room);
        return modelMapper.map(room, RoomResponse.class);
    }

    @Override
    public RoomResponse getRoomById(Long roomId) {
        Room room=roomRepository.getOne(roomId);
        return modelMapper.map(room, RoomResponse.class);
    }

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(room->modelMapper.map(room,RoomResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse updateRoom(Long roomId,RoomRequest roomRequest) {
        Room room=roomRepository.getOne(roomId);
        modelMapper.map(roomRequest,room);
        Room updatedRoom=roomRepository.save(room);
        return modelMapper.map(updatedRoom,RoomResponse.class);
    }

    @Override
    public void deleteRoomById(Long roomId) {
        Room  room=roomRepository.getOne(roomId);
        roomRepository.delete(room);
    }
}
