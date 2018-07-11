package de.smartsquare.smartnotes

import com.fasterxml.jackson.databind.ObjectMapper
/**
 * @author Ruben Gees
 */
class TestUtils {

    static String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper()

        return objectMapper.writeValueAsString(object)
    }
}
