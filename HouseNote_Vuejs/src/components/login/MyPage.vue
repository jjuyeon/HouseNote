<template>
  <v-container>
    <v-row align="center" justify="center" style="margin-top: 5%">
      <v-card class="mx-auto" max-width="500">
        <v-img :src="userinfo.image" :aspect-ratio="1"> </v-img>
        <v-card-title v-model="userinfo">
          <div class="text-h3 mb-2" style="margin-right: 3%">
            {{ userinfo.name }}
          </div>
          <div class="text-h4 font-weight-regular grey--text">
            가입일: {{ userinfo.createdAt }}
          </div>
        </v-card-title>
        <v-row
          class="text-h5 px-4 grey--text"
          align="center"
          v-model="userinfo"
        >
          이메일: {{ userinfo.email }}
        </v-row>

        <v-divider class="mt-6 mx-4"></v-divider>

        <v-card-text>
          <v-img
            src="@/assets/kakao_unlink_large_wide.png"
            @click="unlinkApp"
          ></v-img>
        </v-card-text>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
import HappyHouseApi, { EndPoint, HttpMethod } from "@/constant/HappyHouseApi";
import { mapGetters, mapActions } from "vuex";
export default {
  name: "MyPage",
  data() {
    return {
      userinfo: "",
    };
  },
  computed: {
    ...mapGetters(["userToken"]),
  },
  created() {
    if (this.userToken) {
      // 토큰검증
      HappyHouseApi.request({
        query: { access_token: this.userToken },
        method: HttpMethod.GET,
        url: EndPoint.AUTH_API_LOGINTOKEN,
      })
        .then((res) => {
          // 결과 return
          console.log(res);
          this.userinfo = {
            idx: res.data.idx,
            name: res.data.name,
            email: res.data.email,
            image: res.data.image,
            createdAt: res.data.createdAt,
          };
        })
        .catch(function (error) {
          console.log(error);
        });
    } else {
      alert("로그인이 필요한 서비스입니다.");
      this.$router.push("/login").catch(() => {});
    }
  },
  methods: {
    ...mapActions(["init"]),
    unlinkApp() {
      if (this.userinfo.idx === "null") {
        alert("탈퇴에 실패했습니다.");
        return;
      }
      HappyHouseApi.request({
        query: {
          userIdx: this.userinfo.idx,
        },
        method: HttpMethod.DELETE,
        url: EndPoint.AUTH_API_KAKAOUNLINK,
      })
        .then((res) => {
          console.log(res);
          if (res.data.isSuccess) {
            window.Kakao.API.request({
              url: "/v1/user/unlink",
              success: (response) => {
                console.log(response);
                this.init();
                alert("정상 탈퇴되었습니다.");
              },
              fail: function (error) {
                console.log(error);
                alert("탈퇴에 실패했습니다.");
              },
            });
          } else {
            alert("탈퇴에 실패했습니다.");
          }
        })
        .catch(() => {
          alert("서버 내부 오류로 삭제에 실패했습니다.");
        });
      this.$router.push("/");
    },
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
    alarm() {
      alert("Turning on alarm...");
    },
    blinds() {
      alert("Toggling Blinds...");
    },
    lights() {
      alert("Toggling lights...");
    },
  },
};
</script>

<style scoped></style>
