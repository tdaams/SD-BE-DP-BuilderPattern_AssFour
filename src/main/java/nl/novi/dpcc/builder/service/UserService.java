package nl.novi.dpcc.builder.service;

import nl.novi.dpcc.builder.domain.User;
import nl.novi.dpcc.builder.domain.UserBuilder;
import nl.novi.dpcc.builder.payload.request.UserRegistrationRequest;
import nl.novi.dpcc.builder.payload.response.MessageResponse;
import nl.novi.dpcc.builder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Here the UserRegistrationRequest data is being made.
    public ResponseEntity<MessageResponse>  registerUser(@Valid UserRegistrationRequest userRegistrationRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userRegistrationRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userRegistrationRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new UserBuilder()
                .withUserName(userRegistrationRequest.getUsername())
                .withPassword(userRegistrationRequest.getPassword())
                .withPasswordRepeatCheck(userRegistrationRequest.getPasswordRepeated())
                .withEmail(userRegistrationRequest.getEmail())
                .withLastName(userRegistrationRequest.getLastName())
                .withFirstName(userRegistrationRequest.getFirstName())
                .buildwithChecks(userRegistrationRequest.getPasswordRepeated());

        // TODO Student: Bonus: Check if wachtwoorden gelijk zijn. Geef anders een error.
        // TODO Student: Hier moet het userRegistrationRequest object omgebouwd worden naar een User-object!
        // TODO Student: Huidige code slaat een leeg user object op in de database.
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
