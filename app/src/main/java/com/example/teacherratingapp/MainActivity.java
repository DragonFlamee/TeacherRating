package com.example.teacherratingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.stu.entity.User;
import com.stu.mapper.impl.UserMapperImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private Button btnLogin, btnGoToRegister;
    private UserMapperImpl userMapper;

    // 创建一个单线程的线程池来处理所有数据库操作
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    // 创建一个 Handler 以便在主线程上发布UI更新
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupClickListeners();

        // 在后台线程中初始化 userMapper，避免在主线程进行网络操作
        databaseExecutor.execute(() -> {
            userMapper = new UserMapperImpl();
        });
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            attemptLogin(username, password);
        });

        btnGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void attemptLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 在后台线程中执行数据库查询
        databaseExecutor.execute(() -> {
            if (userMapper == null) {
                 mainThreadHandler.post(() -> Toast.makeText(MainActivity.this, "数据库服务未准备好，请稍后重试", Toast.LENGTH_SHORT).show());
                 return;
            }
            
            User user = userMapper.getByUsername(username);

            // 将结果传递回主线程以更新 UI
            mainThreadHandler.post(() -> {
                if (user != null && user.getPassword().equals(password)) {
                    // 登录成功
                    Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    // 可以在这里传递用户信息给 HomeActivity
                    intent.putExtra("userId", user.getId());
                    startActivity(intent);
                    finish(); // 关闭登录页面
                } else {
                    // 登录失败
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity 销毁时，关闭线程池释放资源
        databaseExecutor.shutdown();
    }
}
