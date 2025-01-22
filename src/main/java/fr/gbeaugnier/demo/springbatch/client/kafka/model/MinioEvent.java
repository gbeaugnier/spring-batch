package fr.gbeaugnier.demo.springbatch.client.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MinioEvent(
        @JsonProperty("EventName") String eventName,
        @JsonProperty("Key") String key,
        @JsonProperty("Records") List<Records> records
) {

    public String getRecordKey() {
        return this.records.getFirst().s3().object().key();
    }

    public String getBucketName() {
        return this.records.getFirst().s3().bucket().name();
    }

    public String getObjectVersion() {
        return this.records.getFirst().s3().object().versionId();
    }

}

record Records(
        S3 s3
) { }

record S3(
        Bucket bucket,
        S3Object object
) { }

record Bucket(
        String name
) {}

record S3Object(
        String key,
        String contentType,
        String versionId
) {}
