<template>
  <v-container>
    <v-row align="center" justify="center" style="margin-top: 5%">
      <v-col cols="6">
        <v-img contain src="@/assets/signup.jpg"></v-img>
      </v-col>
      <v-col cols="6">
        <div class="text-h1" style="margin-bottom: 5%; text-align: center">
          L O G I N
        </div>
        <v-img
          src="@/assets/kakao_login_large_wide.png"
          @click="onClickLogin"
        ></v-img>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import HappyHouseApi, { EndPoint, HttpMethod } from "@/constant/HappyHouseApi";
import { mapActions } from "vuex";
export default {
  name: "LoginKakao",

  methods: {
    ...mapActions(["setUserToken"]),
    onClickLogin() {
      window.Kakao.Auth.login({
        success: (response) => {
          console.log(response);
          console.log(response.access_token);
          HappyHouseApi.request({
            data: {
              access_token: response.access_token,
            },
            method: HttpMethod.POST,
            url: EndPoint.AUTH_API_KAKAOLOGIN,
          })
            .then((res) => {
              // 결과 return
              console.log(res);
              this.setUserToken(res.data.jwt);
              console.log("login finish: " + this.$store.getters.userToken);
            })
            .catch(function (error) {
              console.log(error);
            });
          this.$router.push("/");
        },
      });
    },
  },
};
</script>

<style scoped></style>
