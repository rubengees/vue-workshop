import Reveal from "reveal.js";
import hljs from "highlight.js";

import "reveal.js/css/reveal.css";
import "reveal.js/css/theme/white.css";
import "highlight.js/styles/github-gist.css";

import Vue from "vue/dist/vue.esm";

import "./index.css";

Reveal.initialize({
  center: false,
  history: true
});

hljs.initHighlightingOnLoad();

window.app1 = new Vue({
  el: "#app-1",
  data: {
    message: "Hallo Vue!"
  }
});

Vue.component("todo-item", {
  props: ["todo"],
  template: "<li>{{ todo.text }}</li>"
});

window.app2 = new Vue({
  el: "#app-2",
  data: {
    todos: [
      { text: "Write Code" },
      { text: "Test Code" },
      { text: "Deploy Code" }
    ]
  }
});

