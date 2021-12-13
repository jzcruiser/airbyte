/*
 * Copyright (c) 2021 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.s3.parquet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fasterxml.jackson.databind.JsonNode;
import io.airbyte.commons.json.Jsons;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.junit.jupiter.api.Test;

class S3ParquetFormatConfigTest {

  @Test
  public void testConfigConstruction() {
    final JsonNode formatConfig = Jsons.deserialize("{\n"
        + "\t\"compression_codec\": \"GZIP\",\n"
        + "\t\"block_size_mb\": 1,\n"
        + "\t\"max_padding_size_mb\": 1,\n"
        + "\t\"page_size_kb\": 1,\n"
        + "\t\"dictionary_page_size_kb\": 1,\n"
        + "\t\"dictionary_encoding\": false\n"
        + "}");

    final S3ParquetFormatConfig config = new S3ParquetFormatConfig(formatConfig);

    // The constructor should automatically convert MB or KB to bytes.
    assertEquals(1024 * 1024, config.blockSize());
    assertEquals(1024 * 1024, config.maxPaddingSize());
    assertEquals(1024, config.pageSize());
    assertEquals(1024, config.dictionaryPageSize());

    assertEquals(CompressionCodecName.GZIP, config.compressionCodec());
    assertFalse(config.dictionaryEncoding());
  }

}
