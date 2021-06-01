<template>
  <v-navigation-drawer
    id="default-drawer"
    v-model="drawer"
    :right="$vuetify.rtl"
    :mini-variant.sync="mini"
    mini-variant-width="80"
    app
    width="260"
  >
    <div class="px-2">
      <default-drawer-header />

      <v-divider class="mx-3 mb-2" />

      <default-list :items="items" />
    </div>

    <div class="pt-12" />
  </v-navigation-drawer>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "DefaultDrawer",
  data() {
    return {
      items: [
        {
          title: "매물 검색",
          icon: "mdi-home-city",
          to: "/",
        },
        {
          title: "관심 지역",
          icon: "mdi-emoticon-cool-outline",
          to: "/bookmark",
        },
      ],
    };
  },
  computed: {
    ...mapGetters(["mini"]),
    drawer: {
      get() {
        return this.$store.state.drawer;
      },
      set(payload) {
        this.$store.dispatch("setDrawer", payload);
      },
    },
  },
  components: {
    DefaultDrawerHeader: () =>
      import(
        /* webpackChunkName: "default-drawer-header" */
        "./widgets/DrawerHeader"
      ),
    DefaultList: () =>
      import(
        /* webpackChunkName: "default-list" */
        "./List"
      ),
  },
};
</script>

<style lang="sass">
#default-drawer
  .v-list-item
    margin-bottom: 8px

  .v-list-item::before,
  .v-list-item::after
    display: none

  .v-list-group__header__prepend-icon,
  .v-list-item__icon
    margin-top: 12px
    margin-bottom: 12px
    margin-left: 4px

  &.v-navigation-drawer--mini-variant
    .v-list-item
      justify-content: flex-start !important
</style>
