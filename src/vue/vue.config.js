// CORS対策でプロキシ設定
module.exports = {
    devServer: {
        proxy: {
            "/": {
                target: "http://localhost:8080",
            }
        }
    }
};