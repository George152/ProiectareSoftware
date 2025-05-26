package service;

import dto.BanRequestDTO;
import dto.BanResponseDTO;
import entity.Ban;
import entity.User;
import repository.BanRepository;
import repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BanService {

    @Autowired
    private BanRepository banRepository;

    @Autowired
    private UserRepository userRepository;

    public List<BanResponseDTO> getAllBans() {
        return banRepository.findAll().stream()
                .map(ban -> {
                    BanResponseDTO dto = new BanResponseDTO();
                    dto.setId(ban.getId());
                    dto.setUserId(ban.getUser().getId());
                    dto.setModeratorId(ban.getModerator().getId());
                    dto.setReason(ban.getReason());
                    dto.setBannedDate(ban.getBannedDate());
                    return dto;
                })
                .toList();
    }


    public BanResponseDTO createBan(BanRequestDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<User> modOpt = userRepository.findById(dto.getModeratorId());

        if (userOpt.isEmpty() || modOpt.isEmpty()) {
            throw new IllegalArgumentException("User or moderator not found");
        }

        Ban ban = new Ban();
        ban.setUser(userOpt.get());
        ban.setModerator(modOpt.get());
        ban.setReason(dto.getReason());
        ban = banRepository.save(ban);

        BanResponseDTO response = new BanResponseDTO();
        response.setId(ban.getId());
        response.setUserId(ban.getUser().getId());
        response.setModeratorId(ban.getModerator().getId());
        response.setReason(ban.getReason());
        response.setBannedDate(ban.getBannedDate());
        return response;
    }

    public BanResponseDTO getBanByUserId(Long userId) {
        Ban ban = banRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Ban not found"));
        BanResponseDTO response = new BanResponseDTO();
        response.setId(ban.getId());
        response.setUserId(ban.getUser().getId());
        response.setModeratorId(ban.getModerator().getId());
        response.setReason(ban.getReason());
        response.setBannedDate(ban.getBannedDate());
        return response;
    }
}
