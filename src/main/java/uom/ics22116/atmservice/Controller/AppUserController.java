package uom.ics22116.atmservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uom.ics22116.atmservice.Entity.AppUser;
import uom.ics22116.atmservice.Service.AppUserService;

import java.util.List;

@RestController()
@RequestMapping("api/user")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
        AppUser createdUser = appUserService.createUser(appUser);
        return ResponseEntity.ok(createdUser);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Integer id) {
        AppUser user = appUserService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> users = appUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Update user details
    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Integer id, @RequestBody AppUser appUser) {
        AppUser updatedUser = appUserService.updateUser(id, appUser);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
