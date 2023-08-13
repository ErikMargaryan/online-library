package com.library.testdata;

import com.library.persistence.entity.*;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class TestData {
    public static User userData() {
        return User.builder()
                .firstName("Erik")
                .lastName("Margaryan")
                .phone("+37498490961")
                .email("erickmargarian@gmail.com")
                .password("super")
                .favoriteGenres(List.of("genre1", "genre2"))
                .build();
    }

    public static BillingAddress billingAddressData() {
        return BillingAddress.builder()
                .postalZip(12345)
                .address("123 Main St")
                .country("USA")
                .user(userData())
                .build();
    }

    @SneakyThrows
    public static Book bookData() {
        return Book.builder()
                .title("Majesty,' the.")
                .author("Kieran Swift")
                .genre("Amet")
                .description("VERY wide, but she stopped hastily, " +
                        "for the end of every line: 'Speak roughly to your places!' shouted the Queen." +
                        " 'You make me larger, it must be Mabel after all, and I shall be late!' (when she.")
                .isbn("9783149754645")
                .image(new URL("http://placeimg.com/480/640/any"))
                .published(LocalDate.of(1973, 3, 19))
                .publisher("Repudiandae Adipisci")
                .price(BigDecimal.ZERO)
                .build();
    }

    public static CreditCard creditCardData() {
        return CreditCard.builder()
                .pan("1234 5678 9876 5432")
                .expirationDate(LocalDate.of(2026, 12, 20))
                .cvv(123)
                .build();
    }

    @SneakyThrows
    public static Library libraryData() {
        return Library.builder()
                .name("NPUA Library")
                .address("Teryan")
                .phoneNumber("+374123456")
                .website(new URL("https://polytechnic.am/library"))
                .build();
    }


    public static Role roleData() {
        return Role.builder()
                .name("ADMIN")
                .description("Admin role")
                .build();
    }

    public static Purchase purchaseData() {
        return Purchase.builder()
                .date(LocalDate.now())
                .totalPrice(BigDecimal.valueOf(1235.322))
                .build();
    }
}

