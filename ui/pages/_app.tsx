import * as React from 'react';
import Head from 'next/head';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import NavBar from "../src/nav/NavBar";
import {AppProps} from "next/app";

const theme = createTheme();

export default function MyApp({Component, pageProps}: AppProps) {
    return (
        <ThemeProvider theme={theme}>
            <Head>
                <meta name="viewport" content="initial-scale=1, width=device-width"/>
                <title>NepaliUS</title>
            </Head>
            <CssBaseline/>
            <NavBar/>
            <Component {...pageProps} />
        </ThemeProvider>
    );
}
