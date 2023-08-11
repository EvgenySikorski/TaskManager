package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.INotificationService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class NotificationService implements INotificationService {

    private final MailSenderService mailSenderService;
    private final TemplateEngine templateEngine;

    public NotificationService(MailSenderService mailSenderService, TemplateEngine templateEngine) {
        this.mailSenderService = mailSenderService;
        this.templateEngine = templateEngine;
    }

    @Override
    public void send(User user) {

        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("name", user.getFio());
        context.setVariable("code", user.getActivationCode());
        context.setVariable("mail", user.getMail());
        String text = templateEngine.process("activationMail.txt", context);

        mailSenderService.send(user.getMail(), "Activation message", text);

    }
}
