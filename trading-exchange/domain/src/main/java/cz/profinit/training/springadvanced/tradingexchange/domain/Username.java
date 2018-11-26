package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Wrapper type for username.
 * Usernames are better treated as concrete types, not just {@link String}s.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Username implements Serializable {

    @Column(unique = true)
    @NotBlank
    private String username;

    public static Username of(String username) {
        return Username.builder().username(username).build();
    }
}
