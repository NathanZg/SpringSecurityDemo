package com.ekertree.security.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: User
 * Description:
 * date: 2022/5/25 13:15
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
@ApiModel(description = "用户实体类")
public class User implements Serializable {
    private String username;
    private String password;
    private String nickName;
    private String salt;
    private String token;
}
