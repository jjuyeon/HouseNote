<template>
  <v-btn class="ml-3 mr-4" elevation="1" fab small @click="setMini(!mini)">
    <v-icon>
      {{ mini ? "mdi-format-list-bulleted" : "mdi-dots-vertical" }}
    </v-icon>
  </v-btn>
</template>

<script>
// Utilities
import { mapGetters, mapActions } from "vuex";

export default {
  name: "DefaultDrawerToggle",

  computed: {
    ...mapGetters(["mini"]),
  },
  methods: {
    ...mapActions(["setMini"]),
  },
};
</script>
