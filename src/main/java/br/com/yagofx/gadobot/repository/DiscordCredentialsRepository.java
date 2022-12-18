package br.com.yagofx.gadobot.repository;

import br.com.yagofx.gadobot.entity.DiscordCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscordCredentialsRepository extends JpaRepository<DiscordCredentials, Integer> {

    @Query("SELECT dc FROM DiscordCredentials dc ORDER BY dc.id")
    DiscordCredentials findFirst();

}
