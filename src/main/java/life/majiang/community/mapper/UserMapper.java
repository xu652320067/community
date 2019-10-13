package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author xupeng
 * @create 2019-10-13 20:38
 */
@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user(name,account_id,token,gmt_create,gmt_modified) VALUES(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    /*9.6 indexController中的方法*/
    @Select("SELECT * FROM user WHERE token=#{token}")
    User findByToken(String token);
}
