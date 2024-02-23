package com.sh.shpay.global.util.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Get value from redis
     * @param key
     * @param valueType
     * @return
     * @param <T>
     */
    public <T> T getByClassType(String key, Class<T> valueType) {
        String str = (String)redisTemplate.opsForValue().get(key);
        if(str != null) {
            Object obj = parseStringToObject(str, valueType);
            return (T) obj;
        }
        return null;
    }



    /**
     * Push String type to redis
     * @param key
     * @param value
     * @param expirationTime
     */
    public void putString(String key, Object value, Long expirationTime) {
        if(expirationTime != null){
            String obj = parseObjectToString(value);
            redisTemplate.opsForValue().set(key, obj, expirationTime, TimeUnit.SECONDS);
        }else{
            String obj = parseObjectToString(value);
            redisTemplate.opsForValue().set(key, obj);
        }
    }

    /**
     * Push Set type to redis
     * @param key
     * @param value
     * @param expirationTime
     * @return
     */
    public Long putSet(String key, Object value, Long expirationTime) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        if(expirationTime != null){
            String obj = parseObjectToString(value);
            return redisTemplate.opsForSet().add(key, obj, expirationTime, TimeUnit.SECONDS);

        }else{
            String obj = parseObjectToString(value);
            return  redisTemplate.opsForSet().add(key, obj);
        }
    }

    public Set<Long> getSetMembers(String key){

        SetOperations<String, Object> set = redisTemplate.opsForSet();
        Set<Object> members = set.members(key);

        Set<Long> redis = new HashSet<>(); //추가
        for (Object member : members) {
            redis.add(Long.parseLong((String)member));
        }

        return redis;

    }



    public Long removeSetValue(String key, Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();

        return set.remove(key, value);
    }


    public boolean deleteKey(String key){
        return redisTemplate.delete(key);
    }

    public boolean isExists(String key){
        try {
            return redisTemplate.hasKey(key);
        }
        catch (NullPointerException e){ // key가 존재하지 않으면 null
            return false;
        }

    }

    public void setKeyWithExpireTime(String key, long expirationTime){
        redisTemplate.expire(key, expirationTime, TimeUnit.SECONDS);
    }

    public long getExpireTime(String key){
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }



    /**
     * Bulk insert userId separately to redis
     * @param key
     * @param userIdList
     */
    public void bulkInsertForLikes(String key, List<Long> userIdList){
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        RedisSerializer<String> valueSerializer = (RedisSerializer<String>) redisTemplate.getValueSerializer();

        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (Long userId : userIdList) {
                byte[] serializeKey = stringSerializer.serialize(key);
                String value = parseObjectToString(userId);
                byte[] serializeValue = valueSerializer.serialize(value);
                connection.sAdd(serializeKey, serializeValue);
            }

            return null;
        });
    }

    /**
     * Parse Object type to String type
     * @param data
     * @return
     */
    private String parseObjectToString(Object data)  {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("===== parseObjectToString Error ====");
        }

        return result;
    }

    /**
     * Parse String type to Object type
     * @param data
     * @param valueType
     * @return
     * @param <T>
     */
    private <T> Object parseStringToObject(String data, Class<T> valueType)  {
        T obj = null;

        try {
            obj = objectMapper.readValue(data, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("==== parseStringToObject Error ====");
        }

        return obj;
    }


}
