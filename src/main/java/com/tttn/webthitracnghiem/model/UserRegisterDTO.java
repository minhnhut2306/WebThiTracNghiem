package com.tttn.webthitracnghiem.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UserRegisterDTO {
    @NotBlank(message = "Tài khoản không được để trống.")
    private String id;

    @Length(min = 6, message = "Mật khẩu không được ít hơn 6 kí tự.")
    @NotBlank(message = "Mật khẩu không được để trống.")
    private String passWord;

    @Length(min = 6, message = "Mật khẩu xác nhận không được ít hơn 6 kí tự.")
    @NotBlank(message = "Mật khẩu xác nhận không được để trống.")
    private String rePassWord;

    @NotBlank(message = "Tên không được để trống.")
    private String fullName;

    @Pattern(regexp = "^[a-z][a-z0-9_\\.]*@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$", message = "Sai định dạng email")
    @NotBlank(message = "Email không được để trống.")
    private String email;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @Pattern(regexp = "^((\\(84\\)\\+)|(0))[1-9][\\d]{8}$", message = "Sai định dạng số điện thoại")
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phoneNumber;

    private String img;

    private String createDate;

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassWord() { return passWord; }
    public void setPassWord(String passWord) { this.passWord = passWord; }

    public String getRePassWord() { return rePassWord; }
    public void setRePassWord(String rePassWord) { this.rePassWord = rePassWord; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

    public String getCreateDate() { return createDate; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }

    // Method to validate if password and rePassword match
    public boolean isPasswordValid() {
        return this.passWord != null && this.rePassWord != null && this.passWord.equals(this.rePassWord);
    }
}