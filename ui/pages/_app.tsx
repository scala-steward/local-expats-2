import Head from 'next/head';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import NavBar from "../src/nav/NavBar";
import {AppProps} from "next/app";
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";
import {QueryClient} from "@tanstack/query-core";
import {QueryClientProvider} from "@tanstack/react-query";

const theme = createTheme();

const queryClient = new QueryClient();

export default function MyApp({Component, pageProps}: AppProps) {
    return (
        <ThemeProvider theme={theme}>
            <Head>
                <meta name="viewport" content="initial-scale=1, width=device-width"/>
                <title>NepaliUS</title>
            </Head>

            <CssBaseline/>

            <QueryClientProvider client={queryClient}>
                <NavBar/>
                <Component {...pageProps} />
                <ReactQueryDevtools/>
            </QueryClientProvider>
        </ThemeProvider>
    );
}
