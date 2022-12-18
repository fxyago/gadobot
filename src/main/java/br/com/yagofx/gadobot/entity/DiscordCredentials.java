package br.com.yagofx.gadobot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscordCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Long discordSelfId;

    @NotBlank
    String token;

}
