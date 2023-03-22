package com.shenlx.xinwen.springbootjackson.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shenlx.xinwen.springbootjackson.domain.User;

import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-21 10:29
 **/

public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("user-name", user.getUserName());
        generator.writeEndObject();
    }
}
