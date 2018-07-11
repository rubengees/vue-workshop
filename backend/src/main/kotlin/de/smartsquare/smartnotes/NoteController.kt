package de.smartsquare.smartnotes

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI
import javax.validation.Valid

/**
 * @author Ruben Gees
 */
@CrossOrigin
@RestController
@RequestMapping("/api/notes")
class NoteController(private val repository: NoteRepository) {

    @PostMapping()
    fun create(@Valid @RequestBody form: Mono<CreateNoteForm>): Mono<ResponseEntity<Any>> {
        return form.map { Note(null, it.content, it.isDone) }
            .map { repository.save(it) }
            .map { ResponseEntity.created(URI.create("/api/notes/" + it.id)).build<Any>() }
    }

    @GetMapping
    fun retrieveAll(): Mono<ResponseEntity<List<Note>>> {
        return Flux.fromIterable(repository.findAll())
            .collectList()
            .map { ResponseEntity.ok().body(it) }
    }

    @GetMapping("/{noteId}")
    fun retrieve(@PathVariable noteId: Long): Mono<ResponseEntity<Note>> {
        return Mono.justOrEmpty(repository.findById(noteId).orElse(null))
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build<Note>())
    }

    @PutMapping("/{noteId}")
    fun update(
        @Valid @RequestBody form: Mono<CreateNoteForm>,
        @PathVariable noteId: Long
    ): Mono<ResponseEntity<Any>> {
        return Mono.justOrEmpty(repository.findById(noteId).orElse(null))
            .zipWith(form) { existing: Note, new: CreateNoteForm ->
                existing.copy(content = new.content, isDone = new.isDone)
            }
            .map { repository.save(it) }
            .map { ResponseEntity.ok().build<Any>() }
            .defaultIfEmpty(ResponseEntity.notFound().build<Any>())
    }

    @DeleteMapping("/{noteId}")
    fun delete(@PathVariable noteId: Long): Mono<ResponseEntity<Any>> {
        return Mono.justOrEmpty(repository.findById(noteId).orElse(null))
            .map { repository.delete(it) }
            .map { ResponseEntity.ok().build<Any>() }
            .defaultIfEmpty(ResponseEntity.notFound().build<Any>())
    }
}
