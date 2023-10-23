package dev.ironia.ms.user.producers;

import dev.ironia.ms.user.dtos.EmailDto;
import dev.ironia.ms.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UserModel userModel) {
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserId());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("Cadastro realizado com sucesso!");
        emailDto.setText(
                userModel.getName() + ", seja bem vindx! \n" +
                        "Agradecemos o seu cadastro, aproveite agora mesmo todos os recursos " +
                        "da nossa plataforma!"
        );

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
