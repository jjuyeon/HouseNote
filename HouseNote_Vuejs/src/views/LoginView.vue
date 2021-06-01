<template>
	<v-container fluid>
		<login-kakao></login-kakao>
	</v-container>
</template>

<script>
import LoginKakao from '../components/login/LoginKakao';
export default {
	name: 'LoginView',
	components: { LoginKakao },
};
</script>
