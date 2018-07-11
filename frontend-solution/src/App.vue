<template>
  <div id="app">
    <h1>Smart Notes</h1>

    <div class="container mt-4">
      <div class="row justify-content-center">
        <b-list-group class="col-8 col-offset-2">
          <b-list-group-item v-for="(note, i) in notes" :key="note.id" class="d-flex">

            <b-form-input
                v-if="note.isEditing"
                v-model="note.content"
                type="text"
                class="align-self-center mr-auto"
            >
            </b-form-input>

            <span
                v-else
                class="note-text align-self-center mr-auto"
                :class="[note.isDone ? 'done-note' : '']"
                @click="toggleDone(note, i)"
            >
              {{ note.content }}
            </span>

            <b-button
                variant="primary"
                class="align-self-center ml-4"
                @click="toggleOrEditNote(note, i)"
                :disabled="note.isLoading"
            >
              <i
                  class="fa"
                  :class="[note.isEditing ? 'fa-save' : 'fa-edit']"
              >
              </i>
            </b-button>

          </b-list-group-item>

          <b-list-group-item class="d-flex">
            <b-form-input v-model="newContent" type="text" class="mr-auto" placeholder="Neue Notiz..."></b-form-input>

            <b-button variant="primary" class="align-self-center ml-4" @click="createNote()" :disabled="newLoading">
              <i class="fa fa-save"></i>
            </b-button>
          </b-list-group-item>
        </b-list-group>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable no-console */

import Vue from "vue";
import axios from "axios";

export default {
  data() {
    return {
      notes: [],
      newContent: null,
      newLoading: false
    };
  },

  mounted() {
    axios
      .get("http://localhost:8000/api/notes")
      .then(response => {
        this.notes = response.data.map(it =>
          Object.assign({ isEditing: false, isLoading: false }, it)
        );
      })
      .catch(error => {
        console.log(error);
      });
  },

  methods: {
    toggleDone(note, position) {
      if (!note.isLoading) {
        note.isLoading = true;

        const copy = Object.assign({}, note, { isDone: !note.isDone });

        this.editNote(copy, position);
      }
    },

    toggleOrEditNote(note, position) {
      if (note.isEditing) {
        if (note.content && note.content.length > 0) {
          note.isLoading = true;

          this.editNote(note, position);
        }
      } else {
        note.isEditing = true;
      }
    },

    editNote(note, position) {
      axios
        .put("http://localhost:8000/api/notes/" + note.id, {
          content: note.content,
          isDone: note.isDone
        })
        .then(() => {
          Vue.set(this.notes, position, note);

          note.isEditing = false;
          note.isLoading = false;
        })
        .catch(error => {
          console.log(error);
        });
    },

    createNote() {
      if (this.newContent && this.newContent.length > 0) {
        this.newLoading = true;

        axios
          .post("http://localhost:8000/api/notes", {
            content: this.newContent,
            isDone: false
          })
          .then(response => {
            return axios.get(
              "http://localhost:8000" + response.headers.location
            );
          })
          .then(response => {
            this.notes.push(response.data);
            this.newContent = null;
            this.newLoading = false;
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  }
};
</script>

<style>
#app {
  text-align: center;
  padding: 30px;
}

.note-text {
  cursor: pointer;
}

.done-note {
  text-decoration: line-through;
  opacity: 0.6;
}
</style>
