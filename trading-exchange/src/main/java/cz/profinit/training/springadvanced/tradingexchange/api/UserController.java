package cz.profinit.training.springadvanced.tradingexchange.api;

import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.service.CurrencyTo;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import cz.profinit.training.springadvanced.tradingexchange.service.user.UserService;
import cz.profinit.training.springadvanced.tradingexchange.service.user.UserTo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/{username}")
    public UserTo create(@PathVariable("username") String username) {
        return userService.createUser(Username.of(username));
    }

    @PutMapping("/{username}/balance/{currencyCode}/{amount}")
    public ResponseEntity<UserTo> deposit(@PathVariable("username") String username,
                                          @PathVariable("currencyCode") String currencyCode,
                                          @PathVariable("amount") BigDecimal amount) {

        return userService.deposit(Username.of(username), MoneyTo.of(CurrencyTo.of(currencyCode), amount))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserTo> get(@PathVariable("username") String username) {
        return userService.getUser(Username.of(username))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
