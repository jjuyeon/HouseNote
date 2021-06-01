<template>
  <v-menu
    bottom
    left
    min-width="200"
    offset-y
    origin="top right"
    transition="scale-transition"
  >
    <template v-slot:activator="{ attrs, on }">
      <v-btn class="ml-2" min-width="0" text v-bind="attrs" v-on="on">
        <v-icon>mdi-account</v-icon>
      </v-btn>
    </template>

    <v-list v-if="!userToken" :tile="false" flat nav>
      <template v-for="(b, i) in beforeLogin">
        <v-list-item :key="`item-${i}`" :to="b.path">
          <v-list-item-title v-text="b.title" />
        </v-list-item>
      </template>
    </v-list>

    <v-list v-else :tile="false" flat nav>
      <template v-for="(b, i) in afterLogin">
        <v-list-item
          v-if="b.title === 'Log out'"
          :key="`item-${i}`"
          @click="logoutHandler"
        >
          <v-list-item-title v-text="b.title" />
        </v-list-item>
        <v-list-item v-else :key="`item-${i}`" :to="b.path">
          <v-list-item-title v-text="b.title" />
        </v-list-item>
      </template>
    </v-list>
  </v-menu>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: "DefaultAccount",
  data: () => ({
    beforeLogin: [{ title: "Log in", path: "/login" }],
    afterLogin: [{ title: "Log out" }, { title: "My Page", path: "/mypage" }],
  }),
  computed: {
    ...mapGetters(["userToken"]),
  },
  methods: {
    ...mapActions(["init"]),
    logoutHandler() {
      this.init();
      this.$router.push("/");
      alert("로그아웃 되었습니다.");
      window.location.reload();
    },
  },
};
</script>
