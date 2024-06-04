package wanted.challenge.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import wanted.challenge.goods.dto.response.GoodsResponseDto;

import java.io.IOException;

public class CustomEnumSerializer extends StdSerializer<GoodsResponseDto.GoodsStatus> {
    public CustomEnumSerializer() {
        super(GoodsResponseDto.GoodsStatus.class);
    }

    @Override
    public void serialize(GoodsResponseDto.GoodsStatus goodsStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // Enum이 한글이라 깨짐 방지
        jsonGenerator.writeString(goodsStatus.name());
    }
}
