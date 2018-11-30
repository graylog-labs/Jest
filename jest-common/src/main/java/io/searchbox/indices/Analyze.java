package io.searchbox.indices;

import com.google.common.collect.ImmutableMap;
import io.searchbox.action.AbstractAction;
import io.searchbox.action.GenericResultAbstractAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Performs the analysis process on a text and return the tokens breakdown of the text.
 *
 * @author Dogukan Sonmez
 * @author cihat keser
 */
public class Analyze extends GenericResultAbstractAction {

    protected Analyze(Builder builder) {
        super(builder);

        this.indexName = builder.index;
        this.payload = Collections.singletonMap("text", builder.textToAnalyze);

        final ImmutableMap.Builder<String, Object> payloadBuilder = ImmutableMap.builder();
        payloadBuilder.put("text", builder.textToAnalyze);

        if (!isNullOrEmpty(builder.analyzer)) {
            payloadBuilder.put("analyzer", builder.analyzer);
        }
        if (!isNullOrEmpty(builder.field)) {
            payloadBuilder.put("field", builder.field);
        }
        if (!isNullOrEmpty(builder.tokenizer)) {
            payloadBuilder.put("tokenizer", builder.tokenizer);
        }
        if (builder.filter != null && !builder.filter.isEmpty()) {
            payloadBuilder.put("filter", builder.filter);
        }

        this.payload = payloadBuilder.build();

        setURI(buildURI());
    }

    @Override
    protected String buildURI() {
        return super.buildURI() + "/_analyze";
    }

    @Override
    public String getRestMethodName() {
        return "POST";
    }

    public static class Builder extends AbstractAction.Builder<Analyze, Builder> {
        private String index;
        private List<String> textToAnalyze = new ArrayList<String>();
        private String analyzer;
        private String field;
        private String tokenizer;
        private List<String> filter = new ArrayList<>();

        public Builder index(String index) {
            this.index = index;
            return this;
        }

        public Builder text(String textToAnalyze) {
            this.textToAnalyze.add(textToAnalyze);
            return this;
        }

        public Builder text(Collection<? extends String> textToAnalyze) {
            this.textToAnalyze.addAll(textToAnalyze);
            return this;
        }

        public Builder analyzer(String analyzer) {
            this.analyzer = analyzer;
            return this;
        }

        /**
         * The analyzer can be derived based on a field mapping.
         */
        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder tokenizer(String tokenizer) {
            this.tokenizer = tokenizer;
            return this;
        }

        public Builder filter(String filter) {
            this.filter.add(filter);
            return this;
        }

        /**
         * By default, the format the tokens are returned in are in json and its called detailed.
         * The text format value provides the analyzed data in a text stream that is a bit more readable.
         */
        public Builder format(String format) {
            return setParameter("format", format);
        }

        @Override
        public Analyze build() {
            return new Analyze(this);
        }
    }

}
