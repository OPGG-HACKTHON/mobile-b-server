package co.mobile.b.server.lettuce;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
public class redisRepositoryConfigTest {
    @Autowired
    StringRedisTemplate redisTemplate;
    ObjectMapper mapper = new ObjectMapper();
    JSONObject jsonObject = new JSONObject();

    @Test
    public void testString(){
        final String key =  "test string";
        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(key,"1");
        final String result_1 = stringStringValueOperations.get(key);

        System.out.println("result_1 : " + result_1);

        stringStringValueOperations.increment(key);
        final String result_2 = stringStringValueOperations.get(key);

        System.out.println("result_2 : " + result_2);
    }

    @Test
    public void testList() throws Exception{
        final String key = "room-1234";
        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();

        final String jsonData_1 = stringStringListOperations.index(key, 1);

        System.out.println("jsonData_1 = " + jsonData_1);

        Map<String,String> jsonMap = mapper.readValue(jsonData_1,Map.class);

        System.out.println(jsonMap.values());

        Map<String,String> testMap = new HashMap<String, String>();

        testMap.put("date","03:58:12");
        testMap.put("user","adc");
        testMap.put("message","json 변환");

        for( Map.Entry<String, String> entry : testMap.entrySet()  ){
            String mapKey = entry.getKey();
            Object mapValue = entry.getValue();
            jsonObject.put(mapKey, mapValue);
        }

        String jsonString = jsonObject.toString();

        System.out.println(jsonString);

        String newKey = "room-4567";
        stringStringListOperations.rightPush(newKey,jsonString);
    }
}
