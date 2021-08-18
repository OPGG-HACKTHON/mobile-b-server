package co.mobile.b.server.repository;

import co.mobile.b.server.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Boolean existsByUserKeyAndAndDeletedIsFalse(String userKey);

    Optional<Room> findByInviteCodeAndAndDeletedFalse(String inviteCode);
}
