package hello;

import java.io.IOException;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import hello.domain.Feed;
import hello.domain.FeedMessage;
import hello.domain.News;
import hello.probando.ConnectHttps;
import hello.rss.RSSFeedParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.*;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    List<News> noticias;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/estaEslatipa")
    public List<News> newsAll() throws IOException, NoSuchAlgorithmException, JSONException, KeyManagementException {
        ConnectHttps https = new ConnectHttps();
        try {
            JSONObject jsonObject = https.newsInJson();
            Object items = jsonObject.get("items");
            JSONArray d = jsonObject.getJSONArray("items");

            List<News> noticias = new ArrayList<>();

            for (int i = 0; i < d.length(); i++) {
                JSONObject o = (JSONObject) d.get(i);
                String description = (String) o.get("description");
                String title = (String) o.get("title");
                String pubDate = (String) o.get("pubDate");
                String content = (String) o.get("content");
                String link = (String) o.get("link");
                News nueva = new News(description, title, pubDate, content, link);
                noticias.add(nueva);
            }
            //etiquetas description, title, pubDate, content,link

            return noticias;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/rssRead")
    public void ReadTest() throws NoSuchAlgorithmException, KeyManagementException {
        //para conectarce desde aqui

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {


                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    /*
     * end of the fix
     */
        ProxySelector.setDefault(new ProxySelector() {

            @Override
            public List<Proxy> select(URI uri) {
                return Arrays
                        .asList(new Proxy(Proxy.Type.HTTP,
                                new InetSocketAddress("localhost",
                                        4334)));
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                throw new RuntimeException("Proxy connect failed", ioe);
            }
        });
        //RSSFeedParser parse = new RSSFeedParser("http://www.vogella.com/article.rss");
        RSSFeedParser parse = new RSSFeedParser("http://www.futbolred.com/feeds/champions-league");
        Feed feed = parse.readFeed();
        System.out.println(feed);
        for (FeedMessage message : feed.getMessages()) {
            System.out.println(message);
        }
    }

    @RequestMapping("/allSport")
    public JSONObject listarAllt() {
        ConnectHttps https = new ConnectHttps();
        try {
            JSONObject jsonObject = https.newsInJson();
            Object items = jsonObject.get("items");
            JSONArray d = jsonObject.getJSONArray("items");
            return jsonObject;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/varOffline")
    public List<News> varianteOffLine() {
        noticias = new ArrayList<>();
        saveNews(new News("El Barca no ficha", "11/07/2017", "Contenido Contenido Contenido Contenido ContenidoContenido Contenido Contenido "));
        saveNews(new News("Carlos Lopez", "11/07/2017", "Hospital Blue Electrical Parts ltd Electrical Parts ltd Electrical Parts ltd Electrical Parts ltd"));
        saveNews(new News("TITULO 4", "11/07/2017", " Parts ltd Creativa App Creativa App Creativa Parts ltd Creativa App Creativa App Creativa Parts ltd Creativa App Creativa App Creativa Parts ltd Creativa App Creativa App Creativa Parts ltd Creativa App Creativa App Creativa Parts ltd Creativa App Creativa App CreativaParts ltd Creativa App Creativa App CreativaParts ltd Creativa App Creativa App Creativa Parts ltd Creativa App Creativa App Creativa Electrical Parts ltd Creativa App Creativa App Creativa App Creativa App Creativa App Creativa App"));
        saveNews(new News("TITULO 5", "11/07/2017", "Electrical Parts ltd Creativa App Creativa App Creativa App Creativa App Creativa App Creativa App"));
        saveNews(new News("TITULO 3", "11/07/2017", "Electrical Parts ltd Creativa App Creativa App Creativa App Creativa App Creativa App Creativa App"));
        saveNews(new News("TITULO 2", "11/07/2017", "Electrical Parts ltd Creativa App Creativa App Creativa App Creativa App Creativa App Creativa App"));
        saveNews(new News("TITULO 6", "11/07/2017", "Electrical Parts ltd Creativa App Creativa App Creativa App Creativa App Creativa App Creativa App"));
        saveNews(new News("TITULO 7", "11/07/2017", "Electrical Parts ltd Creativa App Creativa App Creativa App Creativa App Creativa App Creativa App"));
        saveNews(new News("TITULO 8", "11/07/2017", "Electrical Parts ltd Creativa App Creativa App Creativa App Creativa App Creativa App Creativa App"));
        saveNews(new News("TITULO 9", "11/07/2017", "Electrical Parts ltd Creativa App Creativa App Creativa App Creativa App Creativa App Creativa App"));
        saveNews(new News("TITULO", "11/07/2017", "Electrical Parts ltd Creativa App Creativa App Creativa App Creativa App Creativa App Creativa App"));
        return noticias;

    }

    private void saveNews(News news) {
        noticias.add(news);
    }
}
