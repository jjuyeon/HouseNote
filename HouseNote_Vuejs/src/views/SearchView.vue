<template>
  <v-container id="search-view" fluid>
    <v-row>
      <v-img
        src="@/assets/mainbg1.jpg"
        position="center center"
        width="100%"
        height="440px"
      ></v-img>
    </v-row>
    <v-row align="center" justify="center">
      <div class="text-h2" style="margin: 50px 0">
        당신의 Happy House를 찾아드립니다.<br />검색하고자 하는 지역을
        선택하세요.
      </div>
    </v-row>
    <house-detail-map :houseIdx="houseIdx"></house-detail-map>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import HouseDetailMap from "../components/HouseDetailMap.vue";
export default {
  name: "SearchView",
  components: { HouseDetailMap },
  data() {
    return {
      houseIdx: null,
    };
  },
  computed: {
    ...mapGetters(["userToken"]),
  },
  created() {
    this.houseIdx = this.$route.params.houseIdx;
    console.log(this.houseIdx);
  },
};
</script>
