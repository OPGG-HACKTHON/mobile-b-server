package co.mobile.b.server.repository;

import co.mobile.b.server.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Boolean existsByUserKeyAndAndDeletedIsFalse(String userKey);
    Boolean existsByInviteCodeAndDeletedIsFalse(String inviteCode);
}
