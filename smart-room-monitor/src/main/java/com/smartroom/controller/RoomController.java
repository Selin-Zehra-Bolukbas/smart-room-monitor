package com.smartroom.controller;

import com.smartroom.dto.request.RoomRequest;
import com.smartroom.dto.response.RoomResponse;
import com.smartroom.service.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/smart-room-monitor/room")
public class RoomController {

    private final RoomServiceImpl roomService;

    @PostMapping("/create")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest roomRequest) {
        RoomResponse roomResponse = roomService.createRoom(roomRequest);
        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> roomResponseList = roomService.getAllRooms();
        return ResponseEntity.ok(roomResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @RequestBody RoomRequest roomRequest) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id) {
        roomService.deleteRoomById(id);
        return ResponseEntity.noContent().build();
    }

}
