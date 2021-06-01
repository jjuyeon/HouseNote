<template>
  <v-container id="note-view" fluid>
    <v-row justify="space-between">
      <div class="detail-header" @click="moveList">
        <v-icon> mdi-arrow-left </v-icon>
        <div style="margin: 0 10px">{{ house.aptname }}</div>
        <v-list>
          <v-list-item-subtitle
            >{{ house.address }} ({{ house.buildYear }}년)</v-list-item-subtitle
          >
        </v-list>
      </div>
      <a href="javascript:;" class="kakao-link"> </a>
      <div>
        <v-btn
          class="ma-2"
          tile
          outlined
          color="primary"
          style="margin-right: 3%"
          @click="share"
        >
          <v-img
            left
            style="margin-right: 20%"
            src="@/assets/kakaolink_btn_small.png"
          ></v-img>
          Share
        </v-btn>
        <v-btn
          class="ma-2"
          tile
          outlined
          color="primary"
          style="margin-right: 3%"
          @click="updateHandler"
        >
          <v-icon left>mdi-pencil</v-icon> Edit
        </v-btn>
      </div>
    </v-row>
    <v-row v-if="loading" justify="center" style="text-align: center"
      >Loading... Please wait</v-row
    >
    <div v-else>
      <v-row>
        <v-col cols="6">
          <v-list subheader>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-subtitle v-if="note.updatedAt === null"
                  >수정일: {{ note.createdAt }}</v-list-item-subtitle
                >
                <v-list-item-subtitle v-else
                  >수정일: {{ note.updatedAt }}</v-list-item-subtitle
                >
                <v-list-item-subtitle>
                  <v-rating
                    v-model="star"
                    readonly
                    background-color="orange lighten-3"
                    color="orange"
                    half-increments
                    large
                  ></v-rating>
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-textarea
            v-model="note.content"
            clearable
            clear-icon="mdi-close-circle"
            background-color="teal lighten-5"
            label="간단 메모"
          ></v-textarea>
        </v-col>
        <v-col cols="6">
          <div id="roadview" style="height: 300px"></div>
        </v-col>
      </v-row>
      <v-row>
        <v-btn color="primary" class="ma-2" dark @click="dialog1 = true">
          방
        </v-btn>
        <v-btn color="primary" class="ma-2" dark @click="dialog2 = true">
          욕실
        </v-btn>
        <v-btn color="primary" class="ma-2" dark @click="checkHandler(3)">
          주방
        </v-btn>
        <v-btn color="primary" class="ma-2" dark @click="checkHandler(4)">
          시설
        </v-btn>
      </v-row>
      <v-row>
        <v-col>
          <v-simple-table height="300px">
            <template v-slot:default>
              <tbody>
                <tr
                  v-for="(info, index) in note.roomList"
                  :key="index"
                  @click="clickHandler(info, index)"
                >
                  <template v-if="info.status !== 5">
                    <td>
                      <v-icon v-if="info.status === 1"> mdi-bed </v-icon>
                      <v-icon v-if="info.status === 2"> mdi-paper-roll </v-icon>
                      <v-icon v-if="info.status === 3">
                        mdi-fridge-outline
                      </v-icon>
                      <v-icon v-if="info.status === 4">
                        mdi-home-variant-outline
                      </v-icon>
                      {{ info.name }}
                    </td>
                    <td v-for="cindex in 7" :key="cindex + '-item&score'">
                      {{ info.item[cindex - 1] }}
                      <template
                        v-if="cindex <= 5 || (info.status == 1 && cindex == 6)"
                      >
                        <v-icon v-if="info.status === 1"></v-icon>
                        <v-icon
                          v-if="info.score[cindex - 1] === 5"
                          color="green"
                          large
                        >
                          mdi-numeric-5-circle
                        </v-icon>
                        <v-icon
                          v-if="info.score[cindex - 1] === 4"
                          color="blue"
                          large
                        >
                          mdi-numeric-4-circle
                        </v-icon>
                        <v-icon
                          v-if="info.score[cindex - 1] === 3"
                          color="yellow"
                          large
                        >
                          mdi-numeric-3-circle
                        </v-icon>
                        <v-icon
                          v-if="info.score[cindex - 1] === 2"
                          color="orange"
                          large
                        >
                          mdi-numeric-2-circle
                        </v-icon>
                        <v-icon
                          v-if="info.score[cindex - 1] === 1"
                          color="red"
                          large
                        >
                          mdi-numeric-1-circle
                        </v-icon>
                      </template>
                      <template v-else-if="info.status == 1 && cindex == 7">
                        <v-icon v-if="info.score[cindex - 1] == 0" large>
                          mdi-window-close
                        </v-icon>
                        <v-icon v-else-if="info.score[cindex - 1] == 1" large>
                          mdi-window-closed-variant
                        </v-icon>
                        <v-icon v-else-if="info.score[cindex - 1] == 2" large>
                          mdi-numeric-2-box-multiple-outline
                        </v-icon>
                      </template>
                      <template v-else-if="info.status == 2 && cindex == 6">
                        <v-icon v-if="info.score[cindex - 1] == 0" large>
                          mdi-window-close
                        </v-icon>
                        <v-icon v-else-if="info.score[cindex - 1] == 1" large>
                          mdi-shower-head
                        </v-icon>
                        <v-icon v-else-if="info.score[cindex - 1] == 2" large>
                          mdi-shower
                        </v-icon>
                      </template>
                      <template v-else-if="info.status == 4 && cindex == 6">
                        <v-icon v-if="info.score[cindex - 1] == 0" large>
                          mdi-window-close
                        </v-icon>
                        <v-icon v-else-if="info.score[cindex - 1] == 1" large>
                          mdi-check-circle-outline
                        </v-icon>
                      </template>
                    </td>
                    <td>
                      <v-icon
                        large
                        color="primary"
                        @click="removeHandler(index)"
                        >mdi-backspace</v-icon
                      >
                    </td>
                  </template>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-col>
      </v-row>
    </div>

    <v-dialog
      v-model="dialog1"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
      scrollable
      @keydown.esc="(dialog1 = false), resetHandler(1)"
    >
      <v-card tile>
        <v-toolbar flat dark color="primary">
          <v-btn icon dark @click="(dialog1 = false), resetHandler(1)">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>방 상태 기록</v-toolbar-title>
        </v-toolbar>
        <v-card-text>
          <v-list three-line subheader>
            <v-list-item>
              <v-list-item-content>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="room[0]"
                    label="방 이름"
                    :rules="rules"
                    hide-details="auto"
                  ></v-text-field>
                </v-col>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
          <v-list three-line subheader>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>환기</v-list-item-title>
                <v-list-item-subtitle>방 환기는 잘되나요?</v-list-item-subtitle>
                <v-rating
                  v-model="room[1]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>도배</v-list-item-title>
                <v-list-item-subtitle>방 도배는 어떤가요?</v-list-item-subtitle>
                <v-rating
                  v-model="room[2]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>천장</v-list-item-title>
                <v-list-item-subtitle>방 천장은 어떤가요?</v-list-item-subtitle>
                <v-rating
                  v-model="room[3]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>조명/채광</v-list-item-title>
                <v-list-item-subtitle
                  >방 조명/채광은 어떤가요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="room[4]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>바닥</v-list-item-title>
                <v-list-item-subtitle>방 바닥은 어떤가요?</v-list-item-subtitle>
                <v-rating
                  v-model="room[5]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>방음</v-list-item-title>
                <v-list-item-subtitle>방음은 잘되나요?</v-list-item-subtitle>
                <v-rating
                  v-model="room[6]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>창문</v-list-item-title>
                <v-list-item-subtitle>창문은 몇 개인가요?</v-list-item-subtitle>
                <v-radio-group v-model="room[7]" row>
                  <v-radio label="없음" value="0" color="indigo"></v-radio>
                  <v-radio label="1개" value="1" color="indigo"></v-radio>
                  <v-radio label="2개 이상" value="2" color="indigo"></v-radio>
                </v-radio-group>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card-text>
        <div style="flex: 1 1 auto"></div>

        <v-card-actions class="justify-center">
          <div class="my-2">
            <v-btn color="primary" dark class="ma-2" @click="addHandler(1)">
              확인
            </v-btn>
            <v-btn color="primary" dark class="ma-2" @click="resetHandler(1)">
              초기화
            </v-btn>
          </div>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog
      v-model="dialog2"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
      scrollable
      @keydown.esc="(dialog2 = false), resetHandler(2)"
    >
      <v-card tile>
        <v-toolbar flat dark color="primary">
          <v-btn icon dark @click="(dialog2 = false), resetHandler(2)">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>욕실 상태 기록</v-toolbar-title>
        </v-toolbar>
        <v-card-text>
          <v-list three-line subheader>
            <v-list-item>
              <v-list-item-content>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="bath[0]"
                    label="욕실 이름"
                    :rules="rules"
                    hide-details="auto"
                  ></v-text-field>
                </v-col>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
          <v-list three-line subheader>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>환기</v-list-item-title>
                <v-list-item-subtitle
                  >욕실 환기는 잘되나요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="bath[1]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>배수</v-list-item-title>
                <v-list-item-subtitle
                  >욕실 배수는 어떤가요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="bath[2]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>수압</v-list-item-title>
                <v-list-item-subtitle
                  >욕실 수압은 어떤가요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="bath[3]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>타일</v-list-item-title>
                <v-list-item-subtitle
                  >욕실 타일은 어떤가요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="bath[4]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>조명</v-list-item-title>
                <v-list-item-subtitle
                  >욕실 조명은 어떤가요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="bath[5]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>목욕시설</v-list-item-title>
                <v-list-item-subtitle
                  >샤워부스 또는 욕조가 있나요?</v-list-item-subtitle
                >
                <v-radio-group v-model="bath[6]" row>
                  <v-radio label="없음" value="0" color="indigo"></v-radio>
                  <v-radio label="샤워부스" value="1" color="indigo"></v-radio>
                  <v-radio label="욕조" value="2" color="indigo"></v-radio>
                </v-radio-group>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card-text>
        <div style="flex: 1 1 auto"></div>

        <v-card-actions class="justify-center">
          <div class="my-2">
            <v-btn color="primary" dark class="ma-2" @click="addHandler(2)">
              확인
            </v-btn>
            <v-btn color="primary" dark class="ma-2" @click="resetHandler(2)">
              초기화
            </v-btn>
          </div>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog
      v-model="dialog3"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
      scrollable
      @keydown.esc="(dialog3 = false), resetHandler(3)"
    >
      <v-card tile>
        <v-toolbar flat dark color="primary">
          <v-btn icon dark @click="(dialog3 = false), resetHandler(3)">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>주방 상태 기록</v-toolbar-title>
        </v-toolbar>
        <v-card-text>
          <v-list three-line subheader>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>환기</v-list-item-title>
                <v-list-item-subtitle
                  >주방 환기는 잘되나요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="kitchen[1]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>배수</v-list-item-title>
                <v-list-item-subtitle
                  >주방 배수는 어떤가요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="kitchen[2]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>수압</v-list-item-title>
                <v-list-item-subtitle
                  >주방 수압은 어떤가요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="kitchen[3]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>타일</v-list-item-title>
                <v-list-item-subtitle
                  >주방 타일은 어떤가요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="kitchen[4]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>조명</v-list-item-title>
                <v-list-item-subtitle
                  >주방 조명은 어떤가요?</v-list-item-subtitle
                >
                <v-rating
                  v-model="kitchen[5]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card-text>
        <div style="flex: 1 1 auto"></div>

        <v-card-actions class="justify-center">
          <div class="my-2">
            <v-btn
              color="primary"
              dark
              class="ma-2"
              @click="(dialog3 = false), addHandler(3)"
            >
              확인
            </v-btn>
            <v-btn color="primary" dark class="ma-2" @click="resetHandler(3)">
              초기화
            </v-btn>
          </div>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog
      v-model="dialog4"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
      scrollable
      @keydown.esc="(dialog4 = false), resetHandler(4)"
    >
      <v-card tile>
        <v-toolbar flat dark color="primary">
          <v-btn icon dark @click="(dialog4 = false), resetHandler(4)">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>시설 상태 기록</v-toolbar-title>
        </v-toolbar>
        <v-card-text>
          <v-list three-line subheader>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>난방</v-list-item-title>
                <v-list-item-subtitle>난방은 잘되나요?</v-list-item-subtitle>
                <v-rating
                  v-model="all[1]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>온수</v-list-item-title>
                <v-list-item-subtitle>온수는 잘되나요?</v-list-item-subtitle>
                <v-rating
                  v-model="all[2]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>방충망</v-list-item-title>
                <v-list-item-subtitle>방충망은 어떤가요?</v-list-item-subtitle>
                <v-rating
                  v-model="all[3]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>콘센트</v-list-item-title>
                <v-list-item-subtitle>콘센트는 어떤가요?</v-list-item-subtitle>
                <v-rating
                  v-model="all[4]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>방음</v-list-item-title>
                <v-list-item-subtitle>방음은 잘되나요?</v-list-item-subtitle>
                <v-rating
                  v-model="all[5]"
                  background-color="indigo lighten-3"
                  color="indigo"
                  x-large
                ></v-rating>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>발코니 여부</v-list-item-title>
                <v-list-item-subtitle>발코니가 있나요?</v-list-item-subtitle>
                <v-checkbox
                  v-model="all[6]"
                  color="indigo"
                  on-icon="mdi-emoticon-excited-outline"
                  off-icon="mdi-emoticon-cry-outline"
                  false-value="0"
                  true-value="1"
                ></v-checkbox>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card-text>
        <div style="flex: 1 1 auto"></div>

        <v-card-actions class="justify-center">
          <div class="my-2">
            <v-btn
              color="primary"
              dark
              class="ma-2"
              @click="(dialog4 = false), addHandler(4)"
            >
              확인
            </v-btn>
            <v-btn color="primary" dark class="ma-2" @click="resetHandler(4)">
              초기화
            </v-btn>
          </div>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<script>
import { mapGetters } from "vuex";
import HappyHouseApi, { EndPoint, HttpMethod } from "@/constant/HappyHouseApi";
export default {
  name: "NoteView",
  data() {
    return {
      note: {
        idx: null,
        houseIdx: null,
        userIdx: null,
        content: "",
        roomList: [],
        createdAt: null,
        updatedAt: null,
      },
      house: {},
      loading: false,
      star: 0,
      updatedArrIdx: -1,
      hasKitchen: false,
      hasAll: false,
      dialog1: false,
      room: ["", 3, 3, 3, 3, 3, 3, 0],
      dialog2: false,
      bath: ["", 3, 3, 3, 3, 3, 0, 0],
      dialog3: false,
      kitchen: ["주방", 3, 3, 3, 3, 3],
      dialog4: false,
      all: ["시설", 3, 3, 3, 3, 3, 0],
      rules: [(value) => !!value || "Required."],
      feedSettings: {
        objectType: "feed",
        content: {
          title: "",
          description: "",
          imageUrl:
            "https://upload.wikimedia.org/wikipedia/commons/thumb/3/39/NYC_Top_of_the_Rock_Pano.jpg/1920px-NYC_Top_of_the_Rock_Pano.jpg",
          link: {
            mobileWebUrl: "https://developers.kakao.com",
            webUrl: "https://developers.kakao.com",
          },
        },
        buttons: [
          {
            title: "웹으로 보기",
            link: {
              mobileWebUrl: "http://localhost:8080",
              webUrl: "http://localhost:8080",
            },
          },
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["userToken"]),
  },
  watch: {
    house: function (newVal, oldVal) {
      if (this.house.lat !== null && this.house.lng !== null) {
        if (window.kakao && window.kakao.maps) {
          this.initMap();
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
  created() {
    if (this.userToken) {
      // axios 해당 지역 상세 정보 불러오기
      HappyHouseApi.request({
        path: { id: this.$route.params.houseIdx },
        method: HttpMethod.GET,
        url: EndPoint.AUTH_API_HOUSE,
      })
        .then((res) => {
          console.log(res);
          if (res.data.isSuccess) {
            this.house = res.data.result;
          } else {
            alert("정보를 불러오는데 실패했습니다.");
          }
        })
        .catch(() => {
          alert("서버 내부 오류로 정보를 불러오는데 실패했습니다.");
        });

      HappyHouseApi.request({
        query: {
          houseIdx: this.$route.params.houseIdx,
        },
        method: HttpMethod.GET,
        url: EndPoint.AUTH_API_NOTE,
      })
        .then((res) => {
          console.log(res);
          if (res.data.isSuccess) {
            let totalScoreSum = 0;
            let totalScoreCnt = 0;
            this.note = res.data.result;
            this.note.roomList = this.note.roomList.map((data) => {
              data.item = data.item.split(",");
              data.score = data.score.split(",").map(Number);
              for (let i = 0; i < 5; i++) totalScoreSum += data.score[i];
              totalScoreCnt += 5;

              if (data.status === 1) {
                totalScoreSum += data.score[5];
                totalScoreCnt += 1;
              } else if (data.status === 3) {
                this.hasKitchen = true;
              } else if (data.status === 4) {
                this.hasAll = true;
              }
              return data;
            });
            console.log(this.note);
            this.star =
              totalScoreCnt === 0
                ? 0
                : Number((totalScoreSum / totalScoreCnt).toFixed(1));
            if (this.star % 1 < 0.5) this.star = parseInt(this.star);

            console.log("star: " + this.star);
          }
        })
        .catch(() => {
          alert("서버 내부 오류로 정보를 불러오는데 실패했습니다.");
        });
    } else {
      alert("로그인이 필요한 서비스입니다.");
      this.$router.push("/login");
    }
  },
  mounted() {
    window.Kakao.createDefaultButton(
      Object.assign({}, this.feedSettings, { container: ".kakao-link" })
    );
  },
  methods: {
    share() {
      this.feedSettings.content.title =
        "(" + this.star + "★)" + this.house.aptname;
      this.feedSettings.content.description = this.note.content;
      window.Kakao.Link.sendDefault(this.feedSettings);
    },
    clickHandler(info, index) {
      this.updatedArrIdx = index;
      if (info.status === 1) {
        this.room[0] = info.name;
        for (let i = 1; i <= 7; i++) {
          this.room[i] = info.score[i - 1];
        }
        this.dialog1 = true;
      } else if (info.status === 2) {
        this.bath[0] = info.name;
        for (let i = 1; i <= 7; i++) {
          this.bath[i] = info.score[i - 1];
        }
        this.dialog2 = true;
      } else if (info.status === 3) {
        this.kitchen[0] = info.name;
        for (let i = 1; i <= 5; i++) {
          this.kitchen[i] = info.score[i - 1];
        }
        this.dialog3 = true;
      } else if (info.status === 4) {
        this.all[0] = info.name;
        for (let i = 1; i <= 6; i++) {
          this.all[i] = info.score[i - 1];
        }
        this.dialog4 = true;
      }
    },
    checkHandler(display) {
      if (display === 3 && this.hasKitchen) {
        alert("이미 주방 정보가 등록되어 있습니다.");
      } else if (display === 3 && !this.hasKitchen) {
        this.dialog3 = true;
      } else if (display === 4 && this.hasAll) {
        alert("이미 시설 정보가 등록되어 있습니다.");
      } else if (display === 4 && !this.hasAll) {
        this.dialog4 = true;
      }
    },
    resetHandler(no) {
      if (no === 1) {
        this.room = ["", 3, 3, 3, 3, 3, 3, 0];
      } else if (no === 2) {
        this.bath = ["", 3, 3, 3, 3, 3, 0];
      } else if (no === 3) {
        this.kitchen = ["주방", 3, 3, 3, 3, 3];
      } else if (no === 4) {
        this.all = ["시설", 3, 3, 3, 3, 3, 0];
      }
    },
    addHandler(no) {
      let name = "";
      let item = [];
      let score = [];
      if (no === 1) {
        if (this.room[0] === "") {
          alert("방 이름을 입력하세요.");
          return;
        }
        this.dialog1 = false;
        name = this.room[0];
        item = ["환기", "도배", "천장", "조명/채광", "바닥", "방음", "창문"];
        score = this.room.slice(1);
      } else if (no === 2) {
        if (this.bath[0] === "") {
          alert("욕실 이름을 입력하세요.");
          return;
        }
        this.dialog2 = false;
        name = this.bath[0];
        item = ["환기", "배수", "수압", "타일", "조명", "목욕시설"];
        score = this.bath.slice(1);
      } else if (no === 3) {
        this.hasKitchen = true;
        name = this.kitchen[0];
        item = ["환기", "배수", "수압", "타일", "조명"];
        score = this.kitchen.slice(1);
      } else if (no === 4) {
        this.hasAll = true;
        name = this.all[0];
        item = ["난방", "온수", "방충망", "콘센트", "방음", "발코니"];
        score = this.all.slice(1);
      }
      if (this.updatedArrIdx === -1) {
        // 신규 등록
        this.note.roomList.push({
          idx: 0,
          status: no,
          name: name,
          item: item,
          score: score,
        });
      } else {
        // 저장된 메모 수정
        this.note.roomList[this.updatedArrIdx].name = name;
        this.note.roomList[this.updatedArrIdx].item = item;
        this.note.roomList[this.updatedArrIdx].score = score;
        this.updatedArrIdx = -1;
      }
      console.log(this.note.roomList);
      this.resetHandler(no);
    },
    removeHandler(index) {
      if (this.note.roomList[index].status === 3) {
        this.hasKitchen = false;
      } else if (this.note.roomList[index].status === 4) {
        this.hasAll = false;
      }
      this.note.roomList[index].status = 5;
    },
    updateHandler() {
      this.loading = true;
      // axios 메모 정보 업데이트
      this.note.roomList = this.note.roomList.map((data) => {
        data.item = data.item.join(",");
        data.score = data.score.join(",");
        return data;
      });
      HappyHouseApi.request({
        path: { id: this.note.idx },
        data: this.note,
        method: HttpMethod.PATCH,
        url: EndPoint.AUTH_API_NOTE,
      })
        .then((res) => {
          console.log(res);
          if (res.data.isSuccess) {
            alert("정상 등록되었습니다.");
            this.moveList();
          } else {
            alert("업데이트에 실패했습니다.");
            this.moveHome();
          }
        })
        .catch(() => {
          alert("서버 내부 오류로 업데이트에 실패했습니다.");
          this.moveHome();
        });
    },
    moveList() {
      this.$router.push("/bookmark").catch(() => {});
    },
    moveHome() {
      this.$router.push("/");
    },
    initMap() {
      const roadviewContainer = document.getElementById("roadview");
      const roadview = new kakao.maps.Roadview(roadviewContainer);
      const roadviewClient = new kakao.maps.RoadviewClient();
      const position = new kakao.maps.LatLng(this.house.lat, this.house.lng);
      roadviewClient.getNearestPanoId(position, 50, function (panoId) {
        roadview.setPanoId(panoId, position);
      });
    },
  },
};
</script>
<style>
.detail-header {
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: stretch;
  font-size: 3ex;
}
.detail-header:hover,
.detail-header:hover > .v-icon {
  color: #0097a7;
}
</style>