package hello.probando;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by amsaborit on 06/07/2017.
 * http://www.rgagnon.com/javadetails/java-fix-certificate-problem-in-HTTPS.html
 */
public class ConnectHttps {

    public JSONObject newsInJson() throws NoSuchAlgorithmException, KeyManagementException, IOException, JSONException {

        TrustManager[] trustAllCerts = new TrustManager[] {
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

        String a = "http://localhost:8080/greeting";
        String origin = "http://www.estadiodeportivo.com/elementosInt/rss/5";

        URL url = new URL("https://api.rss2json.com/v1/api.json?rss_url=" + origin);
        URL urls = new URL(a);

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


        URLConnection con = url.openConnection();
        //con.setRequestMethod("GET");
        con.setRequestProperty("RequestMethod","GET");
        JSONObject jsonObject = readJsonFromUrl(url);
        return jsonObject;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(URL url) throws IOException, JSONException {
//        InputStream is = new URL(url).openStream();
        InputStream is = url.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
