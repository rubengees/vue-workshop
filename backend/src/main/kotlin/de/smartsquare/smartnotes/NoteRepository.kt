package de.smartsquare.smartnotes

import org.springframework.data.repository.CrudRepository

/**
 * @author Ruben Gees
 */
interface NoteRepository : CrudRepository<Note, Long>
