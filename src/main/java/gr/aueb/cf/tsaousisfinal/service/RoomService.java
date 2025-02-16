package gr.aueb.cf.tsaousisfinal.service;

import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.mapper.Mapper;
import gr.aueb.cf.tsaousisfinal.model.static_data.Room;
import gr.aueb.cf.tsaousisfinal.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);
    private final RoomRepository roomRepository;
    private final Mapper entityMapper;

    /**
     * Fetches a list of all rooms.
     *
     * @return List of RoomReadOnlyDTO
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<RoomReadOnlyDTO> getAllRooms() throws AppObjectNotFoundException {
        LOGGER.info("Fetching all rooms");

        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {throw new AppObjectNotFoundException("ROOM", "No Rooms found");}
        LOGGER.info("Successfully fetched {} rooms", rooms.size());

        return rooms.stream()
                .map(entityMapper::mapToReadOnlyRoomDTO)
                .collect(Collectors.toList());
    }

    /**
     * Checks if a specific room is available.
     *
     * @param roomId the ID of the room
     * @return true if the room has available capacity, false otherwise
     * @throws AppObjectNotFoundException if the room is not found
     */
    @Transactional(readOnly = true)
    public boolean isRoomAvailable(Long roomId) throws AppObjectNotFoundException {
        LOGGER.info("Checking availability for room with ID {}", roomId);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppObjectNotFoundException("ROOM", "Room with ID " + roomId + " not found."));

        boolean available = room.isAvailable();
        LOGGER.info("Room with ID {} is {}available", roomId, available ? "" : "not ");

        return available;
    }
}
