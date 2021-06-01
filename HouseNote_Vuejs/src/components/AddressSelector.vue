<template>
	<v-container>
		<v-row align="center" justify="center">
			<v-col cols="4" md="4">
				<v-select label="도/광역시" 
				v-model="sido"
          :items="sidos"
          item-text="sido_name"
          item-value="sido_code"
          persistent-hint
          single-line
		  v-on:change="onChangeSido"> 
					<!-- <v-option v-for="(sido,index) in sidos" :key="index" :value="sido.sido_code">{{sido.sido_name}}</v-option> -->
				</v-select>
			</v-col>
			<v-col cols="4" md="4">
				<v-select label="시/구/군"
				v-model="gugun"
          :items="guguns"
          item-text="gugun_name"
          item-value="gugun_code"
          persistent-hint
          single-line
		  v-on:change="onChangeGugun"> > </v-select>
			</v-col>
			<v-col cols="4" md="4">
				<v-select label="동"
				v-model="dong"
          :items="dongs"
          item-text="dong"
          item-value="dong"
          persistent-hint
		  return-object
          single-line
		  v-on:change="onChangeDong"> > </v-select>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import HappyHouseApi, {EndPoint, HttpMethod} from "../constant/HappyHouseApi";
import { mapActions, mapGetters } from "vuex";
export default {
	name: 'AddressSelector',
	data() {
		return {
			sidos: [],
		}
	},
	computed: {
		...mapGetters(["guguns","dongs","pageNavigation"]),
		sido: {
      get() {
        return this.$store.state.sido;
      },
      set(payload) {
        this.$store.dispatch("setSido", payload);
      },
    },
	gugun: {
      get() {
        return this.$store.state.gugun;
      },
      set(payload) {
        this.$store.dispatch("setGugun", payload);
      },
    },
	dong: {
      get() {
        return this.$store.state.dong;
      },
      set(payload) {
        this.$store.dispatch("setDong", payload);
      },
    },
	},
	created: function () {
		HappyHouseApi.request({
			data: {},
			method: HttpMethod.GET,
			url: EndPoint.AUTH_API_SIDO,
			}).then((res => { // 결과 return
				if (res.data.code === 200) {
					console.log(res.data.result);
					this.sidos = res.data.result;
				}
			})).catch(function (error) {
				console.log(error);
			});
	},
	methods: {
		...mapActions(["setSido","setGugun","setDong","setGuguns","setDongs","setHouses","setPageNavigation"]),
		onChangeSido(sido) {
			this.setSido(sido);
			this.setDong("");
			HappyHouseApi.request({
			query: {sido: sido},
			method: HttpMethod.GET,
			url: EndPoint.AUTH_API_GUGUN,
			}).then((res => { // 결과 return
				if (res.data.code === 200) {
					this.setGuguns(res.data.result);
				}
			})).catch(function (error) {
				console.log(error);
			});
		},
		onChangeGugun(gugun) {
			this.setGugun(gugun);
			HappyHouseApi.request({
			query: {gugun: gugun},
			method: HttpMethod.GET,
			url: EndPoint.AUTH_API_DONG,
			}).then((res => { // 결과 return
				if (res.data.code === 200) {
					this.setDongs(res.data.result);
				}
			})).catch(function (error) {
				console.log(error);
			});
		},
		onChangeDong(dong) {
			this.setDong(dong);
			HappyHouseApi.request({
			query: {dong: dong.dong, dongcode: dong.dongcode, pg: 1},
			method: HttpMethod.GET,
			url: EndPoint.AUTH_API_HOUSE,
			}).then((res => { // 결과 return
				if (res.data.code === 200) {
					this.setHouses(res.data.result);
					this.setPageNavigation(res.data.pageNavigation);
					console.log(res.data.result);
					console.log(res.data.pageNavigation);
					console.log(this.pageNavigation);
				}
			})).catch(function (error) {
				console.log(error);
			});
		},
	},
};
</script>

<style scoped></style>
