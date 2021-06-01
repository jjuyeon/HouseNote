<template>
  <v-container>
    <v-row justify="center">
      <v-col v-if="noData" cols="4">
        <v-card class="mx-auto" max-width="500" height="200">
          <v-list>
            <v-list-item style="color: gray !important">
              <v-list-item-content>
                <v-list-item-title
                  >해당 지역의 데이터가 존재하지 않습니다</v-list-item-title
                >
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card>
      </v-col>
      <v-col v-else cols="4">
        <v-card class="mx-auto" max-width="500">
          <v-list two-line>
            <v-list-item-group v-model="selected">
              <template v-for="(house, index) in houses">
                <v-list-item :key="house.idx">
                  <template>
                    <v-list-item-content @click="moveHandler(house.idx)">
                      <v-list-item-title
                        v-text="house.aptname"
                      ></v-list-item-title>

                      <v-list-item-subtitle
                        class="text--primary"
                        v-text="house.address"
                      ></v-list-item-subtitle>

                      <v-list-item-subtitle>
                        건축년도: {{ house.buildYear }}년</v-list-item-subtitle
                      >
                    </v-list-item-content>
                    <v-list-item-action>
                      <v-btn
                        v-if="house.isBookMark == 0"
                        icon
                        @click.prevent="addBookmark(index)"
                      >
                        <v-icon color="grey lighten-1">mdi-star-outline</v-icon>
                      </v-btn>
                      <v-btn v-else icon @click="deleteBookmark(index)">
                        <v-icon color="primary lighten-1">mdi-star</v-icon>
                      </v-btn>
                    </v-list-item-action>
                  </template>
                </v-list-item>

                <v-divider
                  v-if="index < houses.length - 1"
                  :key="index"
                ></v-divider>
              </template>
            </v-list-item-group>
          </v-list>
        </v-card>
        <div class="text-center">
          <v-pagination
            v-model="pageNavigation.currentPage"
            :length="pageNavigation.totalPageCount"
            :total-visible="10"
            @input="next"
            prev-icon="mdi-menu-left"
            next-icon="mdi-menu-right"
          ></v-pagination>
        </div>
      </v-col>
      <v-col cols="8">
        <div id="map" style="height: 500px"></div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import HappyHouseApi, { EndPoint, HttpMethod } from "../constant/HappyHouseApi";
import { mapActions, mapGetters } from "vuex";
export default {
  name: "HouseSearchMap",
  props: {
    result: Array,
  },
  data() {
    return {
      noData: true,
      map: null,
      markers: [],
      places: [],
      selected: "",
    };
  },
  computed: {
    ...mapGetters(["houses", "pageNavigation", "userToken"]),
  },
  watch: {
    houses: function (newVal, oldVal) {
      if (this.houses.length === 0) {
        this.noData = true;
      } else {
        this.noData = false;
      }
      this.displayMarker(this.houses);
    },
  },
  created() {
    if (this.houses.length !== 0) {
      this.noData = false;
    }
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_API_KEY}&libraries=services`;
      document.head.appendChild(script);
    }
  },
  methods: {
    ...mapActions(["setHouses", "setPage", "setPageNavigation"]),
    initMap() {
      const containter = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      this.map = new kakao.maps.Map(containter, options);
      if (this.$store.state.houses.length > 0) {
        this.displayMarker(this.$store.state.houses);
      }
    },
    // 맵에 마커를 표시해주는 함수
    displayMarker(houses) {
      console.log(houses);
      // console.log(page);
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = houses.map(
        (house) => new kakao.maps.LatLng(house.lat, house.lng)
      );

      if (positions.length > 0) {
        this.markers = houses.map((house) => {
          const marker = new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(house.lat, house.lng),
          });
          const infowindow = new kakao.maps.InfoWindow({
            content: house.aptname,
          });

          kakao.maps.event.addListener(marker, "mouseover", () => {
            infowindow.open(this.map, marker);
          });

          kakao.maps.event.addListener(marker, "mouseover", () => {
            infowindow.open(this.map, marker);
          });

          kakao.maps.event.addListener(marker, "mouseout", () => {
            infowindow.close();
          });

          return marker;
        });

        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    // 관심지역 등록 함수
    addBookmark(arrIdx) {
      if (this.userToken === null) {
        alert("로그인이 필요한 서비스입니다.");
        this.$router.push("/login");
        return;
      }
      HappyHouseApi.request({
        data: { houseIdx: this.houses[arrIdx].idx },
        method: HttpMethod.POST,
        url: EndPoint.AUTH_API_BOOKMARK,
      })
        .then((res) => {
          // 결과 return
          if (res.data.code === 200) {
            this.houses[arrIdx].isBookMark = 1;
            this.setHouses(this.houses);
            alert(res.data.message);
          }
        })
        .catch(() => {
          alert("서버 내부 오류로 정보를 불러오는데 실패했습니다.");
        });
    },
    // 관심지역 삭제 함수
    deleteBookmark(arrIdx) {
      HappyHouseApi.request({
        query: { houseIdx: this.houses[arrIdx].idx },
        method: HttpMethod.DELETE,
        url: EndPoint.AUTH_API_BOOKMARK,
      })
        .then((res) => {
          // 결과 return
          if (res.data.code === 200) {
            this.houses[arrIdx].isBookMark = 0;
            this.setHouses(this.houses);
            alert(res.data.message);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    moveHandler(houseIdx) {
      this.$router.push("/detail/" + houseIdx).catch(() => {});
    },
    next(page) {
      HappyHouseApi.request({
        query: {
          dong: this.$store.state.dong.dong,
          dongcode: this.$store.state.dong.dongcode,
          pg: page,
        },
        method: HttpMethod.GET,
        url: EndPoint.AUTH_API_HOUSE,
      })
        .then((res) => {
          // 결과 return
          if (res.data.code === 200) {
            this.setHouses(res.data.result);
            this.setPageNavigation(res.data.pageNavigation);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>
<style scoped>
</style>
