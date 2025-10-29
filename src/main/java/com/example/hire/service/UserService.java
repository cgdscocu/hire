package com.example.hire.service;

import com.example.hire.dto.UserDTO;
import com.example.hire.entity.User;
import com.example.hire.exception.BusinessException;
import com.example.hire.exception.ValidationException;
import com.example.hire.mapper.UserMapper;
import com.example.hire.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO, String password) {
        // Username kontrolü
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new ValidationException("Bu kullanıcı adı zaten mevcut: " + userDTO.getUsername());
        }

        // Email kontrolü
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ValidationException("Bu email zaten mevcut: " + userDTO.getEmail());
        }

        User user = userMapper.toEntityWithPassword(userDTO, password);
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Kullanıcı bulunamadı: " + id));
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Kullanıcı bulunamadı: " + id));

        // Username kontrolü (kendisi hariç)
        if (!existingUser.getUsername().equals(userDTO.getUsername()) && 
            userRepository.existsByUsername(userDTO.getUsername())) {
            throw new ValidationException("Bu kullanıcı adı zaten mevcut: " + userDTO.getUsername());
        }

        // Email kontrolü (kendisi hariç)
        if (!existingUser.getEmail().equals(userDTO.getEmail()) && 
            userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ValidationException("Bu email zaten mevcut: " + userDTO.getEmail());
        }

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setIsActive(userDTO.getIsActive());
        existingUser.setUpdatedDate(LocalDateTime.now());

        User savedUser = userRepository.save(existingUser);
        return userMapper.toDTO(savedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("Kullanıcı bulunamadı: " + id);
        }
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

