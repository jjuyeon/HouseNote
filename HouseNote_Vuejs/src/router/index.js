// Imports
import Vue from "vue";
import Router from "vue-router";
import { trailingSlash } from "@/util/helpers";
import { layout, route } from "@/util/routes";

Vue.use(Router);

const router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  scrollBehavior: (to, from, savedPosition) => {
    if (to.hash) return { selector: to.hash };
    if (savedPosition) return savedPosition;

    return { x: 0, y: 0 };
  },
  routes: [
    layout("Default", [
      // (라우터 이름, 뷰 페이지 이름, path 이름)
      route("아파트 정보 검색", "MainView"),
      route("부동산 정보 검색", "SearchView", "detail/:houseIdx"),
      route("관심지역 목록", "BookmarkView", "bookmark"),
      route("관심지역 상세 후기", "NoteView", "note/:houseIdx"),
      route("로그인", "LoginView", "login"),
      route("회원정보 및 탈퇴", "MyPageView", "mypage"),
    ]),
  ],
});

router.beforeEach((to, from, next) => {
  return to.path.endsWith("/") ? next() : next(trailingSlash(to.path));
});

export default router;
