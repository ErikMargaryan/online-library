package com.library.service;

import com.library.csvData.UserCSVModel;
import com.library.dto.mapper.Mapper;
import com.library.dto.request.UserRequestDto;
import com.library.dto.response.UserResponseDto;
import com.library.persistence.entity.BillingAddress;
import com.library.persistence.entity.CreditCard;
import com.library.persistence.entity.User;
import com.library.persistence.repository.RoleRepository;
import com.library.persistence.repository.UserRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final Mapper mapper;

    public Page<UserResponseDto> findAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponseDto> userResponseDtos = users.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(userResponseDtos, pageable, users.getTotalElements());
    }
    @Transactional
    public void parseAndSaveCSV(MultipartFile file) throws IOException {
        List<UserCSVModel> userList = new ArrayList<>();

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<UserCSVModel> csvToBean = new CsvToBeanBuilder<UserCSVModel>(reader)
                    .withType(UserCSVModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            userList = csvToBean.parse();
        }

        for (UserCSVModel userData : userList) {
            User user = new User();
            String[] names = userData.getName().split(" ");
            String firstName = Arrays.stream(names).findFirst().get();
            user.setFirstName(firstName);
            String lastName = Arrays.stream(names).skip(1).collect(Collectors.joining(" "));
            user.setLastName(lastName);
            user.setPhone(userData.getPhone());
            user.setEmail(userData.getEmail());
            user.setPassword(userData.getPassword());

            BillingAddress address = new BillingAddress();
            address.setAddress(userData.getAddress());
            address.setPostalZip(Integer.parseInt(userData.getPostalZip()));
            address.setCountry(userData.getCountry());
            address.setUser(user);
            user.setAddress(address);

            CreditCard creditCard = new CreditCard();
            creditCard.setPan(userData.getPan());
            creditCard.setExpirationDate(LocalDate.parse(userData.getExpdate(), DateTimeFormatter.ofPattern("d-MMM-yy")));
            creditCard.setCvv(Integer.parseInt(userData.getCvv()));
            creditCard.setUser(user);
            user.getCreditCards().add(creditCard);

            userRepository.save(user);
        }
    }

    public Optional<UserResponseDto> findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(mapper::toDto);
    }

    public UserResponseDto updateUser(Long id, @Valid UserRequestDto userDto) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        User updatedUser = mapper.toEntity(userDto);
        updatedUser.setId(id);

        User savedUser = userRepository.save(updatedUser);
        return mapper.toDto(savedUser);
    }

    public void deleteUserById(Long id) {
        System.out.println("Attempting to delete user with ID: " + id);

        try {
            userRepository.deleteById(id);
            System.out.println("User with ID {} deleted successfully." + id);
        } catch (Exception e) {
            System.out.println("Error occurred while deleting user with ID: " + id + e);
        }
    }
}