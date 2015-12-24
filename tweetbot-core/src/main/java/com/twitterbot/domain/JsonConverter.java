package com.twitterbot.domain;

import com.twitterbot.common.SimpleStatus;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

/**
 * Created by fckey on 2015/12/24.
 */
public class JsonConverter {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String convertToString(List<SimpleStatus> ss) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ss);
        return json;
    }

    public static List<SimpleStatus> convertToStatuses(String s) throws IOException {
        return mapper.readValue(s, new TypeReference<List<SimpleStatus>>() {});
    }

}
