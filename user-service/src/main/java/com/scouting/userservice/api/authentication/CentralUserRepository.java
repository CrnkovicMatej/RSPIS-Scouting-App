package com.scouting.userservice.api.authentication;

import com.scouting.userservice.api.authentication.exception.ConflictingActivityDefinitionException;
import com.scouting.userservice.api.authentication.exception.InvalidActivityException;
import com.scouting.userservice.api.authentication.exception.UserNotFoundException;
import com.scouting.userservice.api.authentication.exception.UserSavingException;
import com.scouting.userservice.api.authentication.model.User;
import com.scouting.userservice.api.authentication.model.UserUpdate;
import com.scouting.userservice.api.authentication.storage.UserEntity;
import com.scouting.userservice.api.authentication.storage.UserRepository;
import com.scouting.userservice.api.authentication.utils.UserApiKeyGenerator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.scouting.userservice.api.authentication.utils.Page;
import com.scouting.userservice.api.authentication.utils.UserPageQuery;


@Component
public class CentralUserRepository {
    private final UserRepository userRepository;
    private final UserApiKeyGenerator userApiKeyGenerator = new UserApiKeyGenerator();

    public CentralUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId)
    {
        return userRepository.findById(userId)
                .map(User::fromEntity)
                .orElseThrow();
    };

    public User modifyUser(Long userId, UserUpdate userUpdate){
        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.becauseTheUserDoesNotExist(userId.toString()));
        if (userUpdate.username() != null) {
            userEntity.setUsername(userUpdate.username());
        }
        if (userUpdate.role() != null) {
            userEntity.setRole(userUpdate.role());
        }

        return save(userEntity);
    }

    public void deleteUser(Long userId) {
        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.becauseTheUserDoesNotExist(userId.toString()));
        userRepository.delete(userEntity);
    }

    public User createUser(User user) {
        if (user.id() != null ) {
            throw InvalidActivityException.becauseTheIdIsProvided(user.id().toString());
        } else if (user.apiKey() != null) {
            throw InvalidActivityException.becauseTheApiKeyIsProvided(user.apiKey());
        }

        var userEntity = new UserEntity(
                null,
                userApiKeyGenerator.generateApiKey(),
                user.username(),
                user.role()
        );

        return save(userEntity);
    }

    public User findByApiKey(String apiKey)
    {
        return userRepository.findByApiKey(apiKey)
                .map(User::fromEntity)
                .orElseThrow();
    };

    public Page<User> getAllUsers(UserPageQuery userPageQuery) {
        org.springframework.data.domain.Page<UserEntity> resultPage;
        var pageRequest = PageRequest.of(userPageQuery.page(), userPageQuery.size(), Sort.by("id"));


        if(userPageQuery.role().isPresent()){
            resultPage = userRepository.findPageByRole(
                    userPageQuery.role().get(),
                    pageRequest
            );
        }
        else {
            resultPage = userRepository.findAll(pageRequest);
        }


        return new Page<>(
                resultPage.get().map(User::fromEntity).toList(),
                userPageQuery.page(),
                userPageQuery.size(),
                resultPage.getTotalPages(),
                resultPage.getTotalElements()
        );
    }

    private User save(UserEntity userEntity) {
        try {
            return User.fromEntity(userRepository.save(userEntity));
        } catch (ConstraintViolationException constraintViolationException) {
            throw ConflictingActivityDefinitionException.becauseOfRepositoryConstraintViolation();
        } catch (Exception exception) {
            throw UserSavingException.becauseOfExceptionDuringSaving(exception);
        }
    }
}
