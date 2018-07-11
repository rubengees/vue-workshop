import Vue from "vue";
import BootstrapVue from "bootstrap-vue";

import App from "./App.vue";

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "font-awesome/css/font-awesome.min.css";

Vue.use(BootstrapVue);

new Vue({
  render: h => h(App)
}).$mount("#app");
