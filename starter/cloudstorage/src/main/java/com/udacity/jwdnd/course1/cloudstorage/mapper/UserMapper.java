package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     *
     * @param username username.
     * @return User object tied to provided username.
     */
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    /**
     *
     * @param user user object to insert.
     * @return newly generated userId.
     */
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
}
