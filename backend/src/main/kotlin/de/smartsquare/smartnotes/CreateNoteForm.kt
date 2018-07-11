package de.smartsquare.smartnotes

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * @author Ruben Gees
 */
data class CreateNoteForm(
    @field:NotBlank val content: String,
    @field:NotNull @field:JsonProperty("isDone") val isDone: Boolean
)
