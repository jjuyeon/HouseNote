<template>
  <v-container>
    <v-row>
      <v-col cols="10">
        <v-select
          v-model="selectedFilter"
          :items="filters"
          item-text="item"
          item-value="value"
          return-object
          attach
          chips
          label="필터 검색"
          multiple
        ></v-select>
      </v-col>
      <v-col cols="1">
        <v-btn color="primary" class="ma-2" dark @click="filterHandler">
          필터검색
        </v-btn>
      </v-col>
      <v-col cols="1">
        <v-btn color="primary" class="ma-2" dark @click="clearHandler">
          초기화
        </v-btn>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col cols="4">
        <div class="text-h4" style="margin-bottom: 5%; text-align: center">
          {{ house.aptname }}
          <v-btn
            v-if="house.isBookMark === 0"
            icon
            @click="addBookmark(house.idx)"
          >
            <v-icon color="grey lighten-1">mdi-star-outline</v-icon>
          </v-btn>
          <v-btn v-else icon @click="deleteBookmark(house.idx)">
            <v-icon color="primary lighten-1">mdi-star</v-icon>
          </v-btn>
        </div>
        <v-card
          class="mx-auto"
          max-width="500"
          height="200"
          v-if="detailList.length === 0"
        >
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
        <v-card class="mx-auto" max-width="500" v-else>
          <v-list two-line>
            <template v-for="(detail, index) in detailList">
              <v-list-item :key="detail.idx">
                <v-list-item-content>
                  <v-list-item-title
                    >거래금액: {{ detail.dealAmount }} (만원)</v-list-item-title
                  >
                  <v-list-item-title
                    >전용면적: {{ detail.area }}</v-list-item-title
                  >
                  <v-list-item-title
                    >거래일자: {{ detail.dealYear }}.{{ detail.dealMonth }}.{{
                      detail.dealDay
                    }}</v-list-item-title
                  >
                </v-list-item-content>
              </v-list-item>
              <v-divider
                v-if="index < houses.length - 1"
                :key="index"
              ></v-divider>
            </template>
          </v-list>
        </v-card>
        <div class="text-center">
          <v-pagination
            v-model="detailPageNavigation.currentPage"
            :length="detailPageNavigation.totalPageCount"
            :total-visible="10"
            @input="next"
            prev-icon="mdi-menu-left"
            next-icon="mdi-menu-right"
          ></v-pagination>
        </div>
      </v-col>
      <v-col cols="8">
        <v-slider
          v-model="distance"
          step="1"
          label="거리를 조절해주세요"
          :max="20"
          :min="1"
          thumb-label
          ticks
          ><template v-slot:thumb-label="{ value }">
            {{ value }}00m
          </template></v-slider
        >
        <div id="map" style="height: 500px; margin-top: 7%"></div>
      </v-col>
    </v-row>
    <v-row justify="space-between">
      <div></div>
      <div>
        <v-col cols="4">
          <v-btn
            v-if="places.length !== 0"
            class="ma-2"
            tile
            outlined
            color="primary"
            style="margin-right: 3%"
            @click="overlay = !overlay"
          >
            필터된 장소 보기
          </v-btn>
        </v-col>
      </div>
    </v-row>
    <v-overlay :absolute="absolute" :dark="false" :value="overlay">
      <v-btn color="primary" @click="overlay = false"> 필터된 장소 끄기 </v-btn>
      <v-card class="mx-auto">
        <v-list two-line>
          <template v-for="(place, index) in places">
            <v-list-item :key="place.id">
              <v-list-item-content>
                <v-list-item-title
                  >장소명: {{ place.place_name }} ({{
                    place.category_group_name
                  }})</v-list-item-title
                >
                <v-list-item-title
                  >주소: {{ place.address_name }}</v-list-item-title
                >
                <v-list-item-title
                  >전화번호: {{ place.phone }}</v-list-item-title
                >
              </v-list-item-content>
              <v-list-item-action>
                <v-list-item-action-text
                  >{{ place.distance }} m</v-list-item-action-text
                >
              </v-list-item-action>
            </v-list-item>
            <v-divider v-if="index < place.length - 1" :key="index"></v-divider>
          </template>
        </v-list>
      </v-card>
      <div class="text-center">
        <v-pagination
          v-model="kakaoCurrentPage"
          :length="kakaoPageNation"
          :total-visible="5"
          @input="kakaoNext"
          prev-icon="mdi-menu-left"
          next-icon="mdi-menu-right"
        ></v-pagination>
      </div>
    </v-overlay>
  </v-container>
</template>

<script>
import HappyHouseApi, { EndPoint, HttpMethod } from "../constant/HappyHouseApi";
import { mapGetters, mapActions } from "vuex";
import Axios from "axios";
export default {
  name: "HouseDetailMap",
  props: {
    houseIdx: String,
  },
  data() {
    return {
      house: "",
      detailList: [],
      map: null,
      markers: [],
      places: [],
      filters: [
        { item: "대형마트", value: "MT1" },
        { item: "편의점", value: "CS2" },
        { item: "어린이집/유치원", value: "PS3" },
        { item: "학교", value: "SC4" },
        { item: "학원", value: "AC5" },
        { item: "주차장", value: "PK6" },
        { item: "주유소,충전소", value: "OL7" },
        { item: "지하철역", value: "SW8" },
        { item: "은행", value: "BK9" },
        { item: "문화시설", value: "CT1" },
        { item: "중개업소", value: "AG2" },
        { item: "공공기관", value: "PO3" },
        { item: "관광명소", value: "AT4" },
        { item: "숙박", value: "AD5" },
        { item: "음식점", value: "FD6" },
        { item: "카페", value: "CE7" },
        { item: "병원", value: "HP8" },
        { item: "약국", value: "PM9" },
      ],
      selectedFilter: [],
      distance: 1,
      overlay: false,
      kakaoCurrentPage: 1,
      kakaoPageNation: "",
    };
  },
  computed: {
    ...mapGetters(["houses", "userToken", "detailPageNavigation"]),
  },
  watch: {
    house: function (newVal, oldVal) {
      if (this.house.lat !== null && this.house.lng !== null) {
        if (window.kakao && window.kakao.maps) {
          this.initMap();
          this.setOverlay();
        } else {
          const script = document.createElement("script");
          /* global kakao */
          script.onload = () => kakao.maps.load(this.initMap);
          script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_API_KEY}&libraries=services`;
          document.head.appendChild(script);
        }
      }
    },
  },
  methods: {
    ...mapActions(["setHouses", "setDetailPageNavigation"]),
    initMap() {
      const containter = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.house.lat, this.house.lng),
        level: 3,
      };
      this.map = new kakao.maps.Map(containter, options);
    },
    setOverlay() {
      let content = 
      '<div class="customoverlay">' +
    '  <a target="_blank">' +
    '    <span class="title">'+this.house.aptname+'</span>' +
    '  </a>' +
    '</div>';
      content += `<style>
.customoverlay {position:relative; width:100px; height:40px; background-color:#0097A7; border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}
.customoverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
.customoverlay a {display:block;text-decoration:none;color:#fff;text-align:center;font-size:14px;font-weight:bold;overflow:hidden;)}
.customoverlay .title {display:block;text-align:center;background:#0097A7;padding:10px 15px;font-size:14px;font-weight:bold;}
.customoverlay:after {color:#0097A7;content:'';position:absolute;margin-left:-12px;left:50%;bottom:-12px;width:22px;height:12px;}   
</style>`;
      const mapCustomOverlay = new kakao.maps.CustomOverlay({
        position: new kakao.maps.LatLng(this.house.lat, this.house.lng),
        content: content,
        xAnchor: 0.5, // 커스텀 오버레이의 x축 위치입니다. 1에 가까울수록 왼쪽에 위치합니다. 기본값은 0.5 입니다
        yAnchor: 1.1, // 커스텀 오버레이의 y축 위치입니다. 1에 가까울수록 위쪽에 위치합니다. 기본값은 0.5 입니다
      });
      mapCustomOverlay.setMap(this.map);
      const bounds = new kakao.maps.LatLngBounds();
      bounds.extend(new kakao.maps.LatLng(this.house.lat, this.house.lng));

      this.map.setBounds(bounds);
    },
    searchHousesIdx(houseIdx) {
      let result = -1;
      for (let i = 0; i < this.houses.length; i++) {
        if (this.houses[i].idx === houseIdx) {
          result = i;
          break;
        }
      }
      return result;
    },
    addBookmark(houseIdx) {
      const addIdx = this.searchHousesIdx(houseIdx);
      if (addIdx === -1) {
        alert("북마크 등록에 실패했습니다.");
        return;
      }
      if (this.userToken === null) {
        alert("로그인이 필요한 서비스입니다.");
        this.$router.push("/login");
        return;
      }
      HappyHouseApi.request({
        data: { houseIdx: houseIdx },
        method: HttpMethod.POST,
        url: EndPoint.AUTH_API_BOOKMARK,
      })
        .then((res) => {
          console.log(res);
          // 결과 return
          if (res.data.code === 200) {
            this.house.isBookMark = this.houses[addIdx].isBookMark = 1;
            this.setHouses(this.houses);
            alert(res.data.message);
          }
        })
        .catch(() => {
          alert("서버 내부 오류로 북마크 등록에 실패했습니다.");
        });
    },
    // 관심지역 삭제 함수
    deleteBookmark(houseIdx) {
      const deleteIdx = this.searchHousesIdx(houseIdx);
      if (deleteIdx === -1) {
        alert("북마크 삭제에 실패했습니다.");
        return;
      }
      HappyHouseApi.request({
        query: { houseIdx: houseIdx },
        method: HttpMethod.DELETE,
        url: EndPoint.AUTH_API_BOOKMARK,
      })
        .then((res) => {
          // 결과 return
          if (res.data.code === 200) {
            this.house.isBookMark = this.houses[deleteIdx].isBookMark = 0;
            this.setHouses(this.houses);
            alert(res.data.message);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    next(page) {
      HappyHouseApi.request({
        query: {
          houseIdx: this.houseIdx,
          pg: page,
        },
        method: HttpMethod.GET,
        url: EndPoint.AUTH_API_HOUSEDEAL,
      })
        .then((res) => {
          console.log(res);
          if (res.data.isSuccess) {
            this.detailList = res.data.result;
            this.setDetailPageNavigation(res.data.pageNavigation);
          } else {
            alert("정보를 불러오는데 실패했습니다.");
          }
        })
        .catch(() => {
          alert("서버 내부 오류로 정보를 불러오는데 실패했습니다.");
        });
    },
    kakaoNext(page) {
      this.removeMarker();
      const category = this.selectedFilter.map((data) => data.value);
      const SERVICE_URL =
        "https://dapi.kakao.com/v2/local/search/category.json";

      const params = {
        category_group_code: category.join(","),
        x: this.house.lng,
        y: this.house.lat,
        radius: this.distance * 1000,
        page: page,
        size: 5,
        sort: "distance",
      };
      Axios.get(SERVICE_URL, {
        params,
        headers: {
          Authorization: `KakaoAK f2ff9119f60eb949c0e30dd1cd5954b7`,
        },
      })
        .then((response) => {
          // console.log(response.data.response.body.items.item);
          console.log(response.data.documents);
          this.places = response.data.documents;
          this.kakaoPageNation =
            Math.floor(response.data.meta.total_count / 5) >= 9
              ? 9
              : Math.floor(response.data.meta.total_count / 5) + 1;
          console.log(this.kakaoPageNation);
          this.addMarker(this.places);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    clearHandler() {
      this.selectedFilter = [];
      this.places = [];
      this.removeMarker();
      // todo: 지도 초기화
    },
    filterHandler() {
      // console.log(this.selectedFilter); // item, value 같이 배열로 나옴
      const onlyValue = this.selectedFilter.map((data) => data.value);
      // console.log(onlyValue.join(",")); // value만 뽑아냄
      this.searchKaKaoPlaces(onlyValue);
    },
    searchKaKaoPlaces(category) {
      // this.removeMarker();
      this.setOverlay();
      console.log(category.join(","));
      const SERVICE_URL =
        "https://dapi.kakao.com/v2/local/search/category.json";

      const params = {
        category_group_code: category.join(","),
        x: this.house.lng,
        y: this.house.lat,
        radius: this.distance * 100,
        page: 1,
        size: 5,
        sort: "distance",
      };
      Axios.get(SERVICE_URL, {
        params,
        headers: {
          Authorization: `KakaoAK f2ff9119f60eb949c0e30dd1cd5954b7`,
        },
      })
        .then((response) => {
          // console.log(response.data.response.body.items.item);
          console.log(response.data.documents);
          this.places = response.data.documents;
          console.log(response.data.meta.total_count);
          this.kakaoPageNation =
            Math.floor(response.data.meta.total_count / 5) >= 9
              ? 9
              : Math.floor(response.data.meta.total_count / 5) + 1;
          console.log(this.kakaoPageNation);
          this.removeMarker();
          this.addMarker(this.places);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    addMarker(places) {
      const positions = places.map(
        (place) => new kakao.maps.LatLng(place.y, place.x)
      );

      // 커스텀 오버레이를 생성합니다
      // var customOverlay = new kakao.maps.CustomOverlay({
      //   map: null,
      //   position: new kakao.maps.LatLng(value.lat, value.lng),
      //   content: content,
      //   yAnchor: 1
      // });

      // 마커 이미지의 이미지 크기 입니다
      const imageSize = new kakao.maps.Size(30, 35);

      const imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png";

      if (positions.length > 0) {
        this.markers = places.map((place) => {
          let markerImage = null;
          switch (place.category_group_name) {
            case "대형마트":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/basket.png?alt=media&token=a09f4094-647c-426b-98f2-8c20a0c9abca",
                imageSize
              );
              break;
            case "편의점":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/store-24-hour.png?alt=media&token=07b9320e-53bb-449b-9f14-6254413e7f9f",
                imageSize
              );
              break;
            case "어린이집,유치원":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/human-child.png?alt=media&token=3100d224-715f-4f0d-95e0-98a249e167bf",
                imageSize
              );
              break;
            case "학교":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/school.png?alt=media&token=82cb946e-05c1-49e2-ad0e-17bf5d1719ce",
                imageSize
              );
              break;
            case "학원":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/lead-pencil.png?alt=media&token=d84b77a4-6408-4d0d-84be-a47e9f6d6130",
                imageSize
              );
              break;
            case "주차장":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/parking.png?alt=media&token=d175b44b-b4d6-4f12-bc66-7e6d3d7c4b35",
                imageSize
              );
              break;
            case "주유소, 충전소":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/gas-station.png?alt=media&token=0dbf0c0a-4222-46d9-b37e-b2cdae7da6db",
                imageSize
              );
              break;
            case "지하철역":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/subway-variant.png?alt=media&token=7836bce1-5100-409b-803f-ed46067adbb0",
                imageSize
              );
              break;
            case "은행":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/bank.png?alt=media&token=861d5cce-9c33-45e3-a557-29258891a50f",
                imageSize
              );
              break;
            case "문화시설":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/library.png?alt=media&token=bb5ecdb0-00c5-43f9-a64e-e51483fa40b3",
                imageSize
              );
              break;
            case "중개업소":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/account-supervisor.png?alt=media&token=2956783c-3c89-4329-8ba4-150b0ca1d7b2",
                imageSize
              );
              break;
            case "공공기관":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/handshake-outline.png?alt=media&token=b890a0de-d8ae-4f33-8e47-08d2501fa05f",
                imageSize
              );
              break;
            case "관광명소":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/camera.png?alt=media&token=4754c17f-3740-4163-8a6e-d1fe956e60ab",
                imageSize
              );
              break;
            case "숙박":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/home-city.png?alt=media&token=3a2d9c41-ba15-4e6b-a986-cb5e2b1eebe6",
                imageSize
              );
              break;
            case "음식점":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/account-supervisor.png?alt=media&token=2956783c-3c89-4329-8ba4-150b0ca1d7b2",
                imageSize
              );
              break;
            case "카페":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/coffee.png?alt=media&token=f8f54564-9b16-4dd0-b20a-a8e11843f679",
                imageSize
              );
              break;
            case "병원":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/hospital-box-outline.png?alt=media&token=43ef1d5a-a0a3-49d4-9d43-3d1a6ef0c334",
                imageSize
              );
              break;
            case "약국":
              markerImage = new kakao.maps.MarkerImage(
                "https://firebasestorage.googleapis.com/v0/b/happyhouse-309508.appspot.com/o/mortar-pestle-plus.png?alt=media&token=70774bc6-f956-4b8a-ad32-77e716c19d78",
                imageSize
              );
              break;
          }
          const marker = new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(place.y, place.x),
            image: markerImage,
          });

          const infowindow = new kakao.maps.InfoWindow({
            content: place.place_name,
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
        console.log(this.markers);
        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    removeMarker() {
      this.markers.forEach((e) => e.setMap(null));
    },
    moveHome() {
      this.$router.push("/");
    },
  },
  created() {
    HappyHouseApi.request({
      path: { id: this.houseIdx },
      method: HttpMethod.GET,
      url: EndPoint.AUTH_API_HOUSE,
    })
      .then((res) => {
        console.log(res);
        if (res.data.isSuccess) {
          this.house = res.data.result;
          console.log(this.house);
        } else {
          alert("정보를 불러오는데 실패했습니다.");
        }
      })
      .catch(() => {
        alert("서버 내부 오류로 정보를 불러오는데 실패했습니다.");
      });
    HappyHouseApi.request({
      query: {
        houseIdx: this.houseIdx,
        pg: 1,
      },
      method: HttpMethod.GET,
      url: EndPoint.AUTH_API_HOUSEDEAL,
    })
      .then((res) => {
        console.log(res);
        if (res.data.isSuccess) {
          this.detailList = res.data.result;
          this.setDetailPageNavigation(res.data.pageNavigation);
        } else {
          alert("정보를 불러오는데 실패했습니다.");
        }
      })
      .catch(() => {
        alert("서버 내부 오류로 정보를 불러오는데 실패했습니다.");
      });
  },
};
</script>
<style scoped>
</style>