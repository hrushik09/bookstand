package com.hrushi.bookstand.domain.profiles;

import com.hrushi.bookstand.domain.Country;
import com.hrushi.bookstand.domain.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        ProfileEntity profileEntity = profileRepository.findByUserId(cmd.userId())
                .orElse(new ProfileEntity(userService.getReferenceById(cmd.userId())));
        profileEntity.setFirstName(cmd.firstName());
        profileEntity.setLastName(cmd.lastName());
        profileEntity.setCountry(Country.valueOf(cmd.country()));
        profileEntity.setEmail(cmd.email());
        profileEntity.setBio(cmd.bio());
        profileRepository.save(profileEntity);
    }
}
