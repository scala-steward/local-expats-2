import Head from 'next/head';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import NavBar from "../src/nav/NavBar";
import {AppProps} from "next/app";
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";
import {QueryClient} from "@tanstack/query-core";
import {QueryClientProvider} from "@tanstack/react-query";
import {SelectedLocationProvider} from "../src/location/SelectedLocation";
import {PostBookmarksProvider} from "../src/post/PostBookmarks";

const theme = createTheme();

const queryClient = new QueryClient();

export default function MyApp({Component, pageProps}: AppProps) {
    const title = "NepaliUS";
    const description = "Connect with Nepali communities in the US";
    return (
        <ThemeProvider theme={theme}>
            <Head>
                <title>NepaliUS</title>
                <meta name="description" content={description}/>
                <meta name="keywords" content="nepali,nepalese,communities,us,usa"/>
                <link rel="manifest" href="/manifest.json"/>
                <meta name='viewport'
                      content='minimum-scale=1, initial-scale=1, width=device-width, shrink-to-fit=no, user-scalable=no, viewport-fit=cover'/>
                <meta name="application-name" content={title}/>
                <meta name="apple-mobile-web-app-capable" content="yes"/>
                <meta name="apple-mobile-web-app-status-bar-style" content="default"/>
                <meta name="apple-mobile-web-app-title" content={title}/>
                <meta name="description" content={description}/>
                <meta name="format-detection" content="telephone=yes, date=yes, address=yes, email=yes, url=yes"/>
                <meta name="mobile-web-app-capable" content="yes"/>
                <meta name="theme-color" content="#ffffff"/>

                <link rel="shortcut icon" href="/np-us.png"/>
                <link rel="apple-touch-icon" sizes="152x152" href="/icons/icon-152x152.png"/>

                <meta property="og:type" content="website"/>
                <meta property="og:title" content={title}/>
                <meta property="og:description" content={description}/>
                <meta property="og:site_name" content={title}/>
                <meta property="og:url" content="https://NepaliUS.com"/>
                <meta property="og:image" content="https://NepaliUS.com/icons/icon-512x512.png"/>
            </Head>

            <CssBaseline/>

            <QueryClientProvider client={queryClient}>
                <SelectedLocationProvider>
                    <PostBookmarksProvider>
                        <NavBar/>
                        <Component {...pageProps} />
                    </PostBookmarksProvider>
                </SelectedLocationProvider>
                <ReactQueryDevtools/>
            </QueryClientProvider>
        </ThemeProvider>
    );
}
