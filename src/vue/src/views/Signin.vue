<template>
  <v-app>
    <v-card width="400px" class="mx-auto mt-5">
      <v-card-title>
        <h1 class="display-1">ログイン</h1>
      </v-card-title>
      <v-card-text>
        <v-form @submit.prevent>
          <v-text-field
            prepend-icon="mdi-account-circle"
            label="ユーザ名"
            v-model="user.userId"
          />
          <v-text-field
            v-bind:type="showPassword ? 'text' : 'password'"
            @click:append="showPassword = !showPassword"
            prepend-icon="mdi-lock"
            label="パスワード"
            v-model="user.password"
          />
          <v-card-actions>
            <v-btn class="info" @click="doLogin">ログイン</v-btn>
          </v-card-actions>
        </v-form>
      </v-card-text>
    </v-card>
  </v-app>
</template>
<script>
export default {
  data() {
    return {
      showPassword: false,
      user: {},
    };
  },
  methods: {
    doLogin() {
      this.axios.post("/signin", this.user).then((response) => {
        this.$store.dispatch("auth", {
          userId: response.data.userId,
          userToken: response.data.token,
        });
        this.$router.push(this.$route.query.redirect);
      });
    },
  },
};
</script>