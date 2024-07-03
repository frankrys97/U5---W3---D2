package francescocristiano.U5_W3_D2.config;

import francescocristiano.U5_W3_D2.entities.Employee;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {

    private String mailgunApiKey;
    private String mailgunDomainName;
    private String mailgunName;
    private String mailgunEmail;

    public MailgunSender(@Value("${mailgun.apikey}") String mailgunApiKey, @Value("${mailgun.domainname}") String mailgunDomainName, @Value("${mailgun.personalname}") String mailgunName, @Value("${mailgun.personalemail}") String mailgunEmail) {
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunDomainName = mailgunDomainName;
        this.mailgunName = mailgunName;
        this.mailgunEmail = mailgunEmail;
    }

    public void sendRegistrationEmail(Employee recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainName + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", this.mailgunName + " <" + this.mailgunEmail + ">")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registration Confirmation")
                .queryString("text", "Congratulations " + recipient.getName() + " " + recipient.getSurname() + "! You have been hired and will shortly receive information regarding the devices assigned to you.")
                .asJson();

        System.out.println(response.getBody());
    }
}
