package wi.pb.clothesshop.service.impl;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wi.pb.clothesshop.service.MailService;

import java.io.IOException;
import java.util.List;



@Service
public class MailServiceImpl implements MailService {

    private final OkHttpClient client;

    @Value("${external.mail.api.url}")
    private String apiUrl;

    @Value("${external.mail.api.token}")
    private String apiToken;

    public MailServiceImpl() {
        client = new OkHttpClient().newBuilder().build();
    }

    @Override
    public void sendEmail(String from, String to, String subject, String content) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");

        String bodyJson = String.format("""
        {
          "from": {
            "email": "%s",
            "name": "ClothesShop"
          },
          "to": [
            {
              "email": "%s"
            }
          ],
          "subject": "%s",
          "text": "%s"
        }
        """, from, to, subject, content);

        RequestBody body = RequestBody.create(bodyJson, mediaType);

        Request request = new Request.Builder()
                .url(apiUrl)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "application/json")
                .build();


        Response response = client.newCall(request).execute();
    }

}
