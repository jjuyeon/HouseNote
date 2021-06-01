<template>
  <v-list-item class="mb-0 justify-space-between pl-3">
    <v-list-item-avatar>
      <v-img :src="require('@/assets/icon.png')" />
    </v-list-item-avatar>

    <v-list-item-content class="pl-2">
      <v-list-item-title class="text-h3">
        <strong class="text-h4 font-weight-black" style="margin-right: 5%"
          >집모장</strong
        >
        <span class="text-h6 primary--text">HOUSE NOTE</span>
      </v-list-item-title>
    </v-list-item-content>
  </v-list-item>
</template>

<script>
export default {
  name: "DefaultDrawerHeader",
};
</script>
