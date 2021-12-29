package com.operations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Room;
import com.entities.RoomID;

public interface RoomOperations extends JpaRepository<Room, RoomID>{

}
