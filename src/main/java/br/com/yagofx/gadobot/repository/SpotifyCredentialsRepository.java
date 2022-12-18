package br.com.yagofx.gadobot.repository;

import br.com.yagofx.gadobot.entity.SpotifyCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotifyCredentialsRepository extends JpaRepository<SpotifyCredentials, Integer> {

    @Query("SELECT sc FROM SpotifyCredentials sc ORDER BY sc.id")
    SpotifyCredentials findFirst();

}
