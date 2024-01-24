const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = (app) => {
    app.use(
        createProxyMiddleware(
            ["/ws-stomp","/sub"],
            {
                target: `http://mariapark.synology.me:5392`,
                ws: true,
                router: {
                    "/ws-stomp": `ws://mariapark.synology.me:5392`,
                    "/sub": `ws://mariapark.synology.me:5392`,
                },
            }
        )
    );
};
