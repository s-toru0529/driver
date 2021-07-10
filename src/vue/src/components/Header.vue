<template>
  <v-app-bar color="primary" dark app clipped-left>
    <v-toolbar-title>Vuetify</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn text to="/signin">SIGNIN</v-btn>
    <v-menu offset-y>
      <template v-slot:activator="{ on }">
        <v-btn v-on="on" text>Support<v-icon>mdi-menu-down</v-icon></v-btn>
      </template>
      <v-list>
        <v-subheader>Get help</v-subheader>
        <v-list-item v-for="support in supports" :key="support.name">
          <v-list-item-icon>
            <v-icon>{{ support.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>{{ support.name }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
export default {
  data() {
    return {
      supports: [
        { name: "Consulting and suppourt", icon: "mdi-vuetify" },
        { name: "Discord community", icon: "mdi-discord" },
        { name: "Report a bug", icon: "mdi-bug" },
        { name: "Github issue board", icon: "mdi-github" },
        { name: "Stack overview", icon: "mdi-stack-overflow" },
      ],
    };
  },
};
</script>