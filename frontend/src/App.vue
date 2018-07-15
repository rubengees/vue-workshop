<template>
  <div id="app">
    <h1>Smart Notes</h1>

    <div class="container mt-4">
      <div class="row justify-content-center">
        <b-list-group class="col-8 col-offset-2">

          <!--TODO: Iterate through notes.-->
          <b-list-group-item class="d-flex">

            <!--TODO: Set class based on done state.-->
            <!--TODO: Click listener for toggling the done state.-->
            <span class="note-text align-self-center mr-auto">
              TODO: Show content of individual note in list
            </span>
          </b-list-group-item>

          <b-list-group-item class="d-flex">
            <!--TODO: Save input for new note.-->
            <b-form-input type="text" class="mr-auto" placeholder="Neue Notiz..."></b-form-input>

            <b-button variant="primary" class="align-self-center ml-4" @click="createNote()">
              <i class="fa fa-save"></i>
            </b-button>
          </b-list-group-item>
        </b-list-group>
      </div>
    </div>
  </div>
</template>

<script>
// TODO: Remove to enable lint.
/* eslint-disable */

import axios from "axios";

export default {
  data() {
    return {
      notes: []
      // TODO: Property for text of new note input.
    };
  },

  mounted() {
    // TODO: Retrieve notes and show.
  },

  methods: {
    toggleDone(note) {
      // TODO: Update note on the backend and show updated note in the list.
    },

    createNote() {
      // TODO: Create note on the backend and add to the list.
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
