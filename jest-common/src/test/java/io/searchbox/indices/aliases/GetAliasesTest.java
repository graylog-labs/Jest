package io.searchbox.indices.aliases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class GetAliasesTest {

    @Test
    public void testBasicUriGeneration() {
        GetAliases getAliases = new GetAliases.Builder().addIndex("twitter").build();

        assertEquals("GET", getAliases.getRestMethodName());
        assertEquals("twitter/_alias", getAliases.getURI());
    }

    @Test
    public void equalsReturnsTrueForSameIndex() {
        GetAliases getAliases1 = new GetAliases.Builder().addIndex("twitter").build();
        GetAliases getAliases1Duplicate = new GetAliases.Builder().addIndex("twitter").build();

        assertEquals(getAliases1, getAliases1Duplicate);
    }

    @Test
    public void equalsReturnsFalseForDifferentIndex() {
        GetAliases getAliases1 = new GetAliases.Builder().addIndex("twitter").build();
        GetAliases getAliases2 = new GetAliases.Builder().addIndex("myspace").build();

        assertNotEquals(getAliases1, getAliases2);
    }

}