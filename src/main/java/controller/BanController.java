package controller;

import dto.BanRequestDTO;
import dto.BanResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.BanService;

import java.util.List;

@RestController
@RequestMapping("/api/bans")
public class BanController {

    @Autowired
    private BanService banService;

    @PostMapping
    public BanResponseDTO createBan(@RequestBody BanRequestDTO requestDTO) {
        return banService.createBan(requestDTO);
    }

    @GetMapping
    public List<BanResponseDTO> getAllBans() {
        return banService.getAllBans();
    }

    @GetMapping("/user/{userId}")
    public BanResponseDTO getBanByUser(@PathVariable Long userId) {
        return banService.getBanByUserId(userId);
    }
}
