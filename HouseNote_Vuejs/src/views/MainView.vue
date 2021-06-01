<template>
  <v-container id="main-view" fluid>
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
    <address-selector></address-selector>
    <house-search-map></house-search-map>
  </v-container>
</template>

<script>
import AddressSelector from "../components/AddressSelector";
import HouseSearchMap from "../components/HouseSearchMap";
export default {
  name: "MainView",
  components: { HouseSearchMap, AddressSelector },
};
</script>
