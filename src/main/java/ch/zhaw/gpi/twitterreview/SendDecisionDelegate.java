package ch.zhaw.gpi.twitterreview;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SendDecisionDelegate
 */
@Named("sendDecisionAdapter")
public class SendDecisionDelegate implements JavaDelegate {

    @Autowired
    private MailService mailService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String checkResult = (String) execution.getVariable("checkResult");
        String checkResultComment = (String) execution.getVariable("checkResultComment");
        String email = (String) execution.getVariable("email");
        String fullName = (String) execution.getVariable("fullName");
        String tweetContent = (String) execution.getVariable("tweetContent");

        String anredeKommabt = "Sehr geehrte Kommunikationsabteilung\n\n";
        String anredeMitarbeiter = "Hallo " + fullName + "\n\n";

        String subject;
        String bodyMain;

        if(checkResult.equals("rejected")){
            subject = "Tweet abgelehnt";
            bodyMain = "Die folgende Tweet-Anfrage wurde abgelehnt:\n" + tweetContent + "\nDie Begründung ist: " + checkResultComment;
        } else {
            subject = "Tweet veröffentlicht";
            bodyMain = "Die folgende Tweet-Anfrage wurde veröffentlicht:\n" + tweetContent;
        }

        mailService.sendMail("kommabt@firma.ch", subject, anredeKommabt + bodyMain + "\n\nIhre Prozessplattform");
        mailService.sendMail(email, subject, anredeMitarbeiter + bodyMain + "\n\nIhre Prozessplattform");
    }    
}