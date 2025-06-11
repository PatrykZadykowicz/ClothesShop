package wi.pb.clothesshop.service;

import java.io.IOException;

public interface MailService {
    void sendEmail(String from, String to, String subject, String content) throws IOException;
}
