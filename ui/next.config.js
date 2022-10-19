/** @type {import('next').NextConfig} */

const withPWA = require('next-pwa')({
    dest: 'public'
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
