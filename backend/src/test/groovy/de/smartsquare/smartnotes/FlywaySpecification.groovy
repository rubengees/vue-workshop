package de.smartsquare.smartnotes

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

/**
 * @author Ruben Gees
 */
abstract class FlywaySpecification extends Specification {

    @Autowired
    Flyway flyway

    void setup() {
        flyway.clean()
        flyway.migrate()
    }
}
