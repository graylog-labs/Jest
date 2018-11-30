package io.searchbox.indices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author cihat keser
 */
public class AnalyzeTest {

    private final ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

    @Test
    public void testBasicUrlGeneration() throws Exception {
        Analyze analyze = new Analyze.Builder()
                .analyzer("standard")
                .build();
        assertEquals("/_analyze", analyze.getURI());
        assertEquals("{\"analyzer\":\"standard\",\"text\":[]}", analyze.getData(objectMapper));
    }

    @Test
    public void testUrlGenerationWithCustomTransientAnalyzer() throws Exception {
        Analyze analyze = new Analyze.Builder()
                .tokenizer("keyword")
                .filter("lowercase")
                .build();
        assertEquals("/_analyze", analyze.getURI());
        assertEquals("{\"filter\":[\"lowercase\"],\"text\":[],\"tokenizer\":\"keyword\"}", analyze.getData(objectMapper));
    }

    @Test
    public void testUrlGenerationWithIndex() {
        Analyze analyze = new Analyze.Builder()
                .index("test")
                .build();
        assertEquals("test/_analyze", analyze.getURI());
    }

    @Test
    public void testUrlGenerationWithIndexAndAnalyzer() throws Exception {
        Analyze analyze = new Analyze.Builder()
                .index("test")
                .analyzer("whitespace")
                .build();
        assertEquals("test/_analyze", analyze.getURI());
        assertEquals("{\"analyzer\":\"whitespace\",\"text\":[]}", analyze.getData(objectMapper));
    }

    @Test
    public void testUrlGenerationWithIndexAndFieldMapping() throws Exception {
        Analyze analyze = new Analyze.Builder()
                .index("test")
                .field("obj1.field1")
                .build();
        assertEquals("test/_analyze", analyze.getURI());
        assertEquals("{\"field\":\"obj1.field1\",\"text\":[]}", analyze.getData(objectMapper));
    }

    @Test
    public void testPayloadWithASingleTextEntry() throws Exception {
        Analyze analyze = new Analyze.Builder()
                .text("foo")
                .build();
        assertEquals("{\"text\":[\"foo\"]}", analyze.getData(objectMapper));
    }

    @Test
    public void testPayloadWithAMultipleTextEntry() throws Exception {
        Analyze analyze = new Analyze.Builder()
                .text("foo")
                .text("bar")
                .build();
        assertEquals("{\"text\":[\"foo\",\"bar\"]}", analyze.getData(objectMapper));
    }

    @Test
    public void testPayloadWithAListTextEntry() throws Exception {
        Analyze analyze = new Analyze.Builder()
                .text(ImmutableList.of("foo", "bar"))
                .text("baz")
                .build();
        assertEquals("{\"text\":[\"foo\",\"bar\",\"baz\"]}", analyze.getData(objectMapper));
    }

    @Test
    public void equalsReturnsTrueForSameSource() {
        Analyze analyze1 = new Analyze.Builder()
                .index("test")
                .analyzer("whitespace")
                .text("source")
                .build();
        Analyze analyze1Duplicate = new Analyze.Builder()
                .index("test")
                .analyzer("whitespace")
                .text("source")
                .build();

        assertEquals(analyze1, analyze1Duplicate);
    }

    @Test
    public void equalsReturnsFalseForDifferentSources() {
        Analyze analyze1 = new Analyze.Builder()
                .index("test")
                .analyzer("whitespace")
                .text("source")
                .build();
        Analyze analyze2 = new Analyze.Builder()
                .index("test")
                .analyzer("whitespace")
                .text("source2")
                .build();

        assertNotEquals(analyze1, analyze2);
    }

}
