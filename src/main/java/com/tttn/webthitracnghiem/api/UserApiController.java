package com.tttn.webthitracnghiem.api;

import com.tttn.webthitracnghiem.model.User;
import com.tttn.webthitracnghiem.model.UserDTO;
import com.tttn.webthitracnghiem.model.UserLoginDTO;
import com.tttn.webthitracnghiem.model.UserRegisterDTO;
import com.tttn.webthitracnghiem.model.UserRequest;
import com.tttn.webthitracnghiem.service.IUserService;

import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getId(), loginUser.getPassWord()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User authenticatedUser = userService.findById(loginUser.getId());
            if (authenticatedUser == null) {
                return ResponseEntity.status(401).body("Sai tài khoản hoặc mật khẩu!");
            }
            return ResponseEntity.ok(authenticatedUser); // Chỉ trả trường cần thiết hoặc custom lại response
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Sai tài khoản hoặc mật khẩu!");
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Dữ liệu đầu vào không hợp lệ", "details", bindingResult.getAllErrors()));
        }
        try {
            // Kiểm tra nếu rePassWord là null
            if (dto.getRePassWord() == null) {
                return ResponseEntity.badRequest().body("Mật khẩu xác nhận không được để trống!");
            }

            // Kiểm tra nếu password và rePassword không khớp
            if (!dto.isPasswordValid()) {
                return ResponseEntity.badRequest().body("Mật khẩu xác nhận không khớp!");
            }

            // Kiểm tra tài khoản và email có tồn tại không
            if (userService.userExists(dto.getId())) {
                return ResponseEntity.badRequest().body("Tài khoản đã tồn tại!");
            }
            if (userService.userExistss(dto.getEmail())) {
                return ResponseEntity.badRequest().body("Email đã được sử dụng!");
            }

            // Tạo người dùng
            User user = new User();
            user.setId(dto.getId());
            user.setPassWord(passwordEncoder.encode(dto.getPassWord())); // Mã hóa mật khẩu
            user.setFullName(dto.getFullName());
            user.setEmail(dto.getEmail());
            user.setAddress(dto.getAddress());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setImg(dto.getImg() != null ? dto.getImg() : "/img/avatar/default.jpg"); // Hình ảnh mặc định nếu null
            user.setCreateDate(new java.sql.Date(System.currentTimeMillis())); // Đặt ngày tạo

            // Lưu người dùng (giao dịch được xử lý trong service)
            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser); // Trả về người dùng đã lưu thành công
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi đăng ký: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về lỗi 404 nếu không tìm thấy
        }

        // Chuyển đổi từ User thành UserDTO
        UserDTO userDTO = new UserDTO(user.getId(), user.getFullName(), user.getEmail(),
                user.getPhoneNumber(), user.getImg(), user.getCreateDate());

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> save(@ModelAttribute UserRequest userRequest) {
        User user = userService.findById(userRequest.getId());
        userRequest.setRoles(user.getRoles());
        return ResponseEntity.ok(userService.save(userRequest));
    }
}
