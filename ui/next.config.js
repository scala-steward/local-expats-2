/** @type {import('next').NextConfig} */

const withPWA = require('next-pwa')({
    dest: 'public',
    // To fix an issue with webpack --watch mode in development
    // https://github.com/GoogleChrome/workbox/issues/1790
    disable: process.env.NODE_ENV === 'development'
});

module.exports = withPWA({
    reactStrictMode: true,
    productionBrowserSourceMaps: true,
    rewrites() {
        return [
            {
                source: '/api/:path*',
                destination: `${process.env.API_URL}/api/:path*`,
                basePath: false
            }
        ]
    }
});
