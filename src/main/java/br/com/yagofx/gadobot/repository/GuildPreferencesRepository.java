package br.com.yagofx.gadobot.repository;

import br.com.yagofx.gadobot.entity.GuildPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildPreferencesRepository extends JpaRepository<GuildPreferences, String> {}
