package com.library.testdata;

import com.library.dto.request.*;
import com.library.dto.response.*;
import com.library.persistence.entity.*;
import com.library.persistence.entity.composite.UserRoleKey;
import com.library.persistence.entity.joinEntity.UserRole;
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
        Library library = Library.builder()
                .name("NPUA Library")
                .address("Teryan")
                .phoneNumber("+374123456")
                .website(new URL("https://polytechnic.am/library"))
                .build();
        return library;
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

    @SneakyThrows
    public static LibraryRequestDto libraryRequestData() {
        return LibraryRequestDto.builder()
                .name("NPUA Library")
                .address("Teryan")
                .phoneNumber("+374123456")
                .website(new URL("https://polytechnic.am/library"))
                .build();
    }

    @SneakyThrows
    public static LibraryResponseDto libraryResponseData() {
        return LibraryResponseDto.builder()
                .id(1L)
                .name("NPUA Library")
                .address("Teryan")
                .phoneNumber("+374123456")
                .website(new URL("https://polytechnic.am/library"))
                .books(List.of())
                .build();
    }

    public static RoleRequestDto roleRequestData() {
        return RoleRequestDto.builder()
                .name("ADMIN")
                .description("Admin role")
                .build();
    }

    public static RoleResponseDto roleResponseData() {
        return RoleResponseDto.builder()
                .id(1L)
                .name("ADMIN")
                .description("Admin role")
                .build();
    }

    public static UserRequestDto userRequestData() {
        return UserRequestDto.builder()
                .firstName("Erik")
                .lastName("Margaryan")
                .phone("+37498490961")
                .email("erickmargarian@gmail.com")
                .password("super")
                .favoriteGenres(List.of("genre1", "genre2"))
                .build();
    }

    public static UserRequestDtoForUpdate userRequestDataForUpdate() {
        return UserRequestDtoForUpdate.builder()
                .firstName("Erik")
                .lastName("Margaryan")
                .phone("+37498490961")
                .password("super")
                .favoriteGenres(List.of("genre1", "genre2"))
                .build();
    }

    public static UserResponseDto userResponseData() {
        return UserResponseDto.builder()
                .firstName("Erik")
                .lastName("Margaryan")
                .phone("+37498490961")
                .email("erickmargarian@gmail.com")
                .password("super")
                .favoriteGenres(List.of("genre1", "genre2"))
                .creditCards(List.of())
                .purchases(List.of())
                .roles(List.of())
                .build();
    }

    public static UserRoleKeyDto userRoleKeyDtoData() {
        return UserRoleKeyDto.builder()
                .userId(1L)
                .roleId(1L)
                .build();
    }

    private static UserRoleKey userRoleKeyData() {
        return UserRoleKey.builder()
                .userId(1L)
                .roleId(1L)
                .build();
    }

    public static UserRole userRoleData() {
        return UserRole.builder()
                .id(userRoleKeyData())
                .user(userData())
                .role(roleData())
                .build();
    }

    public static UserRoleKeyResponseDto userRoleKeyResponseData() {
        return UserRoleKeyResponseDto.builder()
                .userId(1L)
                .roleId(1L)
                .build();
    }

    public static CreditCardRequestDto creditCardRequestData() {
        return CreditCardRequestDto.builder()
                .pan("1234 5678 9876 5432")
                .expirationDate(LocalDate.of(2026, 12, 20))
                .cvv(123)
                .build();
    }

    public static CreditCardResponseDto creditCardResponseData() {
        return CreditCardResponseDto.builder()
                .id(1L)
                .pan("1234 5678 9876 5432")
                .expirationDate(LocalDate.of(2026, 12, 20))
                .cvv(123)
                .build();
    }

    public static BillingAddressRequestDto billingAddressRequestData() {
        return BillingAddressRequestDto.builder()
                .postalZip(12345)
                .address("123 Main St")
                .country("USA")
                .build();
    }

    public static BillingAddressResponseDto billingAddressResponseData() {
        return BillingAddressResponseDto.builder()
                .postalZip(12345)
                .address("123 Main St")
                .country("USA")
                .build();
    }

    @SneakyThrows
    public static BookRequestDto bookRequestData() {
        return BookRequestDto.builder()
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

    @SneakyThrows
    public static BookResponseDto bookResponseData() {
        return BookResponseDto.builder()
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
}

