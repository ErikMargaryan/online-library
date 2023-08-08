package com.library.dto.mapper;

import com.library.dto.request.*;
import com.library.dto.response.*;
import com.library.persistence.entity.*;
import com.library.persistence.entity.composite.BookPurchaseKey;
import com.library.persistence.entity.joinEntity.BookPurchase;
import com.library.persistence.entity.joinEntity.LibraryBook;
import com.library.persistence.entity.joinEntity.UserRole;
import jdk.jfr.Name;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;


@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    User toEntity(UserRequestDto dto);

    @Mapping(source = "userRoles", target = "roles", qualifiedByName = "mapUserRolesToListRoleResponseDto")
    UserResponseDto toDto(User entity);

    @Named("mapUserRolesToListRoleResponseDto")
    default List<RoleResponseDto> mapUserRolesToListRoleResponseDto(List<UserRole> userRoles) {
        return userRoles.stream()
                .map(UserRole::getRole)
                .map(this::toDto)
                .toList();
    }

    @Mapping(source = "userId", target = "id.userId")
    @Mapping(source = "roleId", target = "id.roleId")
    UserRole toEntity(UserRoleKeyDto dto);

    @Mapping(source = "id.userId", target = "userId")
    @Mapping(source = "id.roleId", target = "roleId")
    UserRoleKeyResponseDto toDto(UserRole entity);

    CreditCard toEntity(CreditCardRequestDto dto);

    @Mapping(source = "user.id", target = "userId")
    CreditCardResponseDto toDto(CreditCard entity);

    BillingAddress toEntity(BillingAddressRequestDto dto);

    BillingAddressResponseDto toDto(BillingAddress entity);

    Role toEntity(RoleRequestDto dto);

    RoleResponseDto toDto(Role entity);

    Library toEntity(LibraryRequestDto dto);

    @Mapping(source = "libraryBooks", target = "books", qualifiedByName = "mapLibraryBooksToListBookResponseDto")
    LibraryResponseDto toDto(Library entity);

    @Named("mapLibraryBooksToListBookResponseDto")
    default List<BookResponseDto> mapLibraryBooksToListBookResponseDto(List<LibraryBook> libraryBooks) {
        return libraryBooks.stream()
                .map(LibraryBook::getBook)
                .map(this::toDto)
                .toList();
    }

    Book toEntity(BookRequestDto dto);

    BookResponseDto toDto(Book entity);

    Purchase toEntity(PurchaseRequestDto dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "creditCard.id", target = "creditCardId")
    @Mapping(source = "user", target = "bookIds", qualifiedByName = "mapUserToBookIds")
    PurchaseResponseDto toDto(Purchase entity);

    @Named("mapUserToBookIds")
    default List<Long> mapUserToBookIds(User user) {
        return user.getPurchases().stream()
                .flatMap(bookPurchase -> bookPurchase.getBookPurchases().stream())
                .map(BookPurchase::getId)
                .map(BookPurchaseKey::getBookId)
                .toList();
    }

}
