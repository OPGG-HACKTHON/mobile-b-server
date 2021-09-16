package co.mobile.b.server.util;

import co.mobile.b.server.config.redis.UserConnectionInfo;
import co.mobile.b.server.dto.response.MessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Redis util.
 */
@AllArgsConstructor
@Component
public class RedisUtil {

    private final String PREFIX = "room-";
    private final String CONNECTIONS = "connections";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Get room log list.
     *
     * @param inviteCode the invite code
     * @return the list
     */
    public List<String> getRoomLog(String inviteCode){
        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
        final String key = PREFIX + inviteCode;
        final Long len = stringStringListOperations.size(key);
        return stringStringListOperations.range(key,0, len);
    }

    /**
     * Save room log.
     *
     * @param inviteCode    the invite code
     * @param messageResult the message result
     * @throws JsonProcessingException the json processing exception
     */
    public void saveRoomLog(String inviteCode, MessageResult messageResult) throws JsonProcessingException {
        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
        String jsonString = mapper.writeValueAsString(messageResult);
        final String key = PREFIX + inviteCode;
        stringStringListOperations.rightPush(key, jsonString);
    }

    /**
     * Save connections.
     *
     * @param userConnectionInfo the user connection info
     * @throws JsonProcessingException the json processing exception
     */
    public void saveConnections(UserConnectionInfo userConnectionInfo) throws JsonProcessingException {
        final HashOperations<String, String, String> stringStringHashOperations = redisTemplate.opsForHash();
        String connInfoJsonString = mapper.writeValueAsString(userConnectionInfo);
        stringStringHashOperations.put(CONNECTIONS, userConnectionInfo.getSessionId(), connInfoJsonString);
    }

    /**
     * Gets connections.
     *
     * @param sessionId the session id
     * @return the connections
     * @throws Exception the exception
     */
    public UserConnectionInfo getConnections(String sessionId) throws Exception {
        final HashOperations<String, String, String> stringStringHashOperations = redisTemplate.opsForHash();
        String connInfoJsonString = stringStringHashOperations.get(CONNECTIONS, sessionId);
        return mapper.readValue(connInfoJsonString, UserConnectionInfo.class);
    }

    /**
     * Delete connections.
     *
     * @param sessionId the session id
     */
    public void deleteConnections(String sessionId) {
        final HashOperations<String, String, String> stringStringHashOperations = redisTemplate.opsForHash();
        stringStringHashOperations.delete(CONNECTIONS, sessionId);
    }
}
