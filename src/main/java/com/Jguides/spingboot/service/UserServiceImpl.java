package com.Jguides.spingboot.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Jguides.spingboot.Model.User;
import com.Jguides.spingboot.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User u) {
        // timestamps (entity uses String fields)
        String now = OffsetDateTime.now().toString();

        if (u.getId() == null) {
            // create
            if (repo.existsByUserName(u.getUserName())) {
                throw new IllegalStateException("Username already exists");
            }
            u.setCreatedAt(now);
            u.setUpdatedAt(now);
            // expect passwordHash already encoded by controller; guard anyway
            if (u.getPasswordHash() == null || u.getPasswordHash().isBlank()) {
                throw new IllegalArgumentException("Password is required");
            }
            return repo.save(u);
        } else {
            // update (merge + keep createdAt)
            User db = repo.findById(u.getId())
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            // if username changed, ensure unique
            if (!Objects.equals(db.getUserName(), u.getUserName())
                    && repo.existsByUserName(u.getUserName())) {
                throw new IllegalStateException("Username already exists");
            }

            db.setUserName(u.getUserName());
            db.setEmail(u.getEmail());
            db.setFullName(u.getFullName());
            db.setRole(u.getRole());
            // passwordHash should already be encoded by controller
            if (u.getPasswordHash() != null && !u.getPasswordHash().isBlank()) {
                db.setPasswordHash(u.getPasswordHash());
            }
            db.setUpdatedAt(now);
            return repo.save(db);
        }
    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<User> searchUser(String keyword) {
        String k = (keyword == null) ? "" : keyword;
        return repo.findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(k, k, k);
    }
}
