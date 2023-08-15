package com.library.service;

import com.library.csvData.UserCSVModel;
import com.library.dto.mapper.Mapper;
import com.library.dto.request.UserRequestDtoForUpdate;
import com.library.dto.response.UserResponseDto;
import com.library.persistence.entity.BillingAddress;
import com.library.persistence.entity.CreditCard;
import com.library.persistence.entity.User;
import com.library.persistence.repository.UserRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Mapper mapper;

    @Transactional
    public Page<UserResponseDto> findAllUsers(Pageable pageable) {
        val users = userRepository.findAll(pageable);
        val userResponseDtos = users.stream()
                .map(mapper::toDto)
                .toList();
        return new PageImpl<>(userResponseDtos, pageable, users.getTotalElements());
    }
    @Transactional
    public void parseAndSaveCSV(MultipartFile file) throws IOException {
        List<UserCSVModel> userList;

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            val csvToBean = new CsvToBeanBuilder<UserCSVModel>(reader)
                    .withType(UserCSVModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            userList = csvToBean.parse();
        }

        for (UserCSVModel userData : userList) {
            val names = userData.getName().split(" ");
            val firstName = Arrays.stream(names).findFirst().get();
            val lastName = Arrays.stream(names).skip(1).collect(Collectors.joining(" "));
            val user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .phone(userData.getPhone())
                    .email(userData.getEmail())
                    .password(passwordEncoder.encode(userData.getPassword()))
                    .build();

            val address = BillingAddress.builder()
                    .address(userData.getAddress())
                    .postalZip(Integer.parseInt(userData.getPostalZip()))
                    .country(userData.getCountry())
                    .user(user)
                    .build();
            user.setAddress(address);

            val creditCard = CreditCard.builder()
                    .pan(userData.getPan())
                    .expirationDate(LocalDate.parse(userData.getExpdate(), DateTimeFormatter.ofPattern("d-MMM-yy")))
                    .cvv(Integer.parseInt(userData.getCvv()))
                    .user(user)
                    .build();
            user.getCreditCards().add(creditCard);

            userRepository.save(user);
        }
    }

    public Optional<UserResponseDto> findUserById(Long id) {
        val userOptional = userRepository.findById(id);
        return userOptional.map(mapper::toDto);
    }

    public UserResponseDto updateUser(Long id, @Valid UserRequestDtoForUpdate userDto) {
        val user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        val updatedUser = mapper.toEntity(userDto);
        updatedUser.setId(id);
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        updatedUser.setUserRoles(user.getUserRoles());
        updatedUser.setPurchases(user.getPurchases());
        updatedUser.setCreditCards(user.getCreditCards());
        updatedUser.setAddress(user.getAddress());

        val savedUser = userRepository.save(updatedUser);
        return mapper.toDto(savedUser);
    }

    public void deleteUserById(Long id) {
            userRepository.deleteById(id);
    }
}