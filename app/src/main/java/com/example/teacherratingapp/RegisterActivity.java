package com.example.teacherratingapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.stu.entity.User;
import com.stu.mapper.impl.EvaluationMapperImpl;
import com.stu.mapper.impl.UserMapperImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword, etConfirmPassword, etNickname, etPhoneNumber;
    private Button btnRegister;
    private UserMapperImpl userMapper;
    private EvaluationMapperImpl evaluationMapper; // 添加 evaluationMapper 作为成员变量

    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setupClickListeners();

        // 在后台线程中安全地初始化所有数据库操作类
        databaseExecutor.execute(() -> {
            userMapper = new UserMapperImpl();
            evaluationMapper = new EvaluationMapperImpl();
        });
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etNickname = findViewById(R.id.etNickname);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setupClickListeners() {
        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String nickname = etNickname.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
                Toast.makeText(this, "用户名、密码和昵称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            registerUser(username, password, nickname, phoneNumber);
        });
    }

    private void registerUser(String username, String password, String nickname, String phoneNumber) {
        // 将所有数据库操作（包括您的调试代码）都放入后台线程
        databaseExecutor.execute(() -> {
            // 检查数据库服务是否已就绪
            if (userMapper == null || evaluationMapper == null) {
                mainThreadHandler.post(() -> Toast.makeText(RegisterActivity.this, "数据库服务未就绪，请稍后重试", Toast.LENGTH_SHORT).show());
                return;
            }

            // 您用于调试的代码，现在可以在后台线程中安全执行
            System.out.println("test");

            // 检查用户名是否存在
            User existingUser = userMapper.getByUsername(username);

            if (existingUser != null) {
                mainThreadHandler.post(() -> {
                    Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                });
            } else {
                User newUser = new User(System.currentTimeMillis(), username, password, nickname, phoneNumber);
                userMapper.insert(newUser);

                mainThreadHandler.post(() -> {
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseExecutor.shutdown();
    }
}
