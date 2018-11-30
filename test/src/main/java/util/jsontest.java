package util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class jsontest {

    public static void main(String[] args) throws IOException {
        try {
            String json = "{\"info\":[{\"gateway\":\"10.9.11.1\",\"hver\":\"V3.4.81build 170227\",\"port\":8000,\"sn\":\"DS-8116HQH-SH1620170324CCCH736916715WCVU\",\"submsk\":{\"1\":[1,1,1,1,1],\"2\":\"2222\"},\"sver\":{\"1\":\"2\",\"2\":\"1\"},\"type\":[1,1,2,3,4,5]}]}";
            ObjectMapper oMapper = new ObjectMapper();
            JsonNode rootNode = oMapper.readTree(json); // 读取Json
            JsonNode infoMap = rootNode.path("info");
            for (int k = 0; k < infoMap.size(); k++){
                JsonNode lst = infoMap.get(k).get("submsk");
                String str = lst.asText();
                String str2 = lst.toString();
                String str4 = oMapper.writeValueAsString(lst);
                String str1 = infoMap.path(k).path("gateway").asText();
                String str5 = infoMap.path(k).path("port").asText();
                String str3 = infoMap.path(k).path("gateway").toString();
                System.out.println(lst.toString());
                if(infoMap.path(k).path("sver").isObject()){
                    Iterator<Map.Entry<String, JsonNode>> it = infoMap.path(k).path("sver").fields();
                    while (it.hasNext())
                    {
                        Map.Entry<String, JsonNode> entry = it.next();
                        entry.getValue();
                        entry.getKey();
                    }
                }
//                for(int i = 0; i < lst.size(); i++){
//                    System.out.println(lst.get(i));
//                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static <T> T json2Obj(String jsonStr, TypeReference<T> typeReference)
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(jsonStr, typeReference);
    }


}
