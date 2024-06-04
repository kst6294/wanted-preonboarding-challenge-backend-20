package wanted.challenge.custom;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import wanted.challenge.goods.dto.response.GoodsResponseDto;

import java.io.IOException;


public class CustomEnumDeserializer extends JsonDeserializer<GoodsResponseDto.GoodsStatus> {
    @Override
    public GoodsResponseDto.GoodsStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        //jsonparsing을 utf8로 설정
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        return mapper.readValue(jsonParser, GoodsResponseDto.GoodsStatus.class);
//        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
//        String value = jsonNode.asText();
//        System.out.println(
//                "jsonNode = " + jsonNode + ", value = " + value
//        );
//
//        if (value.equals("판매완료")) {
//            return GoodsResponseDto.GoodsStatus.판매완료;
//        } else if (value.equals("판매중")) {
//            return GoodsResponseDto.GoodsStatus.판매중;
//        } else if (value.equals("예약중")) {
//            return GoodsResponseDto.GoodsStatus.예약중;
//        } else {
//            throw new IllegalArgumentException("올바르지않은 값입니다 :  " + value);
//        }
    }
}
