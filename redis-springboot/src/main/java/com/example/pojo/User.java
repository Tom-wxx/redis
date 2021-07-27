package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component  //变成组件方便调用
@AllArgsConstructor
@NoArgsConstructor
@Data
//我们所有的pojo 都回序列化
public class User implements Serializable {
    private  String name;
    private  String age;

}
