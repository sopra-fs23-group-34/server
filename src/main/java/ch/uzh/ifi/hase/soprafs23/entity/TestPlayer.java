package ch.uzh.ifi.hase.soprafs23.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TestPlayer implements  LobbyPlayer{

    @Override
    public Long getId() {
        return 10L;
    }

    @Override
    public String getUsername() {
        return "TestPlayer";
    }
}
