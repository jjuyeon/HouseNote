<template>
  <v-container id="bookmark-view" fluid>
    <view-intro heading="집은 어땠나요?" />

    <v-dialog v-model="dialog" max-width="500px">
      <v-card>
        <v-card-actions class="justify-center">
          <div class="my-2">
            <v-btn x-large color="green darken-1" dark @click="moveSearch"
              >상세 검색으로 이동</v-btn
            >
          </div>
        </v-card-actions>
        <v-card-actions class="justify-center">
          <div class="my-2">
            <v-btn x-large color="red darken-1" dark @click="deleteHandler"
              >관심 지역에서 삭제</v-btn
            >
          </div>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <material-card icon="mdi-clipboard-check" icon-small color="primary">
      <v-data-table
        v-if="!bookmarks"
        loading
        loading-text="NO DATA."
      ></v-data-table>
      <v-data-table
        v-else
        :headers="headers"
        :items="bookmarks"
        :items-per-page="5"
        class="bookmark-table"
        height="300px"
        @contextmenu:row="contextMenuHandler"
        @click:row="clickHandler"
      >
        ></v-data-table
      >
    </material-card>
    <div class="py-3" />
  </v-container>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import HappyHouseApi, { EndPoint, HttpMethod } from "@/constant/HappyHouseApi";
export default {
  name: "BookmarkView",
  data() {
    return {
      dialog: false,
      dialogHouseIdx: 0,
      bookmarks: [],
      headers: [
        {
          text: "아파트 이름",
          align: "start",
          sortable: false,
          value: "aptname",
        },
        { text: "주소", value: "address" },
        { text: "건축 년도", value: "buildYear" },
        { text: "등록일", value: "createdAt" },
      ],
    };
  },
  computed: {
    ...mapGetters(["houses", "userToken"]),
  },
  created() {
    // axios 유저의 관심지역 리스트 받아옴
    if (this.userToken) {
      HappyHouseApi.request({
        method: HttpMethod.GET,
        url: EndPoint.AUTH_API_BOOKMARK,
      })
        .then((res) => {
          console.log(res);
          if (res.data.isSuccess) {
            this.bookmarks = res.data.result;
            if (this.bookmarks.length === 0) {
              alert("저장한 관심 지역이 없습니다.");
            }
          }
        })
        .catch(() => {
          alert("서버 내부 오류로 관심지역 리스트를 불러올 수 없습니다.");
          this.moveHome();
        });
    } else {
      alert("로그인이 필요한 서비스입니다.");
      this.$router.push("/login");
    }
  },
  methods: {
    ...mapActions(["setHouses"]),
    clickHandler(row) {
      console.log(row);
      this.$router.push(`/note/${row.houseIdx}/`);
    },
    contextMenuHandler(event, row) {
      event.preventDefault();
      this.dialog = !this.dialog;
      this.dialogHouseIdx = row.item.houseIdx;
      // row.select(true);
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
    deleteHandler() {
      this.dialog = false;
      const deleteIdx = this.searchHousesIdx(this.dialogHouseIdx);
      if (deleteIdx === -1) {
        alert("북마크 삭제에 실패했습니다.");
        return;
      }
      if (this.dialogHouseIdx !== 0) {
        // axios 관심지역 삭제
        HappyHouseApi.request({
          query: {
            houseIdx: this.dialogHouseIdx,
          },
          method: HttpMethod.DELETE,
          url: EndPoint.AUTH_API_BOOKMARK,
        })
          .then((res) => {
            console.log(res);
            if (res.data.isSuccess) {
              this.houses[deleteIdx].isBookMark = 0;
              this.setHouses(this.houses);
              alert("정상 삭제되었습니다.");
              window.location.reload();
            } else {
              alert("삭제에 실패했습니다.");
            }
          })
          .catch(() => {
            alert("서버 내부 오류로 삭제에 실패했습니다.");
          });
      }
    },
    moveHome() {
      this.$router.push("/");
    },
    moveSearch() {
      this.dialog = false;
      this.$router.push("/detail/" + this.dialogHouseIdx);
    },
  },
};
</script>