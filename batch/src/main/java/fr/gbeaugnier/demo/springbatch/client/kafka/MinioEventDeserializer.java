package fr.gbeaugnier.demo.springbatch.client.kafka;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.gbeaugnier.demo.springbatch.client.kafka.model.MinioEvent;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unused")
public class MinioEventDeserializer implements Deserializer<MinioEvent> {

    private final Logger logger = LoggerFactory.getLogger(MinioEventDeserializer.class);
    private ObjectMapper mapper;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        mapper = new ObjectMapper(factory);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public MinioEvent deserialize(String s, byte[] bytes) {
        logger.info("Deserializing MinioEvent for {}", s);
        try {
            return mapper.readValue(bytes, MinioEvent.class);
        } catch (IOException e) {
            logger.error("Error while deserializing MinioEvent for {}", s, e);
        }
        return null;
    }

}
