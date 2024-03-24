import {defineConfig} from "vite";
import scalaJSPlugin from "@scala-js/vite-plugin-scalajs";

export default defineConfig({
    server: {
        proxy: {
            '/api': 'http://localhost:9000',
        }
    },
    plugins: [
        scalaJSPlugin({
            cwd: '..',
            projectID: 'client',
        })
    ],
});