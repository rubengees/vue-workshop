package de.smartsquare.smartnotes

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * @author Ruben Gees
 */
@Entity
data class Note(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long?,
    val content: String,
    @get:JsonProperty("isDone") val isDone: Boolean
)
