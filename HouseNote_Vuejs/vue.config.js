module.exports = {
  devServer: {
    proxy: "http://localhost/",
    disableHostCheck: true,
  },

  transpileDependencies: ["vuetify"],
};
