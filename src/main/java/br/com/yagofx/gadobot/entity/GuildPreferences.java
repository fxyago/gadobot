package br.com.yagofx.gadobot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuildPreferences {

    @Id
    String guildId;

    Character prefix;

    public GuildPreferences(String guildId) {
        this.guildId = guildId;
        this.prefix = ';';
    }

}