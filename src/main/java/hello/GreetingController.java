package hello;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import hello.domain.News;
import hello.probando.ConnectHttps;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    List<News> noticias;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
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
                News nueva = new News(description,title,pubDate,content,link);
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

    @RequestMapping("/varOffline")
    public List<News> varianteOffLine(){
         noticias = new ArrayList<>();
        saveNews(new News("El Barca no ficha","11/07/2017","Contenido Contenido Contenido Contenido ContenidoContenido Contenido Contenido "));
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
