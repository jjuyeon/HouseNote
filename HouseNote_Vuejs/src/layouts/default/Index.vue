<template>
  <v-app>
    <!-- 상단바 -->
    <default-bar />
    <!-- 사이드바 -->
    <default-drawer />
    <!-- 메인 화면, 라우터에 따라 컴포넌트 뿌려주는 곳 -->
    <default-view />
    <!-- 말그대로 footer -->
    <default-footer />
  </v-app>
</template>

<script>
export default {
  name: "DefaultLayout",

  components: {
    DefaultBar: () =>
      import(
        /* webpackChunkName: "default-app-bar" */
        "./AppBar"
      ),
    DefaultDrawer: () =>
      import(
        /* webpackChunkName: "default-drawer" */
        "./Drawer"
      ),
    DefaultFooter: () =>
      import(
        /* webpackChunkName: "default-footer" */
        "./Footer"
      ),
    DefaultView: () =>
      import(
        /* webpackChunkName: "default-view" */
        "./View"
      ),
  },
};
</script>
