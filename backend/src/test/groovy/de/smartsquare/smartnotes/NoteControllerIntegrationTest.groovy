package de.smartsquare.smartnotes

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Unroll

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import static org.springframework.web.reactive.function.BodyInserters.fromObject

/**
 * @author Ruben Gees
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class NoteControllerIntegrationTest extends FlywaySpecification {

    @Autowired
    private WebTestClient webTestClient

    @Autowired
    private NoteRepository noteRepository

    def "creating a note should work properly"() {
        given:
        def form = new CreateNoteForm("testContent", true)

        expect:
        webTestClient.post()
                .uri("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(form))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueEquals("location", "/api/notes/4")

        def note = noteRepository.findById(4l).get()
        note.content == "testContent"
        note.isDone
    }

    @Unroll
    def "trying to create a note with invalid data should fail"() {
        given:
        Map<String, String> input = [
                "content": content,
                "isDone" : isDone
        ]

        expect:
        webTestClient.post()
                .uri("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(input))
                .exchange()
                .expectStatus().isBadRequest()

        !noteRepository.findById(4l).isPresent()

        where:
        content       | isDone
        ""            | "true"
        "testContent" | "invalid"
    }

    def "retrieving all notes should work properly"() {
        expect:
        webTestClient.get()
                .uri("/api/notes")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("\$.length()").isEqualTo(3)
                .jsonPath("\$[0].content").isEqualTo("Write Code")
    }

    def "retrieving a specific note should work properly"() {
        expect:
        webTestClient.get()
                .uri("/api/notes/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("\$.content").isEqualTo("Test Code")
    }

    def "trying to retrieve a non existing note should fail"() {
        expect:
        webTestClient.get()
                .uri("/api/notes/-1")
                .exchange()
                .expectStatus().isNotFound()
    }

    def "updating a note should work properly"() {
        given:
        def form = new CreateNoteForm("testContent", true)

        expect:
        webTestClient.put()
                .uri("/api/notes/2")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(form))
                .exchange()
                .expectStatus().isOk()

        def note = noteRepository.findById(2l).get()
        note.content == "testContent"
        note.isDone
    }

    @Unroll
    def "trying to update a note with invalid data should fail"() {
        given:
        Map<String, String> input = [
                "content": content,
                "isDone" : isDone
        ]

        expect:
        webTestClient.put()
                .uri("/api/notes/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(input))
                .exchange()
                .expectStatus().isEqualTo(expectedError)

        def note = noteRepository.findById(2L).get()
        note.content == "Test Code"
        !note.isDone

        where:
        id   | content       | isDone    | expectedError
        "-1" | "testContent" | "true"    | HttpStatus.NOT_FOUND
        "2"  | ""            | "true"    | HttpStatus.BAD_REQUEST
        "2"  | "testContent" | "invalid" | HttpStatus.BAD_REQUEST
    }

    def "deleting a note should work properly"() {
        expect:
        webTestClient.delete()
                .uri("/api/notes/2")
                .exchange()
                .expectStatus().isOk()

        !noteRepository.findById(2l).isPresent()
    }

    def "trying to delete a non existing note should fail"() {
        expect:
        webTestClient.delete()
                .uri("/api/notes/-1")
                .exchange()
                .expectStatus().isNotFound()
    }
}
