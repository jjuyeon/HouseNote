import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userToken: null,
    drawer: null,
    mini: false,
    rtl: false,
    sido: "",
    gugun: "",
    dong: "",
    sidos: [],
    guguns: [],
    dongs: [],
    houses: [],
    pageNavigation: "",
    detailPageNavigation: "",
  },
  getters: {
    userToken(state) {
      return state.userToken;
    },
    drawer(state) {
      return state.drawer;
    },
    mini(state) {
      return state.mini;
    },
    sido(state) {
      return state.sido;
    },
    gugun(state) {
      return state.gugun;
    },
    dong(state) {
      return state.dong;
    },
    sidos(state) {
      return state.sidos;
    },
    guguns(state) {
      return state.guguns;
    },
    dongs(state) {
      return state.dongs;
    },
    houses(state) {
      return state.houses;
    },
    pageNavigation(state) {
      return state.pageNavigation;
    },
    detailPageNavigation(state) {
      return state.detailPageNavigation;
    },
  },
  mutations: {
    SET_USER_TOKEN(state, payload) {
      state.userToken = payload;
    },
    SET_DRAWER(state, payload) {
      state.drawer = payload;
    },
    SET_MINI(state, payload) {
      state.mini = payload;
    },
    SET_SIDO(state, payload) {
      state.sido = payload;
    },
    SET_GUGUN(state, payload) {
      state.gugun = payload;
    },
    SET_DONG(state, payload) {
      state.dong = payload;
    },
    SET_SIDOS(state, payload) {
      state.sidos = payload;
    },
    SET_GUGUNS(state, payload) {
      state.guguns = payload;
    },
    SET_DONGS(state, payload) {
      state.dongs = payload;
    },
    SET_HOUSES(state, payload) {
      state.houses = payload;
    },
    INIT(state) {
      state.userToken = null;
      state.drawer = null;
      state.mini = false;
      state.rtl = false;
      state.sido = "";
      state.gugun = "";
      state.dong = "";
      state.sidos = [];
      state.guguns = [];
      state.dongs = [];
      state.houses = [];
      state.pageNavigation = "";
      state.detailPageNavigation = "";
    },
    SET_PAGENAVIGATION(state, payload) {
      state.pageNavigation = payload;
    },
    SET_DETAILPAGENAVIGATION(state, payload) {
      state.detailPageNavigation = payload;
    },
  },
  actions: {
    setUserToken({ commit }, payload) {
      commit("SET_USER_TOKEN", payload);
    },
    setDrawer({ commit }, payload) {
      commit("SET_DRAWER", payload);
    },
    setMini({ commit }, payload) {
      commit("SET_MINI", payload);
    },
    setSido({ commit }, payload) {
      commit("SET_SIDO", payload);
    },
    setGugun({ commit }, payload) {
      commit("SET_GUGUN", payload);
    },
    setDong({ commit }, payload) {
      commit("SET_DONG", payload);
    },
    setSidos({ commit }, payload) {
      commit("SET_SIDOS", payload);
    },
    setGuguns({ commit }, payload) {
      commit("SET_GUGUNS", payload);
    },
    setDongs({ commit }, payload) {
      commit("SET_DONGS", payload);
    },
    setHouses({ commit }, payload) {
      commit("SET_HOUSES", payload);
    },
    init({ commit }) {
      commit("INIT");
    },
    setPageNavigation({ commit }, payload) {
      commit("SET_PAGENAVIGATION", payload);
    },
    setDetailPageNavigation({ commit }, payload) {
      commit("SET_DETAILPAGENAVIGATION", payload);
    },
  },
  modules: {},
  plugins: [createPersistedState()],
});
