package co.mobile.b.server.repository;

import co.mobile.b.server.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Boolean existsByUserKeyAndAndDeletedIsFalse(String userKey);

    Boolean existsByInviteCodeAndDeletedIsFalse(String inviteCode);

    Optional<Room> findByUserKeyAndDeletedFalse(String inviteCode);

    Optional<Room> findByInviteCodeAndAndDeletedFalse(String inviteCode);

    Boolean existsByInviteCodeAndAndUserKeyAndDeletedIsFalse(String inviteCode, String userKey);
}
