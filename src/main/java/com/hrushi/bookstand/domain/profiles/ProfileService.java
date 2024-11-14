package com.hrushi.bookstand.domain.profiles;

import com.hrushi.bookstand.domain.Country;
import com.hrushi.bookstand.domain.users.UserEntity;
import com.hrushi.bookstand.domain.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;

    ProfileService(ProfileRepository profileRepository, UserService userService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
    }

    public Profile findProfile(Long userId) {
        return profileRepository.findByUserId(userId)
                .map(profileEntity -> new Profile(profileEntity.getFirstName(), profileEntity.getLastName(), profileEntity.getCountry().displayName(), profileEntity.getEmail(), profileEntity.getBio()))
                .orElse(Profile.dummyProfile());
    }

    @Transactional
    public void updateProfile(UpdateProfileCommand cmd) {
        Optional<ProfileEntity> optionalProfile = profileRepository.findByUserId(cmd.userId());
        ProfileEntity profileEntity;
        if (optionalProfile.isPresent()) {
            profileEntity = optionalProfile.get();
        } else {
            profileEntity = new ProfileEntity();
            UserEntity userEntity = userService.getReferenceById(cmd.userId());
            profileEntity.setUserEntity(userEntity);
        }
        profileEntity.setFirstName(cmd.firstName());
        profileEntity.setLastName(cmd.lastName());
        profileEntity.setCountry(Country.valueOf(cmd.country()));
        profileEntity.setEmail(cmd.email());
        profileEntity.setBio(cmd.bio());
        profileRepository.save(profileEntity);
    }
}
