<template>
  <div>
    <h3>Welcome {{ $store.state.userId }}</h3>
    <p>{{ message }}</p>
  </div>
</template>
<script>
export default {
  data() {
    return {
      message: "",
    };
  },
  created: function () {
    this.fetchHello();
  },
  methods: {
    fetchHello() {
      this.$axios.get("/hello").then((response) => {
        this.message = response.data.message;
      });
    },
  },
};
</script>