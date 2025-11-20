package com.example.teacherratingapp; // 替换为你的包名

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //界面打开后最先运行的
        super.onCreate(savedInstanceState);

        // 这行代码决定显示哪个布局文件
        setContentView(R.layout.item_teacher); //确保这里是activity_login

//        // 初始化界面组件
//        initViews();
//
//        // 设置按钮点击事件
//        setupClickListeners();
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            // 这里实现登录逻辑
            attemptLogin(username, password);
        });

        btnGoToRegister.setOnClickListener(v -> {
            // 跳转到注册页面（如果还没有注册页面，可以先注释掉）
            // Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            // startActivity(intent);
        });
    }

    private void attemptLogin(String username, String password) {
        // 简单的登录验证（后续可以连接后端API）
        if (username.isEmpty() || password.isEmpty()) {
            // 显示错误提示
            etUsername.setError("用户名和密码不能为空");
        } else {
            // 登录成功，跳转到主页面
            // Intent intent = new Intent(MainActivity.this, TeacherListActivity.class);
            // startActivity(intent);
            // finish(); // 关闭登录页面
        }
    }
}