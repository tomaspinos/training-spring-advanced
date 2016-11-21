package cz.profinit.training.springadvanced.integration;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvToMapTransformer extends AbstractPayloadTransformer<String, Map<String, Object>> {

    private final List<String> fieldNames;
    private final String delimiter;

    public CsvToMapTransformer(List<String> fieldNames, String delimiter) {
        this.fieldNames = fieldNames;
        this.delimiter = delimiter;
    }

    @Override
    protected Map<String, Object> transformPayload(String s) throws Exception {
        String[] fields = s.split(delimiter);

        Map<String, Object> ret = new HashMap<String, Object>();

        for (String fieldName : fieldNames) {
            if (fieldName != null) {
                ret.put(fieldName, fields[0]);
            }
        }

        return ret;
    }


}



