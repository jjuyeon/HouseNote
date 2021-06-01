import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import "./plugins";
import store from "./store";
import { sync } from "vuex-router-sync";

Vue.config.productionTip = false;

sync(store, router);

// 반드시 VUE_APP으로 시작해야 한다.
// const SERVICE_KEY = process.env.VUE_APP_KAKAO_API_KEY;
window.Kakao.init(process.env.VUE_APP_KAKAO_API_KEY);

new Vue({
  router,
  vuetify,
  store,
  render: (h) => h(App),
}).$mount("#app");
