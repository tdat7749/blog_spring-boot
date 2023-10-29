package com.example.blog_springboot.modules.authenticate.constant;

public class AuthConstants {
    public static final String USER_NOT_FOUND = "Không tìm thấy người dùng";

    public static final String LOGIN_SUCCESS = "Đăng nhập thành công";
    public static final String REGISTER_SUCCESS = "Đăng ký thành công, vui lòng kiểm tra email để xác thực tài khoản";

    public static final String REGISTER_FAILED = "Đăng ký thất bại";

    public static final String USERNAME_USED = "Tài khoản này đã có người sử dụng";

    public static final String EMAIL_USED = "Email này đã được sử dụng";

    public static final String ACCOUNT_VERIFIED = "Tài khoản này đã được xác thực";

    public static final String VERIFY_FAILED = "Xác thực thất bại, có vẻ như tài khoản của bạn đã được xác thực hoặc trong quá trình xử lý gặp lỗi. Vui lòng thử lại sau !";

    public static final String VERIFY_SUCCESS = "Xác thực thành công";

    public static final String LOCKED_ACCOUNT_FAILED = "Khóa tài khoản thất bại";

    public static final String LOCKED_ACCOUNT_SUCCESS = "Khóa tài khoản thành công";

    public static final String UN_LOCKED_ACCOUNT_FAILED = "Mở khóa tài khoản thất bại";

    public static final String UN_LOCKED_ACCOUNT_SUCCESS = "Mở khóa tài khoản thành công";

    public static final String SET_CODE_FAILED = "Khởi tạo code xác thực thất bại";

    public static final String RESEND_EMAIL = "Gửi thành công, vui lòng kiểm tra email của bạn";

    public static final String CAN_NOT_LOCKED_YOUR_ACCOUNT = "Không thể khóa tài khoản của chính bạn";
}
