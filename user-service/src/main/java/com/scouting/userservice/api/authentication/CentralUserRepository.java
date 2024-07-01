package com.scouting.userservice.api.authentication;

import com.scouting.userservice.api.authentication.model.User;
import com.scouting.userservice.api.authentication.storage.UserEntity;
import com.scouting.userservice.api.authentication.storage.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.scouting.userservice.api.authentication.utils.Page;
import com.scouting.userservice.api.authentication.utils.UserPageQuery;


@Component
public class CentralUserRepository {
    private final UserRepository userRepository;

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

    public Page<User> getAllUsers(UserPageQuery userPageQuery) {
        org.springframework.data.domain.Page<UserEntity> resultPage;
        var pageRequest = PageRequest.of(userPageQuery.page(), userPageQuery.size(), Sort.by("id"));

        resultPage = userRepository.findAll(pageRequest);


        return new Page<>(
                resultPage.get().map(User::fromEntity).toList(),
                userPageQuery.page(),
                userPageQuery.size(),
                resultPage.getTotalPages(),
                resultPage.getTotalElements()
        );
    }
}
